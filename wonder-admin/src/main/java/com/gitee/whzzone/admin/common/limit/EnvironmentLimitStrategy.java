package com.gitee.whzzone.admin.common.limit;

import cn.hutool.crypto.digest.DigestUtil;
import com.gitee.whzzone.admin.util.IpUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 根据网络环境限流策略
 * @author Create by whz at 2023/9/28
 */
@Component
public class EnvironmentLimitStrategy implements LimitStrategy{

    @Override
    public String apply() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = IpUtil.getIp(request);
        String userAgent = request.getHeader("user-agent");
        int clientPort = request.getRemotePort();
        return request.getRequestURI() + ":" + DigestUtil.md5Hex(ip + userAgent + clientPort);
    }

}
