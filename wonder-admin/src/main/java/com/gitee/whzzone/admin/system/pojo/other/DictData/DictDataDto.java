package com.gitee.whzzone.admin.system.pojo.other.DictData;

import com.gitee.whzzone.admin.common.base.pojo.dto.EntityDto;
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
@ApiModel(value = "DictDataDto对象", description = "系统字典数据")
public class DictDataDto extends EntityDto {

    @ApiModelProperty("字典id")
    private Long dictId;

    @ApiModelProperty("父级id")
    private Long parentId;

    @ApiModelProperty("字典标签")
    private String dictLabel;

    @ApiModelProperty("字典值")
    private String dictValue;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("排序")
    private Integer sort;

}
