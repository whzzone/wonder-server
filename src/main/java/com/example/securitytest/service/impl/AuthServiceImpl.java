package com.example.securitytest.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.securitytest.common.Result;
import com.example.securitytest.common.security.EmailAuthenticationToken;
import com.example.securitytest.pojo.dto.EmailLoginDto;
import com.example.securitytest.pojo.dto.LoginDto;
import com.example.securitytest.pojo.entity.SysUser;
import com.example.securitytest.service.AuthService;
import com.example.securitytest.service.SysUserService;
import com.example.securitytest.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author : whz
 * @date : 2023/5/17 16:29
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Value("${system-config.cache.token.live-time}")
    private long cacheTime;

    @Value("${system-config.cache.token.live-unit}")
    private TimeUnit cacheTimeUnit;

    @Value("${system-config.cache.token.prefix}")
    private String cacheTokenPrefix;

    @Value("${system-config.security.login-type.email}")
    private Boolean emailTypeEnable;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Result loginByUsername(LoginDto dto) {

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, dto.getUsername());
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        if (Objects.isNull(sysUser)) {
            throw new UsernameNotFoundException(StrUtil.format("用户{}不存在", dto.getUsername()));
        }

        try {
            String token = UUID.randomUUID().toString();
            String key = cacheTokenPrefix + token;

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(sysUser), cacheTime, cacheTimeUnit);
            Map<String, Object> res = new HashMap<>();
            res.put("token", token);
            return Result.ok("登录成功", res);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("密码错误");
        } catch (Exception e) {
            throw new RuntimeException("未知错误");
        }
    }

    @Override
    public Result loginByEmail(EmailLoginDto dto) {
        if (!emailTypeEnable){
            throw new RuntimeException("未开启邮箱登录");
        }

        EmailAuthenticationToken authenticationToken = new EmailAuthenticationToken(dto.getEmail(), dto.getCode());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 认证成功，将用户信息存入SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        redisTemplate.delete("EMAIL:" + dto.getEmail());

        SysUser sysUser = sysUserService.getByEmail(dto.getEmail());

        String token = UUID.randomUUID().toString();
        String key = cacheTokenPrefix + token;
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(sysUser), cacheTime, cacheTimeUnit);

        Map<String, Object> res = new HashMap<>();
        res.put("token", token);
        return Result.ok("登录成功", res);
    }

    @Override
    public Result sendEmail(@Email String email) {
        String code = RandomUtil.getCode(6);
        while (Boolean.TRUE.equals(redisTemplate.hasKey(code))) {
            code = RandomUtil.getCode(6);
        }
        redisTemplate.opsForValue().set("EMAIL:" + email, code, 5, TimeUnit.MINUTES);
        MailUtil.send(email, "登录验证码", "此次登录的验证码是：" + code, false);
        return Result.ok("已发送");
    }

    private String generatorToken(){
//        try {
//            String token = UUID.randomUUID().toString();
//            String key = cacheTokenPrefix + token;
//
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
//            Authentication authentication = authenticationManager.authenticate(authenticationToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(sysUser), cacheTime, cacheTimeUnit);
//            Map<String, Object> res = new HashMap<>();
//            res.put("token", token);
//            return Result.ok("登录成功", res);
//
//        } catch (BadCredentialsException e) {
//            throw new BadCredentialsException("密码错误");
//        } catch (Exception e) {
//            throw new RuntimeException("未知错误");
//        }
        return null;
    }
}
