package com.gitee.whzzone.admin.system.pojo.dto;

import com.gitee.whzzone.web.pojo.dto.EntityDto;
import com.gitee.whzzone.web.validation.groups.InsertGroup;
import com.gitee.whzzone.web.validation.groups.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author : whz
 * @date : 2023/5/22 20:18
 */
@Data
public class MenuDto extends EntityDto {

    @ApiModelProperty(value = "parentId")
    private Integer parentId;

    private String parentName;

    @NotBlank(message = "name不能为空", groups = {UpdateGroup.class, InsertGroup.class})
    @ApiModelProperty(value = "name", required = true)
    private String name;

    @ApiModelProperty(value = "权限字符")
    private String permission;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @NotNull(message = "menuType不能为空", groups = {UpdateGroup.class, InsertGroup.class})
    @ApiModelProperty(value = "菜单类型", required = true)
    private Integer menuType;

    @ApiModelProperty(value = "是否在框架中打开")
    private Boolean inFrame;

    @ApiModelProperty(value = "是否url链接")
    private Boolean isUrl;

    @ApiModelProperty(value = "是否缓存")
    private Boolean keepAlive;

    @ApiModelProperty(value = "路由名称")
    private String routeName;

    @ApiModelProperty(value = "是否存在下级")
    private Boolean hasChildren;

}
