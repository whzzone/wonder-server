package com.gitee.whzzone.admin.system.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import com.gitee.whzzone.admin.common.properties.SecurityProperties;
import com.gitee.whzzone.admin.common.redis.RedisCache;
import com.gitee.whzzone.admin.common.security.EmailAuthenticationToken;
import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.common.service.TokenService;
import com.gitee.whzzone.admin.system.pojo.auth.EmailLoginDto;
import com.gitee.whzzone.admin.system.pojo.auth.UsernameLoginDto;
import com.gitee.whzzone.admin.system.pojo.auth.WxLoginDto;
import com.gitee.whzzone.admin.system.service.*;
import com.gitee.whzzone.admin.util.CaptchaUtil;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.common.util.CacheKey;
import com.gitee.whzzone.common.util.RandomUtil;
import com.gitee.whzzone.web.pojo.other.Result;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SecurityProperties securityProperties;

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
    public Result<LoginUser> loginByUsername(UsernameLoginDto dto) {
        if (!securityProperties.getLoginType().getUsername()) {
            throw new RuntimeException("未开启账号密码登录");
        }

        Authentication authentication;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("账号或密码错误");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }finally {
            SecurityContextHolder.clearContext();
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        tokenService.createToken(loginUser);

        tokenService.cacheToken(loginUser);

        return Result.ok("登录成功", loginUser);
    }

    @Override
    public Result loginByEmail(EmailLoginDto dto) {
        if (!securityProperties.getLoginType().getEmail()){
            throw new RuntimeException("未开启邮箱登录");
        }

        EmailAuthenticationToken authenticationToken = new EmailAuthenticationToken(dto.getEmail(), dto.getCode());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 认证成功，将用户信息存入SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return null;
    }

    @Override
    public Result sendEmail(@Email String email) {
        String code = RandomUtil.getIntCode(6);

        while (redisCache.hasKey(code)) {
            code = RandomUtil.getIntCode(6);
        }

        String key = StrUtil.format(CacheKey.EMAIL_EMAIL_CODE, email);

        redisCache.set(key, code, 5, TimeUnit.MINUTES);

        MailUtil.send(email, "登录验证码", "此次登录的验证码是：" + code, false);

        return Result.ok("验证码已发送");
    }

    @Override
    public void logout() {
        Integer id = SecurityUtil.getLoginUser().getId();
        String userKey = StrUtil.format(CacheKey.USER_ID_INFO, id);
        String userIdTokenKey = StrUtil.format(CacheKey.USER_ID_TOKEN, id);
        if (redisCache.hasKey(userIdTokenKey)) {
            String token = (String) redisCache.get(userIdTokenKey);
            String tokenKey = StrUtil.format(CacheKey.TOKEN_USERID, token);
            redisCache.delete(tokenKey);
        }
        redisCache.delete(userIdTokenKey);
        redisCache.delete(userKey);
    }

    @Override
    public Result loginByWeixin(WxLoginDto dto) {
        try {
            log.warn("code =========" + dto.getCode());
            WxMaJscode2SessionResult session = wxMaService.getUserService().getSessionInfo(dto.getCode());
            log.info(session.getSessionKey());
            log.info(session.getOpenid());

            WxMaUserService userService = wxMaService.getUserService();

            WxMaPhoneNumberInfo phoneNoInfo = userService.getPhoneNoInfo(dto.getCode());
            System.out.println("phoneNoInfo = " + phoneNoInfo);

            if (userService.checkUserInfo(session.getSessionKey(), dto.getRawData(), dto.getSignature())) {
                log.info("验证通过");
            }else {
                log.error("数据被改过");
            }

            WxMaUserInfo userInfo = userService.getUserInfo(session.getSessionKey(), dto.getEncryptedData(), dto.getIv());
            System.out.println("userInfo = " + userInfo);

            Map<String, Object> res = new HashMap<>();
            res.put("openid", session.getOpenid());

            //TODO 可以增加自己的逻辑，关联业务相关数据
            return Result.ok(res);
        } catch (WxErrorException e) {
            log.error(e.getMessage(), e);
            return Result.error(e.getMessage());
        }
        //        finally {
        //            WxMaConfigHolder.remove();//清理ThreadLocal
        //        }
    }
}
