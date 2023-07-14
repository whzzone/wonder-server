package com.gitee.whzzone.common.enums;

import lombok.Getter;

/**
 * @author Create by whz at 2023/7/14
 */

@Getter
public enum ProvideType {

    VALUE(1, "值"),
    METHOD(2, "方法");

    private final Integer code;
    private final String name;

    ProvideType(Integer code, String name){
        this.code = code;
        this.name = name;
    }

}
