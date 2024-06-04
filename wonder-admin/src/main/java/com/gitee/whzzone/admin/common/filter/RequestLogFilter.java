package com.gitee.whzzone.admin.common.filter;

import com.gitee.whzzone.admin.common.apilog.ApiLogContext;
import com.gitee.whzzone.admin.common.apilog.ApiLogContextHolder;
import com.gitee.whzzone.admin.common.event.HttpAccessEvent;
import com.gitee.whzzone.admin.system.entity.RequestLog;
import com.gitee.whzzone.admin.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 记录请求日志
 */
@Order(-200)
@Component
public class RequestLogFilter extends HttpFilter {

    @Value("server.servlet.context-path")
    private String contextPath;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            //请求开始时间戳
            long begin = System.currentTimeMillis();

            // Wrapper 封装 Request 和 Response
            ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper(request);
            ContentCachingResponseWrapper cachingResponse = new ContentCachingResponseWrapper(response);

            // 继续执行请求链
            chain.doFilter(cachingRequest, cachingResponse);

            //请求结束时间戳
            long end = System.currentTimeMillis();

            // 缓存的请求体
            byte[] requestContent = cachingRequest.getContentAsByteArray();
            // 缓存的响应体
            byte[] responseContent = cachingResponse.getContentAsByteArray();

            ApiLogContext apiLogContext = ApiLogContextHolder.getApiLogContext();
            if (Objects.nonNull(apiLogContext)) {
                RequestLog requestLog = new RequestLog();
                requestLog.setUri(request.getRequestURI().substring(request.getContextPath().length()).replaceAll(contextPath, ""));
                requestLog.setDesc(apiLogContext.getDesc());
                requestLog.setMethod(request.getMethod());
                requestLog.setIp(IpUtil.getIp(request));
                requestLog.setQueryParams(request.getQueryString());
                requestLog.setBodyParams(new String(requestContent));
                requestLog.setDuration(end - begin);
                requestLog.setResult(new String(responseContent));
                requestLog.setUserId(apiLogContext.getUserId());

                // 发布事件
                applicationEventPublisher.publishEvent(new HttpAccessEvent(requestLog));
            }

            // 把缓存的响应数据，响应给客户端
            cachingResponse.copyBodyToResponse();

        } finally {
            ApiLogContextHolder.remove();
        }
    }
}