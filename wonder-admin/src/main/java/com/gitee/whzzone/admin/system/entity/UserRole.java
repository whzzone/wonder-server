package com.gitee.whzzone.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("sys_user_role")
public class UserRole extends BaseEntity<UserRole> {

    private Long userId;

    private Long roleId;

}
