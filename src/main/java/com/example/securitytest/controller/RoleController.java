package com.example.securitytest.controller;

import com.example.securitytest.pojo.dto.RoleDto;
import com.example.securitytest.pojo.entity.Role;
import com.example.securitytest.pojo.query.RoleQuery;
import com.example.securitytest.pojo.vo.PageData;
import com.example.securitytest.service.RoleService;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : whz
 * @date : 2022/11/30 10:20
 */
@Api(tags = "角色相关")
@RestController
@RequestMapping("role")
public class RoleController extends EntityController<Role, RoleService, RoleDto> {

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
}
