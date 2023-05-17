package com.example.securitytest.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("sys_user_role")
public class SysUserRole extends BaseEntity<SysUserRole> {

    private Long userId;

    private Long roleId;

}
