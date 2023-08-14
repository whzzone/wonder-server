package com.gitee.whzzone.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Create by whz at 2023/8/8
 */
@Getter
@AllArgsConstructor
public enum QrCodeStatusEnum {

    INVALID(-1, "无效的"),
    NOT_SCANNED(0, "未扫描"),
    SCANNED(1, "已扫描");

    private final Integer code;
    private final String name;
}
