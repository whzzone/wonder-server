package com.example.securitytest.common.security;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.securitytest.common.Result;
import com.example.securitytest.pojo.entity.SysUser;
import com.example.securitytest.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author : whz
 * @date : 2023/5/17 17:46
 */
@Component
@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    @Value("${system-config.cache.token.prefix}")
    private String cacheTokenPrefix;

    @Value("${system-config.cache.user.prefix}")
    private String cacheUserPrefix;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${system-config.cache.token.live-time}")
    private long cacheTime;

    @Value("${system-config.cache.token.live-unit}")
    private TimeUnit cacheTimeUnit;

    @Value("${system-config.cache.token.refresh-time}")
    private long refreshTime;

    @Value("${system-config.cache.token.refresh-unit}")
    private TimeUnit refreshUnit;

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");

        if (StrUtil.isBlank(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String tokenKey = cacheTokenPrefix + token;

        Boolean hasKey = redisTemplate.hasKey(tokenKey);
        if (Boolean.FALSE.equals(hasKey)) {
            response.setHeader("content-type", "application/json");
            IoUtil.write(response.getOutputStream(), true, JSONUtil.toJsonStr(Result.error("错误的token")).getBytes());
            log.error("错误的token");
            return;
        }

        Long userId = (Long) redisTemplate.opsForValue().get(tokenKey);

        String userKey = cacheUserPrefix + userId;

        SysUser sysUser;

        String jsonStr = (String) redisTemplate.opsForValue().get(userKey);

        sysUser = JSONUtil.toBean(jsonStr, SysUser.class);

        if (Objects.isNull(sysUser)){
            sysUser = sysUserService.getById(userId);
        }

        if (Objects.isNull(sysUser)){
            response.setHeader("content-type", "application/json");
            IoUtil.write(response.getOutputStream(), true, JSONUtil.toJsonStr(Result.error("用户不存在")).getBytes());
            log.error("用户不存在");
            return;
        }

        try {
            sysUserService.verifyUser(sysUser);
        } catch (Exception e){
            response.setHeader("content-type", "application/json");
            IoUtil.write(response.getOutputStream(), true, JSONUtil.toJsonStr(Result.error(e.getMessage())).getBytes());
            log.error(e.getMessage());
            return;
        }

        if(redisTemplate.boundValueOps(tokenKey).getExpire() < refreshUnit.toSeconds(refreshTime)){
            redisTemplate.boundValueOps(tokenKey).expire(cacheTime, cacheTimeUnit);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser, null, null);
        authenticationToken.setDetails(new WebAuthenticationDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}