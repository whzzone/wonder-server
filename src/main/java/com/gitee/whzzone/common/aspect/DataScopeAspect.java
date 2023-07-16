package com.gitee.whzzone.common.aspect;

import com.gitee.whzzone.common.annotation.DataScope;
import com.gitee.whzzone.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.service.MarkService;
import com.gitee.whzzone.util.SecurityUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Create by whz at 2023/6/10
 */
@Aspect
@Slf4j
//@Order(1)
@Component
public class DataScopeAspect {

    @Autowired
    private MarkService dataScopeService;

    // 通过ThreadLocal记录权限相关的属性值
    public static ThreadLocal<DataScopeParam> threadLocal = new ThreadLocal<>();

    public static DataScopeParam getDataScopeParam() {
        return threadLocal.get();
    }

    // 方法切点
    @Pointcut("@annotation(com.gitee.whzzone.common.annotation.DataScope)")
    public void methodPointCut() {}

    @After("methodPointCut()")
    public void clearThreadLocal() {
        threadLocal.remove();
        log.debug("threadLocal.remove()");
    }

    @Before("methodPointCut()")
    public void doBefore(JoinPoint point) {

        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        // 获得注解
        DataScope dataScope = method.getAnnotation(DataScope.class);

        try {
            if (dataScope != null && !SecurityUtil.isAdmin()) {
                String scopeName = dataScope.value();

                DataScopeInfo dataScopeInfo = dataScopeService.execRuleByName(scopeName);

                DataScopeParam dataScopeParam = new DataScopeParam();

                dataScopeParam.setDataScopeInfo(dataScopeInfo);

                threadLocal.set(dataScopeParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据权限 method 切面错误：" + e.getMessage());
        }

    }

    @Data
    public static class DataScopeParam {
        private DataScopeInfo dataScopeInfo;
    }

    // 形参切点
    @Pointcut("execution(* *(.., @com.gitee.whzzone.common.annotation.DataScope (*), ..))")
    public void parameterPointCut() {}

    @Around("parameterPointCut()")
    public Object doAround(ProceedingJoinPoint point) {
        try {
            Object[] args = point.getArgs();
            MethodSignature methodSignature = (MethodSignature) point.getSignature();
            Annotation[][] parameterAnnotations = methodSignature.getMethod().getParameterAnnotations();
            // 遍历方法的参数注解和参数类型
            for (int i = 0; i < parameterAnnotations.length; i++) {
                Annotation[] annotations = parameterAnnotations[i];
                Class<?> parameterType = methodSignature.getParameterTypes()[i];

                int index = -1;

                for (int k = 0; k < annotations.length; k++) {
                    if (annotations[k] instanceof DataScope) {
                        index = k;
                        break;
                    }
                }

                if (index >= 0){
                    if (parameterType != DataScopeInfo.class){
                        throw new RuntimeException("使用@DataScope的参数类型必须是：DataScopeInfo.class 类型");
                    }
                    DataScope dataScope = (DataScope) annotations[index];
                    String scopeName = dataScope.value();
                    DataScopeInfo dataScopeInfo = dataScopeService.execRuleByName(scopeName);
                    args[i] = dataScopeInfo;
                }

            }

            // 继续执行目标方法
            return point.proceed(args);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据权限 形参 切面错误：" + e.getMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

}
