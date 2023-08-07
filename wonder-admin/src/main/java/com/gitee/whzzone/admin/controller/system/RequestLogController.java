package com.gitee.whzzone.admin.controller.system;

import com.gitee.whzzone.admin.common.base.controller.EntityController;
import com.gitee.whzzone.admin.pojo.dto.system.RequestLogDto;
import com.gitee.whzzone.admin.pojo.entity.system.RequestLog;
import com.gitee.whzzone.admin.pojo.query.system.RequestLogQuery;
import com.gitee.whzzone.admin.service.system.RequestLogService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* 请求日志 控制器
* @author Create by generator at 2023/08/07
*/
@Api(tags = "请求日志")
@RestController
@RequestMapping("/requestLog")
public class RequestLogController extends EntityController<RequestLog, RequestLogService, RequestLogDto, RequestLogQuery> {

}
