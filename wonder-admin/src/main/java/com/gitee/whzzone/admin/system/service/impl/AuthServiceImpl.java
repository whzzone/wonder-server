package com.gitee.whzzone.admin.system.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.gitee.whzzone.admin.common.enums.LoginDeviceTypeEnum;
import com.gitee.whzzone.admin.common.redis.RedisCache;
import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.system.entity.User;
import com.gitee.whzzone.admin.system.pojo.auth.UsernameLoginDTO;
import com.gitee.whzzone.admin.system.pojo.auth.WxLoginDTO;
import com.gitee.whzzone.admin.system.service.AuthService;
import com.gitee.whzzone.admin.system.service.UserService;
import com.gitee.whzzone.admin.util.CaptchaUtil;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.web.pojo.other.Result;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author : whz
 * @date : 2023/5/17 16:29
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private UserService userService;

    @Override
    public Map<String, String> getCode() {
        CaptchaUtil captchaUtil = new CaptchaUtil();
        //得到缓冲区
        BufferedImage image = captchaUtil.getImage();
        String imgBase64 = CaptchaUtil.getBase64(image);

        //得到真实验证码
        String code = captchaUtil.getCode();

        String uuid = UUID.randomUUID().toString();

        // 响应数据
        Map<String, String> res = new HashMap<>();
        res.put("uuid", uuid);
        res.put("code", code);
        res.put("imgBase64", imgBase64);

        String format = "login:code:{}";
        // 缓存
        redisCache.set(StrUtil.format(format, uuid), code, 3, TimeUnit.MINUTES);

        return res;
    }

    @Override
    public Result<LoginUser> loginByUsername(UsernameLoginDTO dto) {

        User user = userService.getByUsername(dto.getUsername());

        userService.loginCheck(user);

        if (!BCrypt.checkpw(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        StpUtil.login(user.getId(), new SaLoginModel()
                .setDevice(LoginDeviceTypeEnum.PC.name()) // 此次登录的客户端设备类型, 用于[同端互斥登录]时指定此次登录的设备类型
                .setIsLastingCookie(false)        // 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
                .setIsWriteHeader(false)         // 是否在登录后将 Token 写入到响应头
        );

        LoginUser loginUserInfo = userService.getLoginUserInfo(user.getId());

        loginUserInfo.setTokenInfo(StpUtil.getTokenInfo());

        return Result.ok("登录成功", loginUserInfo);
    }

    @Override
    public void logout() {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        StpUtil.logout(loginUser.getId());
    }

    @Override
    public Result<LoginUser> loginByWeixin(WxLoginDTO dto) {
        try {
            WxMaUserService wxMaServiceUserService = wxMaService.getUserService();
            WxMaJscode2SessionResult session = wxMaServiceUserService.getSessionInfo(dto.getCode());

            User user = userService.getByOpenid(session.getOpenid());
            userService.loginCheck(user);

            StpUtil.login(user.getId(), new SaLoginModel()
                    .setDevice(LoginDeviceTypeEnum.APP.name()) // 此次登录的客户端设备类型, 用于[同端互斥登录]时指定此次登录的设备类型
                    .setIsLastingCookie(false)        // 是否为持久Cookie（临时Cookie在浏览器关闭时会自动删除，持久Cookie在重新打开后依然存在）
                    .setIsWriteHeader(false)         // 是否在登录后将 Token 写入到响应头
            );

            LoginUser loginUserInfo = userService.getLoginUserInfo(user.getId());

            loginUserInfo.setTokenInfo(StpUtil.getTokenInfo());

            return Result.ok("登录成功", loginUserInfo);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return Result.error(Result.UNAUTHORIZED, e.getMessage());
        }catch (Exception e) {
            return Result.error(e.getMessage());
        }
//        finally {
//            WxMaConfigHolder.remove();//清理ThreadLocal
//        }
    }
}
