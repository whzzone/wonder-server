package com.gitee.whzzone.admin.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.whzzone.admin.common.base.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * @author Create by whz at 2023/7/8
 */

@Data
@TableName("sys_user_dept")
public class UserDept extends BaseEntity<UserDept> {

    private Long userId;

    private Long deptId;

}
