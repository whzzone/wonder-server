package com.gitee.whzzone.controller;

import com.gitee.whzzone.mapper.RoleMapper;
import com.gitee.whzzone.pojo.dto.DataScopeInfo;
import com.gitee.whzzone.pojo.entity.Role;
import com.gitee.whzzone.service.TestService;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */
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
}
