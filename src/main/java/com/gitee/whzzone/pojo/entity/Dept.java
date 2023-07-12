package com.gitee.whzzone.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Create by whz at 2023/7/8
 */
@Data
@TableName("sys_dept")
public class Dept extends BaseEntity<Dept> {

    private Long parentId;

    private String name;

    private Boolean enabled;

    private Integer sort;

}
