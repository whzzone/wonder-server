package com.gitee.whzzone.pojo.dto.system;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.common.serializer.LongSerializer;
import com.gitee.whzzone.common.validation.group.CreateGroup;
import com.gitee.whzzone.common.validation.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */
@Data
public class RuleDto extends EntityDto {

    @NotNull(message = "markId不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "markId", required = true)
    @JsonSerialize(using = LongSerializer.class)
    private Long markId;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("表别名")
    private String tableAlias;

    @NotNull(message = "字段名不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "字段名", required = true)
    private String columnName;

    @NotNull(message = "拼接类型不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "拼接类型 OR AND", required = true)
    private String spliceType;

    @NotNull(message = "表达式 EQ NE LE GT...", groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "表达式 EQ NE LE GT...", required = true)
    private String expression;

    @NotNull(message = "提供类型", groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "提供类型", required = true)
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

    @ApiModelProperty("参数")
    private List<ParamDto> paramList;
}
