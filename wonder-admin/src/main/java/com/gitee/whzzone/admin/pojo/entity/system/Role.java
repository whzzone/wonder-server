package com.gitee.whzzone.admin.pojo.entity.system;

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
@TableName("sys_role")
public class Role extends BaseEntity<Role> {

    private Long parentId;

    private String code;

    private String name;

    private String description;

    private Boolean enabled;

}
