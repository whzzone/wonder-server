package com.gitee.whzzone.annotation;

import java.lang.annotation.*;

/**
 * @author Create by whz at 2023/12/29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface EntityField {

    /**
     * 该字段是否允许新增
     */
    boolean insert() default true;

    /**
     * 该字段是否允许修改
     */
    boolean update() default true;

}
