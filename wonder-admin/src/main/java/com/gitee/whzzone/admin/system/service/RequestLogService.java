package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.web.service.EntityService;
import com.gitee.whzzone.admin.system.pojo.dto.RequestLogDTO;
import com.gitee.whzzone.admin.system.entity.RequestLog;
import com.gitee.whzzone.admin.system.pojo.query.RequestLogQuery;

/**
* 请求日志 服务类
* @author Create by generator at 2023/08/07
*/
public interface RequestLogService extends EntityService<RequestLog, RequestLogDTO, RequestLogQuery> {

    void saveAsync(RequestLog requestLog);

}
