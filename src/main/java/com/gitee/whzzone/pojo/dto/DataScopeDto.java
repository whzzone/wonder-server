package com.gitee.whzzone.pojo.dto;

import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import lombok.Data;

/**
 * @author Create by whz at 2023/7/13
 */
@Data
public class DataScopeDto extends EntityDto<DataScopeDto> {

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
