package com.gitee.whzzone.admin.common.limit;

import com.gitee.whzzone.admin.util.IpUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 根据IP限流策略
 * @author Create by whz at 2023/9/28
 */
@Component
public class IPLimitStrategy implements LimitStrategy {

    @Override
    public String apply() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getRequestURI() + ":" + IpUtil.getIp(request);
    }

}
