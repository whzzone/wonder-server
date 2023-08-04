package com.gitee.whzzone.pojo.dto.system;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gitee.whzzone.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.common.serializer.ListLongSerializer;
import com.gitee.whzzone.common.validation.group.CreateGroup;
import com.gitee.whzzone.common.validation.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RoleDto extends EntityDto {

    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("编码")
    @NotBlank(message = "编码不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    private String code;

    @ApiModelProperty("菜单ids")
    @JsonSerialize(using = ListLongSerializer.class)
    private List<Long> menuIds;

    @ApiModelProperty("是否启用")
    private Boolean enabled;

}
