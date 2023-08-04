package com.gitee.whzzone.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Create by whz at 2023/7/14
 */

@Getter
@AllArgsConstructor
public enum ProvideTypeEnum {

    VALUE(1, "值"),
    METHOD(2, "方法");

    private final Integer code;
    private final String name;

}
