package com.example.securitytest.pojo.dto;

import com.example.securitytest.common.LongSerializer;
import com.example.securitytest.common.validation.group.CreateGroup;
import com.example.securitytest.common.validation.group.UpdateGroup;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author : whz
 * @date : 2023/5/22 20:18
 */
@Data
public class MenuDto extends BaseDto<MenuDto> {

    @JsonSerialize(using = LongSerializer.class)
    @NotNull(message = "id不能为空", groups = UpdateGroup.class)
    @ApiModelProperty(value = "id不能为空", required = true)
    private Long id;

    @JsonSerialize(using = LongSerializer.class)
    @NotNull(message = "parentId不能为空", groups = {UpdateGroup.class, CreateGroup.class})
    @ApiModelProperty(value = "parentId", required = true)
    private Long parentId;

    private String parentName;

    @NotBlank(message = "name不能为空", groups = {UpdateGroup.class, CreateGroup.class})
    @ApiModelProperty(value = "name", required = true)
    private String name;

    @ApiModelProperty(value = "permission", required = false)
    private String permission;

    @ApiModelProperty(value = "description", required = false)
    private String description;

    @ApiModelProperty(value = "enabled", required = false)
    private Boolean enabled;

    @ApiModelProperty(value = "path", required = false)
    private String path;

    @ApiModelProperty(value = "component", required = false)
    private String component;

    @ApiModelProperty(value = "icon", required = false)
    private String icon;

    @ApiModelProperty(value = "sort", required = false)
    private Integer sort;

    @NotNull(message = "menuType不能为空", groups = {UpdateGroup.class, CreateGroup.class})
    @ApiModelProperty(value = "menuType", required = true)
    private Integer menuType;
}
