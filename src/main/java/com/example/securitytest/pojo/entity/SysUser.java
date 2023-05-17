package com.example.securitytest.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author : whz
 * @date : 2023/5/16 19:28
 */
@Getter
@Setter
@ToString
@TableName("sys_user")
public class SysUser extends BaseEntity{

    @NotBlank(message = "账号不能为空")
    private String username;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String phone;

    private String openId;

    private String unionId;

    private String email;

    private Boolean enabled;

}
