package com.gitee.whzzone.admin.system.pojo.dto;

import com.gitee.whzzone.annotation.EntityField;
import com.gitee.whzzone.web.pojo.dto.EntityDTO;
import com.gitee.whzzone.web.validation.groups.AddGroup;
import com.gitee.whzzone.web.validation.groups.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Create by whz at 2023/7/13
 */
@Data
public class MarkDTO extends EntityDTO {

    @EntityField
    @NotBlank(message = "名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @EntityField
    @ApiModelProperty("备注")
    private String remark;

    @EntityField
    @ApiModelProperty("是否启用")
    private Boolean enabled;

    @EntityField
    @ApiModelProperty("排序")
    private Integer sort;

}
