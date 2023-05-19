package com.example.securitytest.controller;

import com.example.securitytest.common.Result;
import com.example.securitytest.pojo.entity.SysUser;
import com.example.securitytest.service.SysUserService;
import com.example.securitytest.util.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author : whz
 * @date : 2023/5/16 19:34
 */
@RestController
@RequestMapping("user")
@CacheConfig(cacheNames = "SysUserController", keyGenerator = "customKeyGenerator")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("test")
    public Result test(){
        return Result.ok(SecurityUtil.getLoginUser().toString());
    }

    @PostMapping("add")
    public Result add(@Validated @RequestBody SysUser entity){
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        sysUserService.save(entity);
        return Result.ok("操作成功", entity);
    }

    @PostMapping("edit")
    public Result edit(@Validated @RequestBody SysUser entity){
        sysUserService.updateById(entity);
        return Result.ok("操作成功", entity);
    }

    @GetMapping("list")
    public Result list(){
        return Result.ok(sysUserService.list());
    }

    @ApiOperation("获取")
    @GetMapping("get/{id}")
    @Cacheable
    public Result get(@PathVariable Long id){
        return Result.ok(sysUserService.getById(id));
    }
}
