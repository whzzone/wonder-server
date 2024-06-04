package com.gitee.whzzone.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Create by whz at 2024/1/24
 */
public class CommonUtil {

    public static Field[] getAllFieldsIncludingParents(Class<?> cls) {
        Objects.requireNonNull(cls, "cls");
        List<Field> allFields = new ArrayList<>();

        // 获取当前类的所有属性
        Field[] currentFields = cls.getDeclaredFields();

        Arrays.stream(currentFields).filter(field -> !Modifier.isStatic(field.getModifiers()))
            .forEach(v -> allFields.add(v));

        // 获取父类的所有属性
        for (Class<?> parentClass = cls.getSuperclass(); parentClass != null;
            parentClass = parentClass.getSuperclass()) {
            Field[] parentFields = parentClass.getDeclaredFields();

            // 避免重复添加相同的属性 及去除 静态属性如 :serialVersionUID
            for (Field parentField : parentFields) {
                String name = parentField.getName();
                if (!allFields.stream().anyMatch(v -> v.getName().equalsIgnoreCase(name))
                    && !Modifier.isStatic(parentField.getModifiers())) {
                    allFields.add(parentField);
                }
            }
        }
        return allFields.toArray(new Field[0]);
    }
}
