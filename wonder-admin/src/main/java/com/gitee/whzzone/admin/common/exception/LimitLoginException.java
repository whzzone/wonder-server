package com.gitee.whzzone.admin.common.exception;

/**
 * @author Create by whz at 2023/9/28
 */
public class LimitLoginException extends RuntimeException {

    public LimitLoginException() {
        super();
    }

    public LimitLoginException(String message) {
        super(message);
    }

}
