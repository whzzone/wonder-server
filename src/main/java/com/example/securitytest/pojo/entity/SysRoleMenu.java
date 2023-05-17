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
@TableName("sys_role_menu")
public class SysRoleMenu extends BaseEntity<SysRoleMenu> {

    private Long roleId;

    private Long menuId;

}
