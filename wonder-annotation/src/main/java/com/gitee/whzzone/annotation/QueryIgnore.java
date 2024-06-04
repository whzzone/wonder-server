package com.gitee.whzzone.annotation;

import java.lang.annotation.*;

/**
 * 查询忽略注解
 * 
 * @author Create by whz at 2023/8/4
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryIgnore {}
