package com.gitee.whzzone.admin.system.controller;

import com.gitee.whzzone.admin.system.entity.Role;
import com.gitee.whzzone.admin.system.pojo.dto.RoleDTO;
import com.gitee.whzzone.admin.system.pojo.query.RoleQuery;
import com.gitee.whzzone.admin.system.service.RoleService;
import com.gitee.whzzone.web.controller.EntityController;
import com.gitee.whzzone.web.pojo.other.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : whz
 * @date : 2022/11/30 10:20
 */
@Api(tags = "角色相关")
@RestController
@RequestMapping("role")
public class RoleController extends EntityController<Role, RoleService, RoleDTO, RoleQuery> {

    @Autowired
    private RoleService roleService;

    @ApiOperation("改变启用状态")
    @PutMapping("/enabledSwitch/{id}")
    public Result enabledSwitch(@PathVariable Integer id) {
        roleService.enabledSwitch(id);
        return Result.ok();
    }

    @ApiOperation("绑定规则")
    @PutMapping("/bindingRule")
    public Result bindingRule(Integer roleId, Integer ruleId) {
        roleService.bindingRule(roleId, ruleId);
        return Result.ok();
    }

    @ApiOperation("取消绑定规则")
    @PutMapping("/unBindingRule")
    public Result unBindingRule(Integer roleId, Integer ruleId) {
        roleService.unBindingRule(roleId, ruleId);
        return Result.ok();
    }
}
