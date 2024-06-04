package com.gitee.whzzone.annotation;

import com.gitee.whzzone.common.enums.ExpressionEnum;

import java.lang.annotation.*;

/**
 * @author Create by whz at 2023/8/4
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Query {

    /**
     * 表字段名，为空自动处理小驼峰转下划线
     */
    String column() default "";

    /**
     * 匹配方式，默认为精确查询查询 {@link ExpressionEnum}
     */
    ExpressionEnum expression() default ExpressionEnum.EQ;

}
