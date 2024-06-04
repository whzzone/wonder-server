package com.gitee.whzzone.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.whzzone.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Create by whz at 2023/7/13
 */
@Getter
@Setter
@ToString
@TableName("sys_mark")
public class Mark extends BaseEntity {

    private String name;

    private String remark;

    private Boolean enabled;

    private Integer sort;

}
