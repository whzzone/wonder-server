package com.gitee.whzzone.common.annotation;

import java.lang.annotation.*;

/**
 * 表示该字段为插入字段
 * @author Create by whz at 2023/11/3
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SaveField {

    boolean required() default false;

}
