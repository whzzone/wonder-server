package com.gitee.whzzone.admin.system.pojo.dto;

import com.gitee.whzzone.annotation.EntityField;
import com.gitee.whzzone.web.pojo.dto.EntityDTO;
import com.gitee.whzzone.web.validation.groups.AddGroup;
import com.gitee.whzzone.web.validation.groups.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RoleDTO extends EntityDTO {

    @EntityField
    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    @EntityField
    @ApiModelProperty("描述")
    private String description;

    @EntityField
    @ApiModelProperty("编码")
    @NotBlank(message = "编码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String code;

    @ApiModelProperty("菜单ids")
    private List<Integer> menuIds;

    @EntityField
    @ApiModelProperty("是否启用")
    private Boolean enabled;

}
