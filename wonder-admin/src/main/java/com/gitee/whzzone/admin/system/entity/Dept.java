package com.gitee.whzzone.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.whzzone.web.entity.BaseEntity;
import lombok.Data;

/**
 * @author Create by whz at 2023/7/8
 */
@Data
@TableName("sys_dept")
public class Dept extends BaseEntity {

    private Integer parentId;

    private String name;

    private Boolean enabled;

    private Integer sort;

}
