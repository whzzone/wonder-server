package com.gitee.whzzone.admin.system.controller;

import com.gitee.whzzone.admin.system.entity.Dept;
import com.gitee.whzzone.admin.system.pojo.dto.DeptDto;
import com.gitee.whzzone.admin.system.pojo.query.DeptQuery;
import com.gitee.whzzone.admin.system.service.DeptService;
import com.gitee.whzzone.web.controller.EntityController;
import com.gitee.whzzone.web.pojo.other.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Create by whz at 2023/7/8
 */

@Api(tags = "部门相关")
@RestController
@RequestMapping("dept")
public class DeptController extends EntityController<Dept, DeptService, DeptDto, DeptQuery> {

    @Autowired
    private DeptService deptService;

    @ApiOperation("列表")
    @GetMapping("list")
    public Result<List<DeptDto>> list(DeptQuery query) {
        return Result.ok("", deptService.list(query));
    }

    @ApiOperation("树")
    @GetMapping("tree")
    public Result<List<DeptDto>> tree(DeptQuery query) {
        return Result.ok(deptService.tree(query));
    }

    @ApiOperation("改变启用状态")
    @PutMapping("/enabledSwitch/{id}")
    public Result enabledSwitch(@PathVariable Integer id) {
        deptService.enabledSwitch(id);
        return Result.ok();
    }

}
