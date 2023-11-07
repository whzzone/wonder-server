package com.gitee.whzzone.admin.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gitee.whzzone.common.base.controller.EntityController;
import com.gitee.whzzone.admin.system.entity.Dict;
import com.gitee.whzzone.admin.system.pojo.other.Dict.DictQuery;
import com.gitee.whzzone.admin.system.pojo.other.Dict.DictDto;
import com.gitee.whzzone.admin.system.service.DictService;
import io.swagger.annotations.Api;

/**
* 系统字典 控制器
* @author Create by generator at 2023/8/8
*/
@Api(tags = "系统字典")
@RestController
@RequestMapping("/dict")
public class DictController extends EntityController<Dict, DictService, DictDto, DictQuery> {

}
