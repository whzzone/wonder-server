package com.gitee.whzzone.common.enums;

/**
 * @author Create by whz at 2023/8/8
 */
public enum QrCodeStatusEnum {

    INVALID(-1, "无效的"),
    NOT_SCANNED(0, "未扫描"),
    SCANNED(1, "已扫描");

    private final Integer code;
    private final String name;

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    QrCodeStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
