package com.gitee.whzzone.admin.common.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.gitee.whzzone.admin.common.properties.WonderProperties;
import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.common.service.TokenService;
import com.gitee.whzzone.admin.system.service.UserService;
import com.gitee.whzzone.web.pojo.other.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * @author : whz
 * @date : 2023/5/17 17:46
 */
@Slf4j
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private WonderProperties wonderProperties;

    @Autowired
    private AntPathMatcher antPathMatcher;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
            for (String ignorePath : wonderProperties.getWeb().getIgnorePath()) {
                if (antPathMatcher.match(ignorePath, path)) {
                    // 如果当前请求的 URL 在忽略列表中，则直接放行
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            String token = request.getHeader(wonderProperties.getToken().getHeader());
            Integer roleId = StringUtils.hasText(request.getHeader("RoleId")) ? Integer.valueOf(request.getHeader("RoleId")) : null;
            Integer deptId = StringUtils.hasText(request.getHeader("DeptId")) ? Integer.valueOf(request.getHeader("DeptId")) : null;

            if (StrUtil.isBlank(token))
                throw new RuntimeException("未携带token访问");

            LoginUser loginUser = tokenService.getLoginUser(token);

            userService.beforeLoginCheck(loginUser);

            List<Integer> deptIds = loginUser.getDeptIds();
            List<Integer> roleIds = loginUser.getRoleIds();

            List<SimpleGrantedAuthority> list = new ArrayList<>();
            for (String authority : loginUser.getPermissions()) {
                if (StringUtils.hasText(authority)){
                    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
                    list.add(simpleGrantedAuthority);
                }
            }

            // 处理角色
            if (CollectionUtil.isNotEmpty(roleIds)){
                if (roleIds.size() == 1){
                    loginUser.setCurrentRoleId(roleIds.get(0));
                }else {
                    if (roleIds.contains(roleId)){
                        loginUser.setCurrentRoleId(roleId);
                    }else {
                        throw new RuntimeException("无效的roleId");
                    }
                }
            }

            // 处理部门
            if (CollectionUtil.isNotEmpty(deptIds)){
                if (deptIds.size() == 1){
                    loginUser.setCurrentDeptId(deptIds.get(0));
                }else {
                    if (deptIds.contains(deptId)){
                        loginUser.setCurrentDeptId(deptId);
                    }else {
                        throw new RuntimeException("无效的deptId");
                    }
                }
            }

            // 续签token
            tokenService.renewToken(token);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, list);
            authenticationToken.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (Exception e) {
            response.setHeader("content-type", "application/json");
            IoUtil.write(response.getOutputStream(), true, JSONUtil.toJsonStr(Result.error(Result.UNAUTHORIZED, e.getMessage())).getBytes());
            log.error(e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
