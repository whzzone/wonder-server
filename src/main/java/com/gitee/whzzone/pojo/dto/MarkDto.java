package com.gitee.whzzone.pojo.dto;

import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.common.validation.group.CreateGroup;
import com.gitee.whzzone.common.validation.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Create by whz at 2023/7/13
 */
@Data
public class MarkDto extends EntityDto {

    @NotBlank(message = "名称不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否启用")
    private Boolean enabled;

    @ApiModelProperty("排序")
    private Integer sort;

}
