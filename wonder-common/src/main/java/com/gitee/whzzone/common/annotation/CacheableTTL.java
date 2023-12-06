package com.gitee.whzzone.common.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.TimeUnit;

/**
 * @author Create by whz at 2023/12/6
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheableTTL {

    long value() default 5;

    TimeUnit timeUnit() default TimeUnit.MINUTES;

}
