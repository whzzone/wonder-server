package com.gitee.whzzone.admin.system.pojo.dto;

import com.gitee.whzzone.annotation.EntityField;
import com.gitee.whzzone.web.pojo.dto.EntityDTO;
import com.gitee.whzzone.web.validation.groups.InsertGroup;
import com.gitee.whzzone.web.validation.groups.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Create by whz at 2023/7/13
 */
@Data
public class RuleDTO extends EntityDTO {

    @EntityField
    @NotNull(message = "markId不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "markId", required = true)
    private Integer markId;

    @EntityField
    @ApiModelProperty("备注")
    private String remark;

    @EntityField
    @ApiModelProperty("表别名")
    private String tableAlias;

    @EntityField
    @NotNull(message = "字段名不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "字段名", required = true)
    private String columnName;

    @EntityField
    @NotNull(message = "拼接类型不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "拼接类型 OR AND", required = true)
    private String spliceType;

    @EntityField
    @NotNull(message = "表达式 EQ NE LE GT...", groups = {InsertGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "表达式 EQ NE LE GT...", required = true)
    private String expression;

    @EntityField
    @NotNull(message = "提供类型", groups = {InsertGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "提供类型", required = true)
    private Integer provideType;

    @EntityField
    @ApiModelProperty("值1")
    private String value1;

/*    @ApiModelProperty("值2")
    private String value2;*/

    @EntityField
    @ApiModelProperty("全限定类名#方法名")
    private String fullMethodName;

    @EntityField
    @ApiModelProperty("形参")
    private String formalParam;

    @EntityField
    @ApiModelProperty("实参注入")
    private String actualParam;

    @ApiModelProperty("参数")
    private List<ParamDTO> paramList;

    @ApiModelProperty("方法执行返回结果")
    private Object result;
}
