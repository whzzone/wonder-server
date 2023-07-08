package com.example.securitytest.controller;

import com.example.securitytest.common.validation.group.CreateGroup;
import com.example.securitytest.common.validation.group.UpdateGroup;
import com.example.securitytest.pojo.dto.RoleDto;
import com.example.securitytest.pojo.query.RoleQuery;
import com.example.securitytest.pojo.vo.IdAndNameVo;
import com.example.securitytest.pojo.vo.PageData;
import com.example.securitytest.service.RoleService;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : whz
 * @date : 2022/11/30 10:20
 */
@Api(tags = "角色相关")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("添加角色")
    @PostMapping("/save")
    public Result save(@Validated(CreateGroup.class) @RequestBody RoleDto dto) {
        roleService.save(dto);
        return Result.ok("操作成功");
    }

    @ApiOperation("编辑角色")
//    @PreAuthorize("hasAuthority('sys:role:edit')")
    @PostMapping("/update")
    public Result edit(@Validated(UpdateGroup.class) @RequestBody RoleDto dto) {
        roleService.updateById(dto);
        return Result.ok("操作成功");
    }

    @ApiOperation("编辑角色的菜单")
    //    @PreAuthorize("hasAuthority('sys:role:edit')")
    @PostMapping("/updateRoleMenu")
    public Result updateRoleMenu(@RequestBody RoleDto dto) {
        roleService.updateRoleMenu(dto);
        return Result.ok("操作成功");
    }

//
    @ApiOperation("删除角色")
    @PreAuthorize("hasAuthority('sys:role:del')")
    @GetMapping("/del/{id}")
    public Result del(@PathVariable Long id){
        roleService.removeById(id);
        return Result.ok("操作成功");
    }
//
    @ApiOperation("查询所有角色")
//    @PreAuthorize("hasAuthority('sys:role:view')")
    @PostMapping("/getAll")
    public Result<List<IdAndNameVo>> getAll(){
        return Result.ok("操作成功", roleService.getAll());
    }

    @ApiOperation("根据id查找角色")
    @PostMapping("/get/{id}")
    public Result<RoleDto> findById(@PathVariable Long id){
        return Result.ok("操作成功", roleService.getDtoById(id));
    }


    @ApiOperation("分页查询角色")
//    @PreAuthorize("hasAuthority('sys:role:view')")
    @PostMapping("/list")
    public Result<PageData<RoleDto>> list(@Validated @RequestBody RoleQuery query){
        return Result.ok(roleService.findPage(query));
    }
}
