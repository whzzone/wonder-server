package com.gitee.whzzone.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : whz
 * @date : 2023/2/15 16:26
 */

@Getter
@AllArgsConstructor
public enum MenuTypeEnum {

    M("目录", 1),
    C("菜单", 2),
    B("按钮", 3);

    private final String name;
    private final Integer code;

}
