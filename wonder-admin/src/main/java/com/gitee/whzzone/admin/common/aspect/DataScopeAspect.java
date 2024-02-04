package com.gitee.whzzone.admin.common.aspect;

import cn.hutool.core.collection.CollectionUtil;
import com.gitee.whzzone.admin.common.mybatis.DataScopeRule;
import com.gitee.whzzone.admin.system.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.admin.system.service.MarkService;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.annotation.DataScope;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Create by whz at 2023/6/10
 */
@Aspect
@Slf4j
@Component
public class DataScopeAspect {

    @Autowired
    private MarkService dataScopeService;

    // 通过ThreadLocal记录权限相关的属性值
    private static ThreadLocal<DataScopeInfo> threadLocal = new ThreadLocal<>();
    private static ThreadLocal<Boolean> methodProcessed = new ThreadLocal<>();

    public static DataScopeInfo getDataScopeInfo() {
        return threadLocal.get();
    }

    // 方法切点
    @Pointcut("@annotation(com.gitee.whzzone.annotation.DataScope)")
    public void methodPointCut() {
    }

    @After("methodPointCut()")
    public void clearThreadLocal() {
        if (methodProcessed.get() != null) {
            return;
        }
        threadLocal.remove();
        methodProcessed.remove();
        log.debug("----------------数据权限信息清除----------------");
    }

    @Before("methodPointCut()")
    public void doBefore(JoinPoint point) {
        if (methodProcessed.get() != null && methodProcessed.get()) {
            return;
        } else {
            methodProcessed.set(true);
        }
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        // 获得注解
        DataScope dataScope = method.getAnnotation(DataScope.class);

        try {
            if (dataScope != null && !SecurityUtil.isAdmin()) {
                String scopeName = dataScope.value();
                DataScopeInfo dataScopeInfo = dataScopeService.execRuleByName(scopeName);
                threadLocal.set(dataScopeInfo);
                printDebug(method ,dataScopeInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据权限切面错误：" + e.getMessage());
        }
    }

    private void printDebug(Method method, DataScopeInfo dataScopeInfo) {
        if (CollectionUtil.isNotEmpty(dataScopeInfo.getRuleList())) {
            log.debug("-----{}#{} 当前绑定规则开始-----", method.getDeclaringClass(), method.getName());
            for (DataScopeRule rule : dataScopeInfo.getRuleList()) {
                log.debug("- markId: {}, ruleId：{}, ruleName：{}, expression: {}", rule.getMarkId(), rule.getId(), rule.getRemark(), rule.getExpression());
            }
            log.debug("-----{}#{} 当前绑定规则结束-----", method.getDeclaringClass(), method.getName());
        }
    }

}
