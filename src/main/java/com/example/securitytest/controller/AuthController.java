package com.example.securitytest.controller;

import com.example.securitytest.common.Result;
import com.example.securitytest.pojo.dto.EmailLoginDto;
import com.example.securitytest.pojo.dto.LoginDto;
import com.example.securitytest.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author : whz
 * @date : 2023/5/16 19:34
 */
@RestController
@RequestMapping("auth")
@Api(tags = "认证服务")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ApiOperation("账号密码登录")
    @PostMapping("/login/username")
    public Result loginByUsername(@Validated @RequestBody LoginDto dto){
        return authService.loginByUsername(dto);
    }

    @ApiOperation("测试")
    @PostMapping("test")
    public Result test(){
        return Result.ok("111111");
    }

    @GetMapping({"logout"})
    public Result logout(){
        authService.logout();
        return Result.ok();
    }

    @ApiOperation("邮箱登录")
    @PostMapping("/login/email")
    public Result loginByEmail(@Validated @RequestBody EmailLoginDto dto){
        return authService.loginByEmail(dto);
    }

    @PostMapping("/send/{email}")
    public Result sendEmail(@PathVariable String email){
        return authService.sendEmail(email);
    }

}

