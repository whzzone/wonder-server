package com.example.securitytest.pojo.dto;

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
public class RoleDto extends BaseDto{

    @ApiModelProperty("角色id")
    @NotNull(message = "角色id不能为空", groups = UpdateGroup.class)
    @JsonSerialize(using = LongSerializer.class)
    private Long id;

    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    private String name;

    @ApiModelProperty("角色描述")
    private String description;

    @ApiModelProperty("角色编码")
    @NotBlank(message = "角色编码不能为空", groups = {CreateGroup.class, UpdateGroup.class})
    private String code;

    @ApiModelProperty("菜单ids")
    private List<Long> menuIds;

}
