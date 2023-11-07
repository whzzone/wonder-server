package com.gitee.whzzone.processor;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * @author Create by whz at 2023/8/5
 */
//@AutoService(Processor.class)
@SupportedAnnotationTypes("com.gitee.whzzone.common.annotation.SaveField")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SwaggerFieldProcessor extends AbstractProcessor {

    //TODO 现在是循环写的时候报错

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "------开始处理 @SaveField 注解------");
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element element : annotatedElements) {
                // 检查是否是字段声明
                if (element.getKind().isField()) {
                    // 修改注解的值
                    String newApiValue = "更新字段";
                    modifyAnnotationValue(element, "ApiModelProperty", "value", newApiValue);
                }
            }
        }
        return true;
    }

    private void modifyAnnotationValue(Element element, String annotationSimpleName, String attributeName, String value) {
        // 获取元素所在的包名和类名
        String packageName = processingEnv.getElementUtils().getPackageOf(element).toString();
        String className = ((TypeElement) element.getEnclosingElement()).getQualifiedName().toString();

        // 获取原来的注解值
        String originalAnnotationValue = element.getAnnotationMirrors().stream()
                .filter(mirror -> mirror.getAnnotationType().toString().equals(annotationSimpleName))
                .map(mirror -> mirror.getElementValues().entrySet().stream()
                        .filter(entry -> entry.getKey().getSimpleName().toString().equals(attributeName))
                        .map(entry -> entry.getValue().getValue().toString())
                        .findFirst().orElse(""))
                .findFirst().orElse("");

        // 在原来的注解值后面添加要追加的字符串
        String modifiedValue = originalAnnotationValue + " - " + value;

        // 构建要修改的注解字符串
        String modifiedAnnotation = "@" + annotationSimpleName + "(\"" + attributeName + "\", \"" + modifiedValue + "\")";

        // 构建修改后的源代码
        String modifiedSourceCode = String.format("package %s;\n\n%s\npublic class %s { }", packageName, modifiedAnnotation, className);

        // 获取Java源文件对象
        JavaFileObject javaFileObject;
        try {
            javaFileObject = (JavaFileObject) processingEnv.getFiler().getResource(StandardLocation.SOURCE_PATH, packageName, className + ".java");
        } catch (IOException e) {
            javaFileObject = null;
        }

        if (javaFileObject != null) {
            try {
                // 打开Java源文件的输出流
                Writer writer = javaFileObject.openWriter();

                // 写入修改后的源代码
                writer.write(modifiedSourceCode);
                // 关闭输出流
                writer.close();
            } catch (IOException e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to modify annotation: " + e.getMessage());
            }
        } else {
            try {
                // 创建新的Java源文件
                processingEnv.getFiler().createSourceFile(className).openWriter().append(modifiedSourceCode).close();
            } catch (Exception e) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to modify annotation: " + e.getMessage());
            }
        }

        // 打印修改前后的注解信息
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Before modification: " + element);
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "After modification: " + modifiedSourceCode);
    }
}
