package com.gitee.whzzone.admin.system.controller;

import com.gitee.whzzone.admin.common.security.LoginUser;
import com.gitee.whzzone.admin.system.entity.User;
import com.gitee.whzzone.admin.system.pojo.dto.ChangePasswdDTO;
import com.gitee.whzzone.admin.system.pojo.dto.ResetPasswdDTO;
import com.gitee.whzzone.admin.system.pojo.dto.UserDTO;
import com.gitee.whzzone.admin.system.pojo.query.UserQuery;
import com.gitee.whzzone.admin.system.service.UserService;
import com.gitee.whzzone.admin.util.SecurityUtil;
import com.gitee.whzzone.web.controller.EntityController;
import com.gitee.whzzone.web.pojo.other.PageData;
import com.gitee.whzzone.web.pojo.other.Result;
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
public class UserController extends EntityController<User, UserService, UserDTO, UserQuery> {

    @Autowired
    private UserService userService;

    @ApiOperation("分页")
    @GetMapping("page")
    public Result<PageData<UserDTO>> page(UserQuery query){
        return Result.ok(userService.page(query));
    }

    @ApiOperation("改变启用状态")
    @PutMapping("/enabledSwitch/{id}")
    public Result enabledSwitch(@PathVariable Integer id) {
        userService.enabledSwitch(id);
        return Result.ok();
    }

    @ApiOperation("重置密码")
    @PutMapping("resetPasswd")
    public Result<?> resetPasswd(@Validated @RequestBody ResetPasswdDTO dto){
        userService.resetPasswd(dto);
        return Result.ok();
    }

    @ApiOperation("更改密码")
    @PutMapping("changPasswd")
    public Result<?> changPasswd(@Validated @RequestBody ChangePasswdDTO dto){
        userService.changPasswd(dto);
        return Result.ok();
    }

    @ApiOperation("获取当前用户信息")
    @GetMapping("current/userinfo")
    public Result<LoginUser> currentUserinfo() {
        return Result.ok("查询成功", userService.getLoginUserInfo(SecurityUtil.getCurrentUserId()));
    }

}
