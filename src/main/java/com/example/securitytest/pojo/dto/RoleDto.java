package com.example.securitytest.pojo.dto;

import com.example.securitytest.common.ListLongSerializer;
import com.example.securitytest.common.LongSerializer;
import com.example.securitytest.common.validation.group.CreateGroup;
import com.example.securitytest.common.validation.group.UpdateGroup;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class RoleDto extends BaseDto<RoleDto> {

    @ApiModelProperty("角色id")
    @NotNull(message = "角色id不能为空", groups = UpdateGroup.class)
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

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
