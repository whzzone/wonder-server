package com.gitee.whzzone.admin.common.limit;

import com.gitee.whzzone.admin.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 根据用户id限流策略
 * @author Create by whz at 2023/9/28
 */
@Component
public class UserIdLimitStrategy implements LimitStrategy{

    @Autowired
    private EnvironmentLimitStrategy environmentLimitStrategy;

    @Override
    public String apply() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return request.getRequestURI() + ":" + SecurityUtil.getLoginUser().getId().toString();
        }catch (Exception e) {
            return environmentLimitStrategy.apply();
        }
    }
}
