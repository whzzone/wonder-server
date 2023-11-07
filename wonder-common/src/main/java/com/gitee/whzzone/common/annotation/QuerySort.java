package com.gitee.whzzone.common.annotation;


import java.lang.annotation.*;

/**
 * 排序列，默认id
 * @author Create by whz at 2023/8/4
 */
@Deprecated
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QuerySort {

    /**
     * 排序列
     */
    String value() default "id";

}
