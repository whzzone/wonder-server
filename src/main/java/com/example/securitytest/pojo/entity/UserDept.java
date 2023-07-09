package com.example.securitytest.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
