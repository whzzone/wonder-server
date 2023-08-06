package com.gitee.whzzone.admin.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.whzzone.admin.common.base.pojo.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Create by whz at 2023/7/16
 */
@Getter
@Setter
@ToString
@TableName("sys_role_mark")
public class RoleMark extends BaseEntity<RoleMark> {

    private Long roleId;

    private Long markId;

    private Long ruleId;

}