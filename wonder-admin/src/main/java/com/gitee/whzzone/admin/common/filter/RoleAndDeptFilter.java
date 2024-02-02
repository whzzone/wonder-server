package com.gitee.whzzone.admin.common.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.gitee.whzzone.admin.common.properties.WonderProperties;
import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.web.pojo.dto.EntityDTO;
import com.gitee.whzzone.web.pojo.other.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : whz
 * @date : 2023/5/17 17:46
 */
@Slf4j
@Order(-80)
@Component
public class RoleAndDeptFilter extends OncePerRequestFilter {

    @Autowired
    private WonderProperties wonderProperties;

    @Autowired
    private AntPathMatcher antPathMatcher;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.debug("----------------经过 RoleAndDeptFilter 前");

            String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
            for (String ignorePath : wonderProperties.getWeb().getIgnorePath()) {
                if (antPathMatcher.match(ignorePath, path)) {
                    // 如果当前请求的 URL 在忽略列表中，则直接放行
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            Integer deptId = StringUtils.hasText(request.getHeader("DeptId")) ? Integer.valueOf(request.getHeader("DeptId")) : null;

            LoginUser loginUser = SecurityUtil.getLoginUser();

            List<Integer> deptIds = loginUser.getDepts().stream().map(EntityDTO::getId).collect(Collectors.toList());

            // 处理部门
            if (CollectionUtil.isNotEmpty(deptIds)) {
                if (deptIds.size() == 1) {
                    loginUser.setCurrentDeptId(deptIds.get(0));
                } else {
                    if (deptIds.contains(deptId)) {
                        loginUser.setCurrentDeptId(deptId);
                    } else {
                        throw new RuntimeException("无效的deptId");
                    }
                }
            }

            filterChain.doFilter(request, response);

            log.debug("----------------经过 RoleAndDeptFilter 后");
        }catch (Exception e) {
            response.setHeader("content-type", "application/json");
            IoUtil.write(response.getOutputStream(), true, JSONUtil.toJsonStr(Result.error(Result.UNAUTHORIZED, e.getMessage())).getBytes());
            log.error("RoleAndDeptFilter error: {}", e.getMessage());
        }
    }
}
