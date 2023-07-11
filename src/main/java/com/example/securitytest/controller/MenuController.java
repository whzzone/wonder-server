package com.example.securitytest.controller;

import com.example.securitytest.pojo.dto.MenuDto;
import com.example.securitytest.pojo.dto.MenuTreeDto;
import com.example.securitytest.pojo.entity.Menu;
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
public class MenuController extends EntityController<Menu, MenuService, MenuDto> {

    @Autowired
    private MenuService menuService;

    @ApiOperation("查询菜单树")
//    @PreAuthorize("hasAuthority('sys:menu:view')")
    @PostMapping("/treeList")
    public Result<List<MenuTreeDto>> treeList(@RequestBody MenuQuery query){
        return Result.ok("操作成功", menuService.treeList(query));
    }

    @ApiOperation("列表")
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
