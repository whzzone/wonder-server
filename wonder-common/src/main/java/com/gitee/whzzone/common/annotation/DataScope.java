package com.gitee.whzzone.common.annotation;

import java.lang.annotation.*;

/**
 * 放到mapper接口上
 * @author Create by whz at 2023/6/8
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {
    /**
     * 标记这是那个接口，
     */
    String value();

}
