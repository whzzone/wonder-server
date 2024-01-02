package com.gitee.whzzone.annotation;

import java.lang.annotation.*;

/**
 * @author Create by whz at 2023/12/29
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface EntityField {

    boolean insert() default true;

    boolean update() default true;

}
