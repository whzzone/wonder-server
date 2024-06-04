package com.gitee.whzzone.admin.system.pojo.other.DictData;

import com.gitee.whzzone.web.pojo.dto.EntityDTO;
import com.gitee.whzzone.web.validation.groups.AddGroup;
import com.gitee.whzzone.web.validation.groups.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
* 系统字典数据
* @author Create by generator at 2023/8/8
*/
@Getter
@Setter
@ToString
@ApiModel(value = "DictDataDTO对象", description = "系统字典数据")
public class DictDataDTO extends EntityDTO {

    @NotNull(message = "字典id不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "字典id", required = true)
    private Integer dictId;

    @ApiModelProperty("父级id")
    private Integer parentId;

    @NotBlank(message = "字典标签不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty("字典标签")
    private String dictLabel;

    @NotBlank(message = "字典值不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty("字典值")
    private String dictValue;

    @ApiModelProperty("列表回显样式")
    private String listClass;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("排序")
    private Integer sort;

}
