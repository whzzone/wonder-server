package com.gitee.whzzone.admin.system.service;

import com.gitee.whzzone.common.base.service.EntityService;
import com.gitee.whzzone.admin.system.pojo.dto.RequestLogDto;
import com.gitee.whzzone.admin.system.entity.RequestLog;
import com.gitee.whzzone.admin.system.pojo.query.RequestLogQuery;

/**
* 请求日志 服务类
* @author Create by generator at 2023/08/07
*/
public interface RequestLogService extends EntityService<RequestLog, RequestLogDto, RequestLogQuery> {

    void saveAsync(RequestLog requestLog);

}
