package com.gitee.whzzone.admin.common.aspect;

import cn.hutool.core.collection.CollectionUtil;
import com.gitee.whzzone.admin.common.apilog.ApiLogContext;
import com.gitee.whzzone.admin.common.apilog.ApiLogContextHolder;
import com.gitee.whzzone.admin.common.listener.HttpAccessListener;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.annotation.ApiLogger;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;

/**
 * @author Create by whz at 2023/8/7
 */
@Aspect
@Slf4j
@Component
public class RequestLoggerAspect {

    @Autowired
    private ApplicationContext applicationContext;

    private Boolean enableLog;

    @Pointcut("@annotation(com.gitee.whzzone.annotation.ApiLogger)")
    public void pointcut() {
    }

    @PostConstruct
    public void init() {
        enableLog = CollectionUtil.isNotEmpty(applicationContext.getBeansOfType(HttpAccessListener.class));
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        if (!enableLog) {
            return joinPoint.proceed();
        }

        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        ApiLogger apiLogger = method.getAnnotation(ApiLogger.class);

        if (!apiLogger.enabled()) {
            return joinPoint.proceed();
        }

        // RequestLogger注解的接口描述信息
        ApiLogContext apiLogContext = getApiLogContext(apiLogger, method);
        ApiLogContextHolder.setApiLogContext(apiLogContext);

        return joinPoint.proceed();
    }

    @NotNull
    private static ApiLogContext getApiLogContext(ApiLogger apiLogger, Method method) {
        String desc = apiLogger.value();

        if (desc.isEmpty()) {
            // 没有就取@ApiOperation的描述信息，避免重复写描述
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            if (apiOperation != null) {
                desc = apiOperation.value();
            }
        }

        // 设置自定义的日志信息
        ApiLogContext apiLogContext = new ApiLogContext();
        apiLogContext.setDesc(desc);
        try {
            apiLogContext.setUserId(SecurityUtil.getLoginUser() == null ? null :SecurityUtil.getLoginUser().getId());
        }catch (Exception ignored){}

        return apiLogContext;
    }

}
