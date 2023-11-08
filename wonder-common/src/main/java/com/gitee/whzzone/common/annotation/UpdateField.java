package com.gitee.whzzone.common.annotation;

import java.lang.annotation.*;

/**
 * @author Create by whz at 2023/11/3
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UpdateField {

    boolean required() default false;

}
