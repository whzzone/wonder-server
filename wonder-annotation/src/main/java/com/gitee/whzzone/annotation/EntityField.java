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
     * 该字段是否允许修改
     */
    boolean updateAble() default true;

    /**
     * 该字段更新时是否必填
     */
    boolean updateRequired() default false;

    /**
     * 该字段是否允许新增
     */
    boolean addAble() default true;

    /**
     * 该字段新增时是否必填
     */
    boolean addRequired() default false;
}
