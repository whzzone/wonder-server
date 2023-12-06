package com.gitee.whzzone.admin.common.aspect;

import com.gitee.whzzone.common.annotation.CacheableTTL;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Map;

/**
 * Create by whz at 2023/6/10
 */
@Aspect
@Slf4j
@Component
@Order(1)
public class CacheableTTLAspect {

    // 方法切点
    @Pointcut("@annotation(com.gitee.whzzone.common.annotation.CacheableTTL)")
    public void methodPointCut() {
    }

    @Before("methodPointCut()")
    public void doBefore(JoinPoint point) throws NoSuchFieldException, IllegalAccessException {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        // 获得注解
        CacheableTTL cacheableTTLAnnot = method.getAnnotation(CacheableTTL.class);
        Cacheable cacheableAnnot = method.getAnnotation(Cacheable.class);

        InvocationHandler invocationHandler = Proxy.getInvocationHandler(cacheableAnnot);
        Field memberValuesField = invocationHandler.getClass().getDeclaredField("memberValues");
        memberValuesField.setAccessible(true);
        Map memberValuesMap = (Map) memberValuesField.get(invocationHandler);
//        for (String string : memberValuesMap) {
//            string = "aaaa20";
//        }
        memberValuesMap.put("value", new String[]{"aaaa20"});

        System.out.println("----- " + Arrays.toString(cacheableAnnot.value()));
    }

}
