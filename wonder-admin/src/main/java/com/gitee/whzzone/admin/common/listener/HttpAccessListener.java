package com.gitee.whzzone.admin.common.listener;

import com.gitee.whzzone.admin.common.event.HttpAccessEvent;
import com.gitee.whzzone.admin.system.entity.RequestLog;
import com.gitee.whzzone.admin.system.service.RequestLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

/**
 * @author Create by whz at 2024/1/29
 */
@Slf4j
@ConditionalOnProperty(prefix = "wonder.web", name = "enable-log", havingValue = "true")
@Configuration
public class HttpAccessListener implements ApplicationListener<HttpAccessEvent> {

    @Autowired
    private RequestLogService requestLogService;

    @Override
    public void onApplicationEvent(HttpAccessEvent event) {
        try {
            RequestLog requestLog = (RequestLog) event.getSource();
            log.debug("====================================请求信息开始========================================");
            //请求用户
            log.debug("请求用户：{}", requestLog.getUserId());
            //请求链接
            log.debug("请求URI：{}", requestLog.getUri());
            //接口描述信息
            log.debug("接口描述：{}", requestLog.getDesc());
            //请求方法
            log.debug("请求方法：{}", requestLog.getMethod());
            //请求IP
            log.debug("请求IP：{}", requestLog.getIp());
            //请求入参
            log.debug("请求query入参：{}", requestLog.getQueryParams());
            //请求入参
            log.debug("请求body入参：{}", requestLog.getBodyParams());
            //请求耗时
            log.debug("请求耗时：{}", requestLog.getDuration());
            //请求返回
            // log.debug("请求返回：{}", requestLog.getResult());
            log.debug("====================================请求信息结束========================================");
            requestLogService.save(requestLog);
        }catch (Exception e) {
            log.error("保存请求日志异常：{}", e.getMessage());
        }
    }
}
