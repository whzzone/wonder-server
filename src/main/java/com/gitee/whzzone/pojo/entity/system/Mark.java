package com.gitee.whzzone.pojo.entity.system;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.whzzone.common.base.pojo.entity.BaseEntity;
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
public class Mark extends BaseEntity<Mark> {

    private String name;

    private String remark;

    private Boolean enabled;

    private Integer sort;

}
