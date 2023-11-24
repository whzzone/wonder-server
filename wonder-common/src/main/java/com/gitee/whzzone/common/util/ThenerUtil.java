package com.gitee.whzzone.common.util;

import java.lang.reflect.Field;
import java.util.Objects;

public class ThenerUtil {

    //用于比较两个相同类型的字段大小
    public static <T extends Comparable<T>> boolean compareFields(T fieldValue1, T fieldValue2) {
        if (Objects.isNull(fieldValue1) || Objects.isNull(fieldValue2)) {
            // 如果任意一个字段为nufll，则不进行比较，直接false
            return false;
        }

        //fieldValue2 > fieldValue1 返回true 反之则反
        return fieldValue2.compareTo(fieldValue1) > 0;
    }

    public static<T extends Comparable<T>> boolean compareFields(Field field1, Field field2, Object obj) {
        try {
            field1.setAccessible(true);
            field2.setAccessible(true);

            T value1 = (T) field1.get(obj);
            T value2 = (T) field2.get(obj);

            return compareFields(value1, value2);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("无法访问字段值", e);
        }
    }

}