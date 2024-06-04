package com.gitee.whzzone.processor;

import com.gitee.whzzone.annotation.EntityField;
import com.google.auto.service.AutoService;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.List;
import io.swagger.annotations.ApiModelProperty;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * @author Create by whz at 2023/8/5
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("com.gitee.whzzone.annotation.EntityField")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class EntityFieldProcessor extends AbstractProcessor {

    private Messager messager;
    private JavacTrees trees;
    private TreeMaker treeMaker;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        trees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        treeMaker = TreeMaker.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "------开始处理 @EntityField 注解------");
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element element : annotatedElements) {

                EntityField entityField = element.getAnnotation(EntityField.class);
                ApiModelProperty apiModelProperty = element.getAnnotation(ApiModelProperty.class);

                boolean addAble = entityField.addAble();
                boolean updateAble = entityField.updateAble();
                boolean insertRequired = entityField.addRequired();
                boolean updateRequired = entityField.updateRequired();

                JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) trees.getTree(element);

                JCTree.JCModifiers modifiers = jcVariableDecl.getModifiers();

                List<JCTree.JCAnnotation> jcAnnotationList = modifiers.getAnnotations();

                // 存在 @ApiModelProperty，进行修改
                if (apiModelProperty != null) {
                    for (JCTree.JCAnnotation jcAnnotation : jcAnnotationList) {
                        JCTree annotationType = jcAnnotation.getAnnotationType();
                        if (annotationType.type.toString().equals("io.swagger.annotations.ApiModelProperty")) {

                            List<JCTree.JCExpression> arguments = jcAnnotation.getArguments();

                            for (JCTree.JCExpression argument : arguments) {

                                JCTree.JCAssign tree = (JCTree.JCAssign) argument.getTree();
                                if (tree.lhs.toString().equals("value")) {
                                    StringBuilder stringBuilder = new StringBuilder(apiModelProperty.value());
                                    stringBuilder.append(" - ");
                                    if (addAble) {
                                        stringBuilder.append( "【可新增：");
                                        stringBuilder.append(insertRequired ? "必填" : "非必填");
                                        stringBuilder.append("】");
                                    }

                                    if (updateAble) {
                                        stringBuilder.append( "【可编辑：");
                                        stringBuilder.append(updateRequired ? "必填" : "非必填");
                                        stringBuilder.append("】");
                                    }

                                    tree.rhs = treeMaker.Literal(stringBuilder.toString());
                                    break;
                                }
                            }
                        }
                    }
                }else {
                    // 不存在 @ApiModelProperty，新增该注解
                    //TODO 2024-02-23 09:26 待实现新增注解
                }
            }
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "------处理 @EntityField 注解 结束------");
        return true;
    }
}
