package com.gitee.whzzone.admin.system.pojo.dto;

import com.gitee.whzzone.web.pojo.dto.EntityDto;
import lombok.Data;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/2/7 16:29
 */
@Data
public class MenuTreeDto extends EntityDto {
    private Integer id;
    private Integer parentId;
    private String parentName;
    private String name;
    private String permission;
    private String description;
    private Boolean hasChildren;
    private String path;
    private String component;
    private Integer sort;
    private String icon;
    private Integer menuType;

    private List<MenuTreeDto> children;


}
