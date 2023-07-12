package com.gitee.whzzone.common.enums;

import lombok.Getter;

/**
 * @author : whz
 * @date : 2023/2/15 16:26
 */

@Getter
public enum MenuTypeEnums {

    M("目录", 1),
    C("菜单", 2),
    B("按钮", 3);

    private final String name;
    private final Integer code;

    MenuTypeEnums(String name, Integer code){
        this.name = name;
        this.code = code;
    }
}
