package com.gitee.whzzone.admin.controller.system;

import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.mapper.system.RoleMapper;
import com.gitee.whzzone.admin.pojo.dto.system.DataScopeInfo;
import com.gitee.whzzone.admin.pojo.entity.system.Role;
import com.gitee.whzzone.admin.service.system.TestService;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.web.Result;
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

    @ApiOperation("方法注解")
    @GetMapping("method")
    public Result dataScopeTest(){
        List<Role> roles = roleMapper.selectAllTest();
        return Result.ok(roles);
    }

    @ApiOperation("带参测试")
    @GetMapping("withParam")
    public Result<DataScopeInfo> withParam(){
        DataScopeInfo info = testService.injectTest("带参测试", new DataScopeInfo());
        return Result.ok(info);
    }

    @ApiOperation("@DataScope 标记了错误类型的参数")
    @GetMapping("errorOfObject")
    public Result errorOfObject(){
        String info = testService.injectTest2("@DataScope 标记了错误类型", "");
        return Result.ok(info);
    }

    @ApiOperation("获取登录用户的信息")
    @GetMapping("getLoginUser")
    public Result<LoginUser> getLoginUser(){
        return Result.ok(SecurityUtil.getLoginUser());
    }
}
