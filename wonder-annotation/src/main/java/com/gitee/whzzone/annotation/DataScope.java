package com.gitee.whzzone.annotation;

import java.lang.annotation.*;

/**
 * 数据权限，使用在mapper层方法上
 * @author Create by whz at 2023/6/8
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 规则名称
     */
    String value();

    /**
     * 是否启用
     */
    boolean enabled() default true;
}
