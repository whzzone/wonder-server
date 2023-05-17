package com.example.securitytest.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.securitytest.common.Result;
import com.example.securitytest.pojo.dto.LoginDto;
import com.example.securitytest.pojo.entity.SysUser;
import com.example.securitytest.service.AuthService;
import com.example.securitytest.service.SysUserService;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Result login(LoginDto dto) {

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
}
