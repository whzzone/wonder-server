package com.gitee.whzzone.common.annotation;


import java.lang.annotation.*;

/**
 * 排序方式，默认asc, 可选desc
 * @author Create by whz at 2023/8/4
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryOrder {

    /**
     * 排序方式
     */
    String value() default "asc";

}
