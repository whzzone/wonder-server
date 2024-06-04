package com.gitee.whzzone.admin.common.mybatis;

import lombok.Data;

/**
 * @author Create by whz at 2024/2/4
 */
@Data
public class DataScopeRule {

    private Integer id;

    private Integer markId;

    private String remark;

    private String tableAlias;

    private String columnName;

    private String spliceType;

    private String expression;

    private Integer provideType;

    private String value1;

    private String fullMethodName;

    private String formalParam;

    private String actualParam;

    private Object result;
}
