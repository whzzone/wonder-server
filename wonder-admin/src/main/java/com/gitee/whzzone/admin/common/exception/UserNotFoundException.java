package com.gitee.whzzone.admin.common.exception;

/**
 * @author Create by whz at 2023/9/28
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
