package com.gitee.whzzone.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gitee.whzzone.admin.common.base.pojo.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : whz
 * @date : 2023/5/16 19:28
 */
@Getter
@Setter
@ToString
@TableName("sys_user")
public class User extends BaseEntity<User> {

    private String username;

    private String nickname;

    @JsonIgnore
    private String password;

    private String phone;

    private String openId;

    private String unionId;

    private String email;

    private Boolean enabled;

}
