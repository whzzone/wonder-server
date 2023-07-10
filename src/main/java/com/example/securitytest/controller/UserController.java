package com.example.securitytest.controller;

import com.example.securitytest.pojo.dto.UserDto;
import com.example.securitytest.pojo.entity.User;
import com.example.securitytest.pojo.query.UserQuery;
import com.example.securitytest.pojo.vo.PageData;
import com.example.securitytest.service.UserService;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author : whz
 * @date : 2023/5/16 19:34
 */

@Api(tags = "用户相关")
@RestController
@RequestMapping("user")
public class UserController extends EntityController<User, UserService, UserDto> {

    @Autowired
    private UserService userService;

    @ApiOperation("分页")
    @PostMapping("page")
    public Result<PageData<UserDto>> page(@Validated @RequestBody UserQuery query){
        return Result.ok(userService.page(query));
    }

    @ApiOperation("改变启用状态")
    @GetMapping("/enabledSwitch/{id}")
    public Result enabledSwitch(@PathVariable Long id) {
        userService.enabledSwitch(id);
        return Result.ok();
    }

}
