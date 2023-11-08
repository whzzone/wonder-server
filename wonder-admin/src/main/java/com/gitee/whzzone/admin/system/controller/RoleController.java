package com.gitee.whzzone.admin.system.controller;

import com.gitee.whzzone.common.base.controller.EntityController;
import com.gitee.whzzone.common.PageData;
import com.gitee.whzzone.admin.system.pojo.dto.RoleDto;
import com.gitee.whzzone.admin.system.entity.Role;
import com.gitee.whzzone.admin.system.pojo.query.RoleQuery;
import com.gitee.whzzone.admin.system.service.RoleService;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : whz
 * @date : 2022/11/30 10:20
 */
@Api(tags = "角色相关")
@RestController
@RequestMapping("role")
public class RoleController extends EntityController<Role, RoleService, RoleDto, RoleQuery> {

    @Autowired
    private RoleService roleService;

    @ApiOperation("列表")
    @PostMapping("list")
    public Result<List<RoleDto>> list(@RequestBody RoleQuery query){
        return Result.ok("操作成功", roleService.list(query));
    }

    @ApiOperation("分页")
    @PostMapping("page")
    public Result<PageData<RoleDto>> page(@Validated @RequestBody RoleQuery query){
        return Result.ok(roleService.page(query));
    }

    @ApiOperation("改变启用状态")
    @GetMapping("/enabledSwitch/{id}")
    public Result enabledSwitch(@PathVariable Long id) {
        roleService.enabledSwitch(id);
        return Result.ok();
    }

    @ApiOperation("绑定规则")
    @GetMapping("/bindingRule")
    public Result bindingRule(Long roleId, Long ruleId) {
        roleService.bindingRule(roleId, ruleId);
        return Result.ok();
    }

    @ApiOperation("取消绑定规则")
    @GetMapping("/unBindingRule")
    public Result unBindingRule(Long roleId, Long ruleId) {
        roleService.unBindingRule(roleId, ruleId);
        return Result.ok();
    }
}
