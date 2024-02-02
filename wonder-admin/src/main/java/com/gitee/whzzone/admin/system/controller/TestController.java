package com.gitee.whzzone.admin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.system.entity.Role;
import com.gitee.whzzone.admin.system.mapper.RoleMapper;
import com.gitee.whzzone.admin.system.service.TestService;
import com.gitee.whzzone.admin.system.service.UserService;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.web.pojo.other.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */
@Api(tags = "测试相关")
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private TestService testService;

    @Autowired
    private UserService userService;

    @ApiOperation("方法注解")
    @GetMapping("method")
    public Result dataScopeTest(){
        List<Role> roles = roleMapper.selectAllTest();
        return Result.ok(roles);
    }

    @SaCheckPermission("aaaa")
//    @SaCheckOr(role = @SaCheckRole("worker"), permission = @SaCheckPermission("aaaa"))
    @ApiOperation("获取登录用户的信息")
    @GetMapping("getLoginUser")
    public Result<LoginUser> getLoginUser(){
        return Result.ok(SecurityUtil.getLoginUser());
    }

}
