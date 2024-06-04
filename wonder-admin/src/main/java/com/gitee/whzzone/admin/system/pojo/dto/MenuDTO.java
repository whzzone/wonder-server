package com.gitee.whzzone.admin.system.pojo.dto;

import com.gitee.whzzone.annotation.EntityField;
import com.gitee.whzzone.web.pojo.dto.EntityDTO;
import com.gitee.whzzone.web.validation.groups.AddGroup;
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
public class MenuDTO extends EntityDTO {

    @EntityField
    @ApiModelProperty(value = "parentId")
    private Integer parentId;

    private String parentName;

    @EntityField
    @NotBlank(message = "name不能为空", groups = {UpdateGroup.class, AddGroup.class})
    @ApiModelProperty(value = "name", required = true)
    private String name;

    @EntityField
    @ApiModelProperty(value = "权限字符")
    private String permission;

    @EntityField
    @ApiModelProperty(value = "描述")
    private String description;

    @EntityField
    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    @EntityField
    @ApiModelProperty(value = "路由地址")
    private String path;

    @EntityField
    @ApiModelProperty(value = "组件路径")
    private String component;

    @EntityField
    @ApiModelProperty(value = "图标")
    private String icon;

    @EntityField
    @ApiModelProperty(value = "排序")
    private Integer sort;

    @EntityField
    @NotNull(message = "menuType不能为空", groups = {UpdateGroup.class, AddGroup.class})
    @ApiModelProperty(value = "菜单类型", required = true)
    private Integer menuType;

    @EntityField
    @ApiModelProperty(value = "是否在框架中打开")
    private Boolean inFrame;

    @EntityField
    @ApiModelProperty(value = "是否url链接")
    private Boolean isUrl;

    @EntityField
    @ApiModelProperty(value = "是否缓存")
    private Boolean keepAlive;

    @EntityField
    @ApiModelProperty(value = "路由名称")
    private String routeName;

    @ApiModelProperty(value = "是否存在下级")
    private Boolean hasChildren;

}
