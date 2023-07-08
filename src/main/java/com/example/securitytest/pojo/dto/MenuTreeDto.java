package com.example.securitytest.pojo.dto;

import com.example.securitytest.common.LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.List;

/**
 * @author : whz
 * @date : 2023/2/7 16:29
 */
@Data
public class MenuTreeDto extends BaseDto {
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
