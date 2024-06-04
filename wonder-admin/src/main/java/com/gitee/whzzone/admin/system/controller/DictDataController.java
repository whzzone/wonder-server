package com.gitee.whzzone.admin.system.controller;

import com.gitee.whzzone.admin.system.entity.DictData;
import com.gitee.whzzone.admin.system.pojo.other.DictData.DictDataDTO;
import com.gitee.whzzone.admin.system.pojo.other.DictData.DictDataQuery;
import com.gitee.whzzone.admin.system.service.DictDataService;
import com.gitee.whzzone.annotation.ApiLogger;
import com.gitee.whzzone.web.controller.EntityController;
import com.gitee.whzzone.web.pojo.other.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* 系统字典数据 控制器
* @author Create by generator at 2023/8/8
*/
@Api(tags = "系统字典数据")
@RestController
@RequestMapping("/dictData")
public class DictDataController extends EntityController<DictData, DictDataService, DictDataDTO, DictDataQuery> {

    @Autowired
    private DictDataService dictDataService;

    @ApiLogger
    @ApiOperation("根据dictCode查询字典数据")
    @GetMapping("/dictCode/{dictCode}")
    public Result<List<DictDataDTO>> findByDictCode(@PathVariable String dictCode) {
        return Result.ok(dictDataService.findByDictCode(dictCode));
    }

}
