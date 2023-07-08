package com.example.securitytest.controller;

import com.example.securitytest.pojo.dto.UserDto;
import com.example.securitytest.pojo.query.UserQuery;
import com.example.securitytest.pojo.vo.PageData;
import com.example.securitytest.service.UserService;
import com.example.securitytest.util.SecurityUtil;
import com.gitee.whzzone.web.Result;
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
@CacheConfig(cacheNames = "UserController", keyGenerator = "customKeyGenerator")
public class UserController {

    @Autowired
    private UserService sysUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("test")
//    @PreAuthorize("hasAuthority('sys:index:index')")
    public Result test(){
        return Result.ok(SecurityUtil.getLoginUser().toString());
    }

    @PostMapping("save")
    public Result save(@Validated @RequestBody UserDto dto){
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        sysUserService.save(dto);
        return Result.ok("操作成功", dto);
    }

    @PostMapping("updateById")
    public Result updateById(@Validated @RequestBody UserDto dto){
        sysUserService.updateById(dto);
        return Result.ok("操作成功", dto);
    }

    @PostMapping("list")
    public Result<PageData<UserDto>> list(@Validated @RequestBody UserQuery query){
        return Result.ok(sysUserService.page(query));
    }

    @ApiOperation("获取")
    @GetMapping("get/{id}")
    @Cacheable
    public Result get(@PathVariable Long id){
        return Result.ok(sysUserService.getDtoById(id));
    }

    @ApiOperation("删除")
    @GetMapping("delete/{id}")
    public Result delete(@PathVariable Long id){
        return Result.ok(sysUserService.removeById(id));
    }
}
