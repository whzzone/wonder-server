package com.gitee.whzzone.admin.service.system.impl;

import com.gitee.whzzone.admin.common.base.service.impl.EntityServiceImpl;
import com.gitee.whzzone.admin.mapper.system.RequestLogMapper;
import com.gitee.whzzone.admin.pojo.dto.system.RequestLogDto;
import com.gitee.whzzone.admin.pojo.entity.system.RequestLog;
import com.gitee.whzzone.admin.pojo.query.system.RequestLogQuery;
import com.gitee.whzzone.admin.service.system.RequestLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
* 请求日志 服务实现类
* @author Create by generator at 2023/08/07
*/
@Slf4j
@Service
public class RequestLogServiceImpl extends EntityServiceImpl<RequestLogMapper, RequestLog, RequestLogDto, RequestLogQuery> implements RequestLogService {

    @Async
    @Override
    public void saveAsync(RequestLog requestLog) {
        try {
            log.warn("====================================开始请求========================================");
            //请求用户
            log.warn("请求用户：{}", requestLog.getUserId());
            //请求链接
            log.warn("请求链接：{}", requestLog.getUrl());
            //接口描述信息
            log.warn("接口描述：{}", requestLog.getDesc());
            //请求类型
            log.warn("接口类型：{}", requestLog.getType());
            //请求方法
            log.warn("请求方法：{}", requestLog.getMethod());
            //请求IP
            log.warn("请求IP：{}", requestLog.getIp());
            //请求入参
            log.warn("请求入参：{}", requestLog.getParams());
            //请求耗时
            log.warn("请求耗时：{}", requestLog.getDuration());
            //请求返回
            log.warn("请求返回：{}", requestLog.getResult());
            log.warn("====================================请求结束========================================");

            save(requestLog);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
