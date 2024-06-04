package com.gitee.whzzone.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.whzzone.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author : whz
 * @date : 2023/5/16 19:28
 */
@Getter
@Setter
@ToString
@TableName("sys_menu")
public class Menu extends BaseEntity {

    private Integer parentId;

    private String name;

    private String permission;

    private String description;

    private Boolean enabled;

    private String path;

    private String component;

    private String icon;

    private Integer sort;

    private Integer menuType;

    private Boolean inFrame;

    private Boolean isUrl;

    private Boolean keepAlive;

    private String routeName;

}
