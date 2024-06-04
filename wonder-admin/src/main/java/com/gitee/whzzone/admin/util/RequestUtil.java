package com.gitee.whzzone.admin.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Create by whz at 2023/12/14
 */
@Slf4j
public class RequestUtil {

    /**
     * 默认用这个
     */
    public static String getDomain(HttpServletRequest request)
    {
        StringBuffer url = request.getRequestURL();
        // 构建最终的 URL
        return url.substring(0, url.indexOf(request.getServletPath()));
    }

    /**
     * nginx转发用这个，nginx要配置 proxy_set_header X-Forwarded-Proto $scheme;
     * 也可以配置nginx强制https
     */
    public static String getDomainNginx(HttpServletRequest request)
    {
        StringBuffer url = request.getRequestURL();

        // 获取 X-Forwarded-Proto 头部的值
        String forwardedProto = request.getHeader("X-Forwarded-Proto");

        // 使用 X-Forwarded-Proto 的值来确定协议
        String protocol = "http"; // 默认为 http
        if ("https".equalsIgnoreCase(forwardedProto)) {
            protocol = "https";
        }

        // 构建最终的 URL
        return protocol + url.substring(url.indexOf("://"), url.indexOf(request.getServletPath()));
    }

//    /**
//     * nginx转发用这个，nginx要配置 proxy_set_header X-Forwarded-Proto $scheme;
//     */
//    public static String getDomainNginx(HttpServletRequest request)
//    {
//        StringBuffer url = request.getRequestURL();
//        String uri = request.getRequestURI();
//        String contextPath = request.getServletContext().getContextPath();
//
//        // 获取 X-Forwarded-Proto 头部的值
//        String forwardedProto = request.getHeader("X-Forwarded-Proto");
//
//        // 使用 X-Forwarded-Proto 的值来确定协议
//        String protocol = "http"; // 默认为 http
//        if ("https".equalsIgnoreCase(forwardedProto)) {
//            protocol = "https";
//        }
//
//        // 构建最终的 URL
//        return protocol + url.substring(url.indexOf("://"), url.indexOf(uri)) + contextPath;
//    }

    public static String getViewPrefix() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return getDomainNginx(request) + "/file/view/";
    }

}
