package com.gitee.whzzone.admin.system.pojo.other.Dict;

import com.gitee.whzzone.admin.common.base.pojo.dto.EntityDto;
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
@ApiModel(value = "DictDto对象", description = "系统字典")
public class DictDto extends EntityDto {

    @ApiModelProperty("字典名称")
    private String dictName;

    @ApiModelProperty("字典编码（唯一）")
    private String dictCode;

    @ApiModelProperty("字典类型，0-列表，1-树")
    private Integer dictType;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("备注")
    private String remark;

}
