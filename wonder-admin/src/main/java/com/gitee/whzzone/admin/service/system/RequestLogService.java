package com.gitee.whzzone.admin.service.system;

import com.gitee.whzzone.admin.common.base.service.EntityService;
import com.gitee.whzzone.admin.pojo.dto.system.RequestLogDto;
import com.gitee.whzzone.admin.pojo.entity.system.RequestLog;
import com.gitee.whzzone.admin.pojo.query.system.RequestLogQuery;

/**
* 请求日志 服务类
* @author Create by generator at 2023/08/07
*/
public interface RequestLogService extends EntityService<RequestLog, RequestLogDto, RequestLogQuery> {

    void saveAsync(RequestLog requestLog);

}
