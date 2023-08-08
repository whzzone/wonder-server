package com.gitee.whzzone.admin.system.pojo.dto;

import com.gitee.whzzone.admin.common.base.pojo.dto.EntityDto;
import com.gitee.whzzone.admin.common.serializer.LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/2/7 16:29
 */
@Data
public class MenuTreeDto extends EntityDto {
    @JsonSerialize(using = LongSerializer.class)
    private Long id;
    @JsonSerialize(using = LongSerializer.class)
    private Long parentId;
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
