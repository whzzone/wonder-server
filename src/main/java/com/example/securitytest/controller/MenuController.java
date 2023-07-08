package com.example.securitytest.controller;

import com.example.securitytest.pojo.dto.MenuDto;
import com.example.securitytest.pojo.dto.MenuTreeDto;
import com.example.securitytest.pojo.query.MenuQuery;
import com.example.securitytest.service.MenuService;
import com.example.securitytest.util.SecurityUtil;
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
@Api(tags = "菜单相关")
@RestController
@RequestMapping("/menu")
@Validated
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation("添加菜单")
//    @PreAuthorize("hasAuthority('sys:role:add')")
    @PostMapping("/save")
    public Result save(@Validated @RequestBody MenuDto dto){
        menuService.save(dto);
        return Result.ok("操作成功");
    }
//
    @ApiOperation("编辑菜单")
//    @PreAuthorize("hasAuthority('sys:role:edit')")
    @PostMapping("/updateById")
    public Result updateById(@Validated @RequestBody MenuDto dto){
        menuService.updateById(dto);
        return Result.ok("操作成功");
    }

    @ApiOperation("删除菜单")
//    @PreAuthorize("hasAuthority('sys:role:del')")
    @GetMapping("/delete/{id}")
    public Result del(@PathVariable Long id){
        menuService.removeById(id);
        return Result.ok("操作成功");
    }

    @ApiOperation("根据id查找")
    @GetMapping("/get/{id}")
    public Result<MenuDto> findById(@PathVariable Long id){
        return Result.ok("操作成功", menuService.getDtoById(id));
    }

    @ApiOperation("查询树形菜单列表")
//    @PreAuthorize("hasAuthority('sys:menu:view')")
    @PostMapping("/treeList")
    public Result<List<MenuTreeDto>> treeList(@RequestBody MenuQuery query){
        return Result.ok("操作成功", menuService.treeList(query));
    }

    @ApiOperation("查询菜单列表")
    //    @PreAuthorize("hasAuthority('sys:menu:view')")
    @PostMapping("/list")
    public Result<List<MenuTreeDto>> list(@RequestBody MenuQuery query){
        return Result.ok("操作成功", menuService.list(query));
    }

    @ApiOperation("获取用户菜单-包含路由、权限")
    //    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping("/findByUserId")
    public Result<List<MenuDto>> getRoutes(){
        return Result.ok("操作成功", menuService.findByUserId(SecurityUtil.getLoginUser().getId()));
    }
}
