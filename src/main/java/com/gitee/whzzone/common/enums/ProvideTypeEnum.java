package com.gitee.whzzone.common.enums;

import lombok.Getter;

/**
 * @author Create by whz at 2023/7/14
 */

@Getter
public enum ProvideTypeEnum {

    VALUE(1, "值"),
    METHOD(2, "方法");

    private final Integer code;
    private final String name;

    ProvideTypeEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

}
