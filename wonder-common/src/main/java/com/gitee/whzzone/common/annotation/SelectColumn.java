package com.gitee.whzzone.common.annotation;

import java.lang.annotation.*;

/**
 * @author Create by whz at 2023/8/7
 */
@Deprecated
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SelectColumn {

    /**
     * 需要查询返回的列，为空查询所有列
     */
    String[] value() default {};

}
