package com.gitee.whzzone.controller.system;

import com.gitee.whzzone.common.base.controller.EntityController;
import com.gitee.whzzone.pojo.PageData;
import com.gitee.whzzone.pojo.dto.system.ResetPWDDto;
import com.gitee.whzzone.pojo.dto.system.UserDto;
import com.gitee.whzzone.pojo.entity.system.User;
import com.gitee.whzzone.pojo.query.system.UserQuery;
import com.gitee.whzzone.service.system.UserService;
import com.gitee.whzzone.web.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author : whz
 * @date : 2023/5/16 19:34
 */

@Api(tags = "用户相关")
@RestController
@RequestMapping("user")
public class UserController extends EntityController<User, UserService, UserDto, UserQuery> {

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

    @ApiOperation("重置密码")
    @PreAuthorize("hasAuthority('sys:user:resetPWD')")
    @PostMapping("resetPWD")
    public Result resetPWD(@Validated @RequestBody ResetPWDDto dto){
        userService.resetPWD(dto);
        return Result.ok();
    }

}
