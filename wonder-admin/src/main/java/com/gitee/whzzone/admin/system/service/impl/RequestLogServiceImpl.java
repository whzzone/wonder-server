package com.gitee.whzzone.admin.system.service.impl;

import com.gitee.whzzone.admin.system.entity.RequestLog;
import com.gitee.whzzone.admin.system.mapper.RequestLogMapper;
import com.gitee.whzzone.admin.system.pojo.dto.RequestLogDTO;
import com.gitee.whzzone.admin.system.pojo.query.RequestLogQuery;
import com.gitee.whzzone.admin.system.service.RequestLogService;
import com.gitee.whzzone.web.service.impl.EntityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
* 请求日志 服务实现类
* @author Create by generator at 2023/08/07
*/
@Slf4j
@Service
public class RequestLogServiceImpl extends EntityServiceImpl<RequestLogMapper, RequestLog, RequestLogDTO, RequestLogQuery> implements RequestLogService {

}
