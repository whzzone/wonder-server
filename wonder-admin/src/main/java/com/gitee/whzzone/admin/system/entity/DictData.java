package com.gitee.whzzone.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.whzzone.admin.common.base.pojo.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* 系统字典数据
* @author Create by generator at 2023/8/8
*/
@Getter
@Setter
@ToString
@TableName("sys_dict_data")
@ApiModel(value = "DictData对象", description = "系统字典数据")
public class DictData extends BaseEntity<DictData> {

    @ApiModelProperty("字典id")
    @TableField("dict_id")
    private Long dictId;

    @ApiModelProperty("父级id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("字典标签")
    @TableField("dict_label")
    private String dictLabel;

    @ApiModelProperty("字典值")
    @TableField("dict_value")
    private String dictValue;

    @ApiModelProperty("列表回显样式")
    @TableField("list_class")
    private String listClass;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

}
