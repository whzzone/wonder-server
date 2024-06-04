package com.gitee.whzzone.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.whzzone.web.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* 系统字典
* @author Create by generator at 2023/8/8
*/
@Getter
@Setter
@ToString
@TableName("sys_dict")
@ApiModel(value = "Dict对象", description = "系统字典")
public class Dict extends BaseEntity {

    @ApiModelProperty("字典名称")
    @TableField("dict_name")
    private String dictName;

    @ApiModelProperty("字典编码（唯一）")
    @TableField("dict_code")
    private String dictCode;

    @ApiModelProperty("字典类型，0-列表，1-树")
    @TableField("dict_type")
    private Integer dictType;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

}
