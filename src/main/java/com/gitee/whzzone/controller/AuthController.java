package com.gitee.whzzone.controller;

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaCodeLineColor;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.gitee.whzzone.common.QrCodeStatus;
import com.gitee.whzzone.common.weixin.WxLoginDto;
import com.gitee.whzzone.pojo.dto.EmailLoginDto;
import com.gitee.whzzone.pojo.dto.UserDto;
import com.gitee.whzzone.pojo.dto.auth.LoginSuccessDto;
import com.gitee.whzzone.pojo.dto.auth.UsernameLoginDto;
import com.gitee.whzzone.pojo.entity.User;
import com.gitee.whzzone.service.AuthService;
import com.gitee.whzzone.service.UserService;
import com.gitee.whzzone.util.CacheKey;
import com.gitee.whzzone.util.RandomUtil;
import com.gitee.whzzone.util.RedisCache;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author : whz
 * @date : 2023/5/16 19:34
 */
@RestController
@RequestMapping("auth")
@Api(tags = "认证服务")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserService sysUserService;

    @Value("${security.token.live-time}")
    private long cacheTime;

    @Value("${security.token.live-unit}")
    private TimeUnit cacheTimeUnit;

    @ApiOperation("账号密码登录-获取验证码")
    @GetMapping("/getCode")
    public Result getCode(){
        return Result.ok("操作成功", authService.getCode());
    }

    @GetMapping("/getLoginCodeByUUID/{uuid}")
    @ApiOperation("查看验证码")
    public Result getLoginCodeByUUID(@PathVariable String uuid){
        String format = "login:code:{}";
        return Result.ok("操作成功", redisCache.get(StrUtil.format(format, uuid)));
    }

    @ApiOperation("账号密码登录")
    @PostMapping("/login/username")
    public Result loginByUsername(@Validated @RequestBody UsernameLoginDto dto) {
        return authService.loginByUsername(dto);
    }

    @ApiOperation("测试")
    @GetMapping("test")
    public Result test() {
        return Result.ok("111111");
    }

    @GetMapping({"logout"})
    public Result logout() {
        authService.logout();
        return Result.ok();
    }

    @ApiOperation("邮箱登录")
    @PostMapping("/login/email")
    public Result loginByEmail(@Validated @RequestBody EmailLoginDto dto) {
        return authService.loginByEmail(dto);
    }

    @PostMapping("/send/{email}")
    public Result sendEmail(@PathVariable String email) {
        return authService.sendEmail(email);
    }

    @ApiOperation("微信登录")
    @PostMapping("/login/weixin")
    public Result loginByWeixin(@Validated @RequestBody WxLoginDto dto) {
        return authService.loginByWeixin(dto);
    }

    @ApiOperation(value = "生成PC登录小程序二维码")
    @GetMapping("/generatorPCLoginQrcode")
    public Result generatorQrcode() throws WxErrorException {
        WxMaQrcodeService qrcodeService = wxMaService.getQrcodeService();
        String keyFormat = "wx:scene:{}";
        String scene = RandomUtil.getStringCode(6); //参数，具体自定配置,32位
        String key = StrUtil.format(keyFormat, scene);

        while (redisCache.hasKey(key)) {
            scene = RandomUtil.getStringCode(6);
            key = StrUtil.format(keyFormat, scene);
        }

        redisCache.set(key, QrCodeStatus.NOT_SCANNED, 10, TimeUnit.MINUTES);

        String page = "pages/pc/pc";        //小程序页面路径，为空跳转主页面
        //返回二维码相关接口方法的实现类对象
        //获取小程序码（永久有效、数量暂无限制）
        File file = qrcodeService.createWxaCodeUnlimit(
                scene,
                page,
                false,
                "develop",
                100,
                false,
                new WxMaCodeLineColor("0", "0", "0"),
                false);
        try {
            // 生成到本地
//            FileOutputStream fos = new FileOutputStream(("E:\\Desktop\\test.jpg"));
//            FileInputStream fis = new FileInputStream(file);
//            byte[] buffer = new byte[1024];
//            int len;
//            while ((len = fis.read(buffer)) > 0) {
//                fos.write(buffer, 0, len);
//            }
//            fis.close();
//            fos.close();

            // base64
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fileInputStream.read(data);
            fileInputStream.close();

            // 将字节数组转换为base64编码的字符串
            String base64 = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(data);
            Map<String, Object> res = new HashMap<>();
            res.put("qrcode", base64);
            res.put("scene", scene);

            return Result.ok(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.error("错误");
    }

    @ApiOperation(value = "checkQrcode")
    @GetMapping("/checkQrcode/{scene}")
    public Result checkQrcode(@PathVariable String scene){
        String keyFormat = "wx:scene:{}";
        String key = StrUtil.format(keyFormat, scene);
        if (!redisCache.hasKey(key)){
            return Result.ok(QrCodeStatus.INVALID);
        }
        return Result.ok(redisCache.get(key));
    }

    @ApiOperation(value = "getWxPhone")
    @GetMapping("/getWxPhone")
    public Result getWxPhone() throws WxErrorException {
        WxMaUserService userService = wxMaService.getUserService();
        WxMaPhoneNumberInfo phoneNoInfo = userService.getPhoneNoInfo("92702ff1dc3a31e65066fa6a3b9a016ce29dd031c7c8a9a38caead6114e1ef15");
        System.out.println("phoneNoInfo = " + phoneNoInfo);
        return Result.ok(phoneNoInfo);
    }

    @ApiOperation("PC小程序码登录")
    @PostMapping("/login/weixin/pc")
    public Result loginByWeixinPC(@Validated @RequestBody WxLoginDto dto) throws WxErrorException {
        log.info("进入PC小程序码登录");
        String scene = dto.getScene();
        String code = dto.getCode();

        if (StrUtil.isBlank(scene)){
            throw new RuntimeException("scene为空");
        }

        if (StrUtil.isBlank(code)){
            throw new RuntimeException("code为空");
        }

        String keyFormat = "wx:scene:{}";
        String key = StrUtil.format(keyFormat, scene);
        if (!redisCache.hasKey(key)) {
            throw new RuntimeException("无效的二维码");
        }


//        redisCache.delete(key);

        WxMaUserService userService = wxMaService.getUserService();
        WxMaJscode2SessionResult sessionInfo = userService.getSessionInfo(code);

        String openid = sessionInfo.getOpenid();
        if (StrUtil.isBlank(openid)){
            throw new RuntimeException("openid为空");
        }

        User sysUser = sysUserService.getByOpenid(openid);
        if (Objects.isNull(sysUser)) {
            throw new RuntimeException("getByOpenid 未查询到用户");
        }

        String token = UUID.randomUUID().toString();

        String tokenKey = StrUtil.format(CacheKey.TOKEN_USERID, token);
        String userKey = StrUtil.format(CacheKey.USER_ID_INFO, sysUser.getId());
        String userIdTokenKey = StrUtil.format(CacheKey.USER_ID_TOKEN, sysUser.getId());

        redisCache.set(tokenKey, sysUser.getId(), cacheTime, cacheTimeUnit);
        redisCache.set(userKey, sysUser);
        redisCache.set(userIdTokenKey, token, cacheTime, cacheTimeUnit);
        log.info("获得token=" + token);
        String keyTokenFormat = "wx:scene:{}:token";
        String keyToken = StrUtil.format(keyTokenFormat, scene);
        redisCache.set(keyToken, token, 10, TimeUnit.MINUTES);

        UserDto sysUserDto = new UserDto();
        BeanUtil.copyProperties(sysUser,sysUserDto);
        LoginSuccessDto res = new LoginSuccessDto();
        res.setToken(token);
        res.setExpire(cacheTimeUnit.toSeconds(cacheTime));
        return Result.ok("登录成功", res);
    }

    @ApiOperation("已扫描某个scene值的小程序码")
    @GetMapping("/wx/scanned/{scene}")
    public Result scanned(@PathVariable String scene){
        String keyFormat = "wx:scene:{}";
        String key = StrUtil.format(keyFormat, scene);
        if (!redisCache.hasKey(key)) {
            return Result.error("扫描的了无效的小程序码");
        }
        Integer qrcodeStatus = (Integer) redisCache.get(key);
        if (qrcodeStatus.equals(QrCodeStatus.SCANNED)){
            return Result.error("此码已被扫过了");
        }

        redisCache.set(key, QrCodeStatus.SCANNED);
        return Result.ok("处理成功");
    }

    @ApiOperation("根据scene值获取token")
    @GetMapping("/getTokenByScene/{scene}")
    public Result getTokenByScene(@PathVariable String scene){
        String keyFormat = "wx:scene:{}:token";
        String key = StrUtil.format(keyFormat, scene);
        String token = "";
        if (redisCache.hasKey(key)){
            token = (String) redisCache.get(key);
//            redisCache.delete(key);
        }

        return Result.ok("获取token的结果", token);
    }
}

