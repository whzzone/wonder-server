package com.gitee.whzzone.admin.system.controller;

import com.gitee.whzzone.admin.system.entity.Menu;
import com.gitee.whzzone.admin.system.pojo.dto.MenuDTO;
import com.gitee.whzzone.admin.system.pojo.dto.MenuTreeDTO;
import com.gitee.whzzone.admin.system.pojo.query.MenuQuery;
import com.gitee.whzzone.admin.system.service.MenuService;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.web.controller.EntityController;
import com.gitee.whzzone.web.pojo.other.Result;
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
public class MenuController extends EntityController<Menu, MenuService, MenuDTO, MenuQuery> {

    @Autowired
    private MenuService menuService;

    @ApiOperation("查询菜单树")
//    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping("/treeList")
    public Result<List<MenuTreeDTO>> treeList(MenuQuery query){
        return Result.ok("操作成功", menuService.treeList(query));
    }

    @ApiOperation("列表")
    //    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping("list")
    public Result<List<MenuDTO>> list(MenuQuery query){
        return Result.ok("操作成功", menuService.list(query));
    }

    @ApiOperation("获取用户菜单-包含路由、权限")
    @GetMapping("/findByUserId")
    public Result<List<MenuDTO>> getRoutes(){
        return Result.ok("操作成功", menuService.findByUserId(SecurityUtil.getLoginUser().getId()));
    }
}
