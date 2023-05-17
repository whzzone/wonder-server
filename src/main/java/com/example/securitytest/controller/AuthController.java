package com.example.securitytest.controller;

import com.example.securitytest.common.Result;
import com.example.securitytest.pojo.dto.EmailLoginDto;
import com.example.securitytest.pojo.dto.LoginDto;
import com.example.securitytest.pojo.entity.SysUser;
import com.example.securitytest.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author : whz
 * @date : 2023/5/16 19:34
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public Result login(@Validated @RequestBody LoginDto dto){
        return authService.login(dto);
    }

    @PostMapping("test")
    public Result test(){
        return Result.ok();
    }

    @GetMapping({"logout"})
    public String logout(){
        SysUser sysUser = new SysUser();
        sysUser.setUsername(new Date().toString());
        return sysUser.toString();
    }

    @PostMapping("/login/email")
    public Result login(@Validated @RequestBody EmailLoginDto dto){
        return authService.login(dto);
    }

    @PostMapping("/send/{email}")
    public Result sendEmail(@PathVariable String email){
        return authService.sendEmail(email);
    }

}

