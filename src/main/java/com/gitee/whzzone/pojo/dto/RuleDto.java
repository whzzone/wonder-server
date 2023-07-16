package com.gitee.whzzone.pojo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.common.serializer.LongSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */
@Data
public class RuleDto extends EntityDto<RuleDto> {

    @ApiModelProperty
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @ApiModelProperty("是否启用")
    private Boolean enabled;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("表别名")
    private String tableAlias;

    @ApiModelProperty("字段名")
    private String columnName;

    @ApiModelProperty("拼接类型 OR AND")
    private String spliceType;

    @ApiModelProperty("表达式 EQ NE LE GT...")
    private String expression;

    @ApiModelProperty("提供类型")
    private Integer provideType;

    @ApiModelProperty("值1")
    private String value1;

    @ApiModelProperty("值2")
    private String value2;

    @ApiModelProperty("全限定类名")
    private String className;

    @ApiModelProperty("方法名")
    private String methodName;

    @ApiModelProperty("形参")
    private String formalParam;

    @ApiModelProperty("实参注入")
    private String actualParam;

//    @ApiModelProperty("形参列表")
//    private String[] formalParamList;
//
//    @ApiModelProperty("实参列表")
//    private String[] actualParamList;

    @ApiModelProperty("参数")
    private List<ParamDto> paramList;
}
