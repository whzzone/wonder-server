package com.gitee.whzzone.common.enums;

/**
 * @author : whz
 * @date : 2023/2/15 16:26
 */
public enum MenuTypeEnum {

    M("目录", 1),
    C("菜单", 2),
    B("按钮", 3);

    private final String name;
    private final Integer code;

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

    MenuTypeEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }
}
