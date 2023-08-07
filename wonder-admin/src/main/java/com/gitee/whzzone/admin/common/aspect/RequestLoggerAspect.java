package com.gitee.whzzone.admin.common.aspect;

import com.alibaba.fastjson.JSON;
import com.gitee.whzzone.admin.pojo.entity.system.RequestLog;
import com.gitee.whzzone.admin.service.system.RequestLogService;
import com.gitee.whzzone.admin.util.IpUtil;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.common.annotation.RequestLogger;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author Create by whz at 2023/8/7
 */
@Aspect
@Slf4j
@Component
public class RequestLoggerAspect {

    @Autowired
    private RequestLogService requestLogService;

    @Value("server.servlet.context-path")
    private String contextPath;

    @Pointcut("@annotation(com.gitee.whzzone.common.annotation.RequestLogger)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //请求开始时间戳
        long begin = System.currentTimeMillis();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        RequestLogger requestLogger = method.getAnnotation(RequestLogger.class);

        // RequestLogger注解的接口描述信息
        String desc = requestLogger.value();

        if (desc.isEmpty()) {
            // 没有就取@ApiOperation的描述信息，避免重复写描述
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                desc = apiOperation.value();
            }
        }

        Object result = joinPoint.proceed();

        //请求结束时间戳
        long end = System.currentTimeMillis();

        RequestLog requestLog = new RequestLog();
        Result r = (Result) result;
        requestLog.setCode(r.getCode());
        requestLog.setUrl(request.getRequestURI().substring(request.getContextPath().length()).replaceAll(contextPath, ""));
        requestLog.setDesc(desc);
        requestLog.setType(request.getMethod());
        requestLog.setMethod(signature.getDeclaringTypeName() + '.' + signature.getName());
        requestLog.setIp(IpUtil.getIp(request));
        requestLog.setParams(JSON.toJSONString(joinPoint.getArgs()));
        requestLog.setDuration(end - begin);
        requestLog.setResult(JSON.toJSONString(result));
        try {
            requestLog.setUserId(SecurityUtil.getLoginUser() == null ? null :SecurityUtil.getLoginUser().getId());
        }catch (Exception ignored){}
        // 异步保存
        requestLogService.saveAsync(requestLog);

        return result;
    }

}
