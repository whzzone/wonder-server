package com.gitee.whzzone.admin.common.apilog;

/**
 * @author Create by whz at 2024/3/14
 */
public class ApiLogContextHolder {

    private ApiLogContextHolder() {}

    private static final ThreadLocal<ApiLogContext> THREAD_LOCAL = new ThreadLocal<>();

    public static ApiLogContext getApiLogContext() {
        return THREAD_LOCAL.get();
    }

    public static void setApiLogContext(ApiLogContext context) {
        THREAD_LOCAL.set(context);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}

