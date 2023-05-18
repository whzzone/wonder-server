package com.example.securitytest.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.securitytest.common.Result;
import com.example.securitytest.common.security.EmailAuthenticationToken;
import com.example.securitytest.pojo.dto.EmailLoginDto;
import com.example.securitytest.pojo.dto.LoginDto;
import com.example.securitytest.pojo.dto.auth.LoginSuccessDto;
import com.example.securitytest.pojo.entity.SysUser;
import com.example.securitytest.service.AuthService;
import com.example.securitytest.service.SysUserService;
import com.example.securitytest.util.CacheKey;
import com.example.securitytest.util.RandomUtil;
import com.example.securitytest.util.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
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

    @Value("${system-config.security.login-type.email}")
    private Boolean emailTypeEnable;

    @Value("${system-config.security.login-type.username}")
    private Boolean usernameTypeEnable;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisCache redisCache;

    @Value("${system-config.cache.token.refresh-time}")
    private long refreshTime;

    @Value("${system-config.cache.token.refresh-unit}")
    private TimeUnit refreshUnit;

    @Override
    public Result loginByUsername(LoginDto dto) {
        try {
            if (!usernameTypeEnable){
                throw new RuntimeException("未开启账号密码登录");
            }

            LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getUsername, dto.getUsername());
            SysUser sysUser = sysUserService.getOne(queryWrapper);
            if (Objects.isNull(sysUser)) {
                throw new UsernameNotFoundException(StrUtil.format("用户{}不存在", dto.getUsername()));
            }

            String userIdTokenKey = StrUtil.format(CacheKey.USER_ID_TOKEN, sysUser.getId());

            if (redisCache.hasKey(userIdTokenKey)) {
//                Long expire = redisCache.getExpire(userIdTokenKey);
//                if (expire < refreshUnit.toSeconds(refreshTime)){
//                    redisCache.expire(userIdTokenKey, cacheTime, cacheTimeUnit);
//                }
//                return Result.ok("登录成功", new LoginSuccessDto((String) redisCache.get(userIdTokenKey), expire + cacheTimeUnit.toSeconds(cacheTime)));
                return Result.ok("登录成功", new LoginSuccessDto((String) redisCache.get(userIdTokenKey), redisCache.getExpire(userIdTokenKey)));
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = UUID.randomUUID().toString();

            String tokenKey = StrUtil.format(CacheKey.TOKEN_TOKEN_USERID, token);
            String userKey = StrUtil.format(CacheKey.USER_ID_INFO, sysUser.getId());

            redisCache.set(tokenKey, sysUser.getId(), cacheTime, cacheTimeUnit);
            redisCache.set(userKey, sysUser);
            redisCache.set(userIdTokenKey, token, cacheTime, cacheTimeUnit);

            return Result.ok("登录成功", new LoginSuccessDto(token, cacheTimeUnit.toSeconds(cacheTime)));

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("密码错误");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
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

        String key = StrUtil.format(CacheKey.EMAIL_EMAIL_CODE, dto.getEmail());

        redisCache.delete(key);

        SysUser sysUser = sysUserService.getByEmail(dto.getEmail());

        String token = UUID.randomUUID().toString();

        String tokenKey = StrUtil.format(CacheKey.TOKEN_TOKEN_USERID, token);
        String userKey = StrUtil.format(CacheKey.USER_ID_INFO, sysUser.getId());

        redisCache.set(tokenKey, sysUser.getId(), cacheTime, cacheTimeUnit);
        redisCache.set(userKey, sysUser);

        return Result.ok("登录成功", new LoginSuccessDto(token, cacheTimeUnit.toSeconds(cacheTime)));
    }

    @Override
    public Result sendEmail(@Email String email) {
        String code = RandomUtil.getCode(6);

        while (Boolean.TRUE.equals(redisCache.hasKey(code))) {
            code = RandomUtil.getCode(6);
        }

        String key = StrUtil.format(CacheKey.EMAIL_EMAIL_CODE, email);

        redisCache.set(key, code, 5, TimeUnit.MINUTES);

        MailUtil.send(email, "登录验证码", "此次登录的验证码是：" + code, false);

        return Result.ok("验证码已发送");
    }

}
