package com.gitee.whzzone.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Create by whz at 2024/1/24
 */
public class CommonUtil {

    public static Field[] getAllFieldsIncludingParents(Class<?> clazz) {
        List<Field> allFields = new ArrayList<>();

        // 获取当前类的所有属性（包括私有属性）
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            allFields.add(field);
        }

        // 获取父类的所有属性
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            Field[] parentFields = getAllFieldsIncludingParents(superClass);
            for (Field field : parentFields) {
                // 避免重复添加相同的属性
                if (!containsFieldWithName(allFields, field.getName())) {
                    allFields.add(field);
                }
            }
        }

        return allFields.toArray(new Field[0]);
    }

    private static boolean containsFieldWithName(List<Field> fields, String fieldName) {
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return true;
            }
        }
        return false;
    }
}
