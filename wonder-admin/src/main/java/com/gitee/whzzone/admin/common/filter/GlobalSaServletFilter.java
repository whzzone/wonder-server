package com.gitee.whzzone.admin.common.filter;

import cn.dev33.satoken.filter.SaServletFilter;
import com.gitee.whzzone.admin.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author Create by whz at 2024/2/1
 */
@Slf4j
public class GlobalSaServletFilter extends SaServletFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("--------------------------经过全局过滤器前");
        super.doFilter(request, response, chain);
        log.debug("--------------------------经过全局过滤器后");
        SecurityUtil.clearLoginUser();
    }
}
