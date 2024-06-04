package com.gitee.whzzone.admin.common.limit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FrequencyLimit {

    /**
     * 用户访问接口的时间间隔，单位为秒，默认值为 3 秒
     */
    int interval() default 3;

    /**
     * 是否开启限制功能，默认为开启
     */
    boolean enabled() default true;

    /**
     * 提示消息
     */
    String message() default "操作太快了~";

    /**
     * 限流策略需要实现{@link LimitStrategy}
     */
    Class<? extends LimitStrategy> strategy() default EnvironmentLimitStrategy.class;

}
