package com.gitee.whzzone.admin.system.entity;

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
@TableName("sys_rule")
public class Rule extends BaseEntity {

    private String remark;

    private Long markId;

    private String tableAlias;

    private String columnName;

    private String spliceType;

    private String expression;

    private Integer provideType;

    private String value1;

    private String value2;

    private String className;

    private String methodName;

    private String formalParam;

    private String actualParam;

}
