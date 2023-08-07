package com.gitee.whzzone.processor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Create by whz at 2023/8/5
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.gitee.whzzone.common.annotation.QuerySort")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class QuerySortProcessor extends AbstractProcessor {

    private Messager messager;
    private List<String> classNameList;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        classNameList = new ArrayList<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "------开始校验 @QuerySort 注解------");
        for (TypeElement annotation : annotations) {
            for (VariableElement fieldElement : ElementFilter.fieldsIn(roundEnv.getElementsAnnotatedWith(annotation))) {
                String className = ((TypeElement) fieldElement.getEnclosingElement()).getQualifiedName().toString();
                if (classNameList.contains(className)) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "@QuerySort注解在一个类中只能标记一个属性", fieldElement);
                }else {
                    classNameList.add(className);
                }
            }
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "------处理 @QuerySort 注解结束------");
        return false;
    }

}
