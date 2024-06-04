package com.gitee.whzzone.admin.common.exception;

/**
 * @author Create by whz at 2023/9/28
 */
public class FrequencyLimitException extends RuntimeException {

    public FrequencyLimitException() {
        super();
    }

    public FrequencyLimitException(String message) {
        super(message);
    }

}
