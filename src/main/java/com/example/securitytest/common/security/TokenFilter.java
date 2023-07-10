package com.example.securitytest.common.security;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.securitytest.pojo.entity.User;
import com.example.securitytest.service.MenuService;
import com.example.securitytest.service.UserService;
import com.example.securitytest.util.CacheKey;
import com.example.securitytest.util.RedisCache;
import com.gitee.whzzone.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author : whz
 * @date : 2023/5/17 17:46
 */
@Component
@Slf4j
public class TokenFilter extends OncePerRequestFilter {

    @Value("${security.cache.token.live-time}")
    private long cacheTime;

    @Value("${security.cache.token.live-unit}")
    private TimeUnit cacheTimeUnit;

    @Value("${security.cache.token.refresh-time}")
    private long refreshTime;

    @Value("${security.cache.token.refresh-unit}")
    private TimeUnit refreshUnit;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MenuService menuService;

    @Value("${security.ignore-path}")
    private String[] ignorePath = new String[]{"/auth/**", "/test"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
            AntPathMatcher matcher = new AntPathMatcher();
            for (String ignorePath : ignorePath) {
                if (matcher.match(ignorePath, path)) {
                    // 如果当前请求的 URL 在忽略列表中，则直接放行
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            String token = request.getHeader(HttpHeaders.AUTHORIZATION);

            if (StrUtil.isBlank(token))
                throw new  RuntimeException("未携带token访问");

            String tokenKey = StrUtil.format(CacheKey.TOKEN_USERID, token);

            Boolean hasKey = redisCache.hasKey(tokenKey);
            if (Boolean.FALSE.equals(hasKey))
                throw new RuntimeException("失效的token");

            Long userId = (Long) redisCache.get(tokenKey);

            User user = userService.getById(userId);

            if (Objects.isNull(user))
                throw new RuntimeException("用户不存在");

            userService.beforeLoginCheck(user);

            if (redisCache.getExpire(tokenKey) < refreshUnit.toSeconds(refreshTime)) {
                redisCache.expire(tokenKey, cacheTime, cacheTimeUnit);
            }

            List<String> permitByUserId = menuService.findPermitByUserId(userId);
            log.warn(permitByUserId.toString());

            List<SimpleGrantedAuthority> list = new ArrayList<>();
            for (String authority : permitByUserId) {
                if (!StringUtils.isEmpty(authority)){
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
                    list.add(simpleGrantedAuthority);
                }
            }

            LoginUser loginUser = new LoginUser();
            BeanUtil.copyProperties(user, loginUser);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, list);
            authenticationToken.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (Exception e) {
            response.setHeader("content-type", "application/json");
            IoUtil.write(response.getOutputStream(), true, JSONUtil.toJsonStr(Result.error(Result.UNAUTHORIZED, e.getMessage())).getBytes());
            e.printStackTrace();
            return;
        }

        filterChain.doFilter(request, response);
    }
}
