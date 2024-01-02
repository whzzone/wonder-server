package com.gitee.whzzone.admin.system.controller;

import com.gitee.whzzone.web.controller.EntityController;
import com.gitee.whzzone.admin.system.pojo.dto.RequestLogDto;
import com.gitee.whzzone.admin.system.entity.RequestLog;
import com.gitee.whzzone.admin.system.pojo.query.RequestLogQuery;
import com.gitee.whzzone.admin.system.service.RequestLogService;
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
