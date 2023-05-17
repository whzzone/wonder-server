package com.example.securitytest.controller;

import com.example.securitytest.common.Result;
import com.example.securitytest.service.SysUserService;
import com.example.securitytest.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : whz
 * @date : 2023/5/16 19:34
 */
@RestController
@RequestMapping("user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("test")
    public Result test(){
        return Result.ok(SecurityUtil.getLoginUser().toString());
    }

}
