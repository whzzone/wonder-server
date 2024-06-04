package com.gitee.whzzone.processor;

import com.gitee.whzzone.annotation.DataScope;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * @author Create by whz at 2024/2/19
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.gitee.whzzone.annotation.DataScope")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DataScopeProcessor extends AbstractProcessor {

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "------开始校验 @DataScope 注解------");
        for (Element element  : roundEnv.getElementsAnnotatedWith(DataScope.class)) {
            /*if (element instanceof ExecutableElement) {
                TypeElement enclosingClass = (TypeElement) element.getEnclosingElement();
                if (!enclosingClass.getKind().isInterface()) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "@DataScope只能使用在接口类中", element);
                }
            }*/

            TypeElement enclosingClass = (TypeElement) element.getEnclosingElement();
            if (!enclosingClass.getKind().isInterface()) {
                messager.printMessage(Diagnostic.Kind.ERROR, "@DataScope只能使用在接口类中", element);
            }
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "------处理 @DataScope 注解结束------");
        return true;
    }

}
