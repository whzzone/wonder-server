package com.gitee.whzzone.pojo.entity;

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
@TableName("sys_data_scope")
public class DataScope extends BaseEntity<DataScope> {

    private String scopeName;

    private String tableAlias;

    private String columnName;

    private String operation;

    private Integer provideType;

    private String value1;

    private String value2;

    private String className;

    private String methodName;

}
