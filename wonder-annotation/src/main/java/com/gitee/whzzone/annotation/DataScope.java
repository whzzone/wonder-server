package com.gitee.whzzone.annotation;

import java.lang.annotation.*;

/**
 * 使用数据权限。
 * 建议使用在mapper层接口上，使用在其他层会出现同一个类中直接调用了另一个方法，从而导致无法代理，
 * 如果使用在其他层方法，那么这个方法内的所有sql查询都会被数据权限限制！！！
 * @author Create by whz at 2023/6/8
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 规则name
     */
    String value();

}
