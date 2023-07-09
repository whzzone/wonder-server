package com.example.securitytest.controller;

import com.example.securitytest.common.validation.group.UpdateGroup;
import com.example.securitytest.pojo.dto.UserDto;
import com.example.securitytest.pojo.query.UserQuery;
import com.example.securitytest.pojo.vo.PageData;
import com.example.securitytest.service.UserService;
import com.example.securitytest.util.SecurityUtil;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author : whz
 * @date : 2023/5/16 19:34
 */

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

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
        userService.save(dto);
        return Result.ok("操作成功", dto);
    }

    @PostMapping("update")
    public Result update(@Validated(UpdateGroup.class) @RequestBody UserDto dto){
        userService.updateById(dto);
        return Result.ok("操作成功", dto);
    }

    @ApiOperation("分页")
    @PostMapping("page")
    public Result<PageData<UserDto>> page(@Validated @RequestBody UserQuery query){
        return Result.ok(userService.page(query));
    }

    @ApiOperation("获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        return Result.ok(userService.getDtoById(id));
    }

    @ApiOperation("删除")
    @GetMapping("delete/{id}")
    public Result delete(@PathVariable Long id){
        return Result.ok(userService.removeById(id));
    }

    @ApiOperation("改变启用状态")
    @GetMapping("/enabledSwitch/{id}")
    public Result enabledSwitch(@PathVariable Long id) {
        userService.enabledSwitch(id);
        return Result.ok();
    }

}
