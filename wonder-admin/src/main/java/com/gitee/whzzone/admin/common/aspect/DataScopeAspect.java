package com.gitee.whzzone.admin.common.aspect;

import com.gitee.whzzone.admin.system.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.admin.system.pojo.dto.RuleDto;
import com.gitee.whzzone.admin.system.service.MarkService;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.common.annotation.DataScope;
import com.gitee.whzzone.common.exception.NoDataException;
import lombok.Data;
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
    public static ThreadLocal<DataScopeParam> threadLocal = new ThreadLocal<>();

    public static DataScopeParam getDataScopeParam() {
        return threadLocal.get();
    }

    // 方法切点
    @Pointcut("@annotation(com.gitee.whzzone.common.annotation.DataScope)")
    public void methodPointCut() {
    }

    @After("methodPointCut()")
    public void clearThreadLocal() {
        threadLocal.remove();
        log.debug("----------------数据权限信息清除----------------");
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

                log.debug("----------------设置数据权限信息----------------");
                if (dataScopeInfo.getRuleList() != null && dataScopeInfo.getRuleList().size() > 0) {
                    for (RuleDto rule : dataScopeInfo.getRuleList()) {
                        log.debug("- ruleId：{}", rule.getId());
                    }
                }

            }
        } catch (NoDataException e) {
            throw new NoDataException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据权限切面错误：" + e.getMessage());
        }
    }

    @Data
    public static class DataScopeParam {
        private DataScopeInfo dataScopeInfo;
    }

}
