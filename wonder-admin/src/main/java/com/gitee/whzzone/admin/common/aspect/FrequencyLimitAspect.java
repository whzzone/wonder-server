package com.gitee.whzzone.admin.common.aspect;

import cn.hutool.extra.spring.SpringUtil;
import com.gitee.whzzone.admin.common.exception.FrequencyLimitException;
import com.gitee.whzzone.admin.common.limit.LimitStrategy;
import com.gitee.whzzone.admin.common.redis.RedisCache;
import com.gitee.whzzone.admin.common.limit.FrequencyLimit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Aspect
@Configuration
public class FrequencyLimitAspect {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private Map<String, LimitStrategy> strategyMap;

    private static final String KEY_FORMAT = "frequency_limit:{}_last_time";

    @Pointcut("@annotation(com.gitee.whzzone.admin.common.limit.FrequencyLimit)")
    public void limit() {}

    // 在方法执行前检查用户请求次数和时间间隔
    @Before("limit()")
    public void before(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        FrequencyLimit limit = method.getAnnotation(FrequencyLimit.class);
        if (!limit.enabled())
            return;

        LimitStrategy limitStrategy;
        try {
            limitStrategy = SpringUtil.getBean(limit.strategy());
        } catch (Exception e) {
            try {
                limitStrategy = limit.strategy().newInstance();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        // 策略获取唯一标识
        String flag = "frequency_limit:" + limitStrategy.apply();

        // 获取用户最后一次访问该接口的时间
        String lastTimeStr = "_last_time";

        Long lastTime = (Long) redisCache.get(flag + lastTimeStr);
        lastTime = lastTime == null ? 0L : lastTime;

        // 如果用户访问该接口的时间间隔小于限制的时间间隔，则拒绝访问
        if (System.currentTimeMillis() - lastTime < limit.interval() * 1000) {
            throw new FrequencyLimitException(limit.message());
        }

        // 记录用户访问该接口的时间和次数
        redisCache.set(flag + lastTimeStr, System.currentTimeMillis(), 10, TimeUnit.MINUTES);
    }

}
