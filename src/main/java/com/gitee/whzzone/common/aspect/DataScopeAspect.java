package com.gitee.whzzone.common.aspect;

import cn.hutool.core.collection.CollectionUtil;
import com.gitee.whzzone.common.annotation.DataScope;
import com.gitee.whzzone.service.DataScopeService;
import com.gitee.whzzone.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Create by whz at 2023/6/10
 */
@Aspect
@Slf4j
@Component
public class DataScopeAspect {

    @Autowired
    private DataScopeService dataScopeService;

    // 通过ThreadLocal记录权限相关的属性值
    public static ThreadLocal<DataScopeParam> threadLocal = new ThreadLocal<>();

    public static DataScopeParam getDataScopeParam() {
        return threadLocal.get();
    }

    // 切点
    @Pointcut("@annotation(com.gitee.whzzone.common.annotation.DataScope)")
    public void dataScopePointCut() {
    }

    @After("dataScopePointCut()")
    public void clearThreadLocal() {
        threadLocal.remove();
        log.debug("threadLocal.remove()");
    }

    @Before("dataScopePointCut()")
    public void doBefore(JoinPoint point) {
        try {
            Signature signature = point.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            // 获得注解
            DataScope dataScope = method.getAnnotation(DataScope.class);

            if (dataScope != null && !SecurityUtil.isAdmin()) {
                String scopeName = dataScope.value();
                com.gitee.whzzone.pojo.entity.DataScope dataScopeEntity = dataScopeService.findByName(scopeName);
                if (dataScopeEntity == null)
                    throw new RuntimeException("数据规则不存在：" + scopeName);

                // todo 需要判断provideType

                Class<?> clazz = Class.forName(dataScopeEntity.getClassName());
                Method targetMethod = clazz.getDeclaredMethod(dataScopeEntity.getMethodName());

                // 数据范围ids
                List<Long> scopeList;

                if (Modifier.isStatic(targetMethod.getModifiers())) {
                    // 设置静态方法可访问
                    targetMethod.setAccessible(true);
                    // 执行静态方法
                    scopeList = (List<Long>) targetMethod.invoke(null);
                } else {
                    // 创建类实例
                    Object obj = clazz.newInstance();
                    // 执行方法
                    scopeList = (List<Long>) targetMethod.invoke(obj);
                }

                DataScopeParam dataScopeParam = new DataScopeParam();
                dataScopeParam.setField(dataScopeEntity.getColumnName());
                dataScopeParam.setTableAlias(dataScopeEntity.getTableAlias());

                if (CollectionUtil.isEmpty(scopeList))
                    throw new RuntimeException("没有查看权限：数据权限为0");

                dataScopeParam.setSecretary(scopeList);

                threadLocal.set(dataScopeParam);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据权限切面错误：" + e.getMessage());
        }

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DataScopeParam {
        private String tableAlias;
        private String field;
        private List<Long> secretary;
    }

}
