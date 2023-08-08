package com.gitee.whzzone.admin.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gitee.whzzone.admin.common.base.controller.EntityController;
import com.gitee.whzzone.admin.system.entity.DictData;
import com.gitee.whzzone.admin.system.pojo.other.DictData.DictDataQuery;
import com.gitee.whzzone.admin.system.pojo.other.DictData.DictDataDto;
import com.gitee.whzzone.admin.system.service.DictDataService;
import io.swagger.annotations.Api;

/**
* 系统字典数据 控制器
* @author Create by generator at 2023/8/8
*/
@Api(tags = "系统字典数据")
@RestController
@RequestMapping("/dictData")
public class DictDataController extends EntityController<DictData, DictDataService, DictDataDto, DictDataQuery> {

}
