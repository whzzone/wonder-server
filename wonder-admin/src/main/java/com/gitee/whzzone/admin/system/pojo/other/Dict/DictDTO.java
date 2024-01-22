package com.gitee.whzzone.admin.system.pojo.other.Dict;

import com.gitee.whzzone.web.pojo.dto.EntityDTO;
import com.gitee.whzzone.web.validation.groups.InsertGroup;
import com.gitee.whzzone.web.validation.groups.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* 系统字典
* @author Create by generator at 2023/8/8
*/
@Getter
@Setter
@ToString
@ApiModel(value = "DictDTO对象", description = "系统字典")
public class DictDTO extends EntityDTO {

    @NotBlank(message = "字典名称不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "字典名称",required = true)
    private String dictName;

    @NotBlank(message = "字典编码不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "字典编码（唯一）", required = true)
    private String dictCode;

    @NotNull(message = "字典类型不能为空", groups = {InsertGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "字典类型，0-列表，1-树", required = true)
    private Integer dictType;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("备注")
    private String remark;

}
