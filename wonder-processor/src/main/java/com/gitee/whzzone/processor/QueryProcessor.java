package com.gitee.whzzone.processor;

import com.gitee.whzzone.common.annotation.Query;
import com.gitee.whzzone.common.enums.ExpressionEnum;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Create by whz at 2023/8/5
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.gitee.whzzone.common.annotation.Query")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class QueryProcessor extends AbstractProcessor {

    private Elements elementUtils;
    private Messager messager;
    private Map<String, Integer> columnCountMap;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
        columnCountMap = new HashMap<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "------开始校验 @Query 注解------");
        for (VariableElement fieldElement : ElementFilter.fieldsIn(roundEnv.getElementsAnnotatedWith(Query.class))) {
            Query queryAnnotation = fieldElement.getAnnotation(Query.class);
            String column = queryAnnotation.column();

            if (queryAnnotation.expression() != ExpressionEnum.BETWEEN) {
                continue;
            }

            if (column.isEmpty()) {
                messager.printMessage(Diagnostic.Kind.ERROR,
                        "@Query注解'expression'为'BETWEEN'时，'column'不能为空：" + fieldElement.getSimpleName(), fieldElement);
            }

            String className = ((TypeElement) fieldElement.getEnclosingElement()).getQualifiedName().toString();
            //TODO 20231122 待处理BETWEEN的类型 Integer 和 Date 不能比较
            TypeMirror type = fieldElement.asType();
            messager.printMessage(Diagnostic.Kind.WARNING, type.toString());

            String key = className + "#" + column;
            if (columnCountMap.containsKey(key)) {
                int count = columnCountMap.get(key) + 1;
                if (count > 2) {
                    messager.printMessage(Diagnostic.Kind.ERROR,
                            "@Query注解'expression'为'BETWEEN'时，'column'相同的必须成对使用：" + fieldElement.getSimpleName() + "，未为成对的column：" + column, fieldElement);
                } else {
                    columnCountMap.put(key, count);
                }
            } else {
                columnCountMap.put(key, 1);
            }

            // 查找同类中相同column,且expression() == ExpressionEnum.BETWEEN
            boolean foundMatch = false;
            for (VariableElement otherFieldElement : ElementFilter.fieldsIn(elementUtils.getAllMembers((TypeElement) fieldElement.getEnclosingElement()))) {
                if (otherFieldElement == fieldElement) {
                    continue;
                }
                Query otherQueryAnnotation = otherFieldElement.getAnnotation(Query.class);
                if (otherQueryAnnotation != null
                        && otherQueryAnnotation.column().equals(column)
                        && otherQueryAnnotation.expression() == ExpressionEnum.BETWEEN
                ) {
                    foundMatch = true;
                    break;
                }
            }

            // 如果无法找到匹配的属性，则报错
            if (!foundMatch) {
                messager.printMessage(Diagnostic.Kind.ERROR,
                        "@Query注解'expression'为'BETWEEN'时，'column'必须成对存在：" + column, fieldElement);
            }
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "------处理 @Query 注解结束------");
        return false;
    }

}
