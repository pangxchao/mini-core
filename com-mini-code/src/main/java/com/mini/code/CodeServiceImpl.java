package com.mini.code;

import com.mini.inject.annotation.Implemented;
import com.squareup.javapoet.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;

import static com.mini.util.StringUtil.firstLowerCase;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

public final class CodeServiceImpl {
    public static final String CLASS_NAME = Config.JAVA_NAME + "ServiceImpl";

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println("========= Start Code Service Impl ==");
        System.out.println("====================================");

        ClassName serviceClassName = ClassName.get(Config.PACKAGE_NAME, CodeService.CLASS_NAME);
        ClassName daoClassName = ClassName.get(Config.PACKAGE_NAME, CodeDao.CLASS_NAME);

        // /**
        //  * InitInfoServiceImpl.java
        //  * @author xchao
        //  */
        // @Singleton
        // @Implemented(value = ${CodeService.CLASS_NAME}.class, name = "firstLowerCase(${CodeService.CLASS_NAME})")
        TypeSpec.Builder builder = TypeSpec.classBuilder(CLASS_NAME)
                .addModifiers(PUBLIC)
                .addSuperinterface(ClassName.get(Config.PACKAGE_NAME, CodeService.CLASS_NAME))
                .addAnnotation(Singleton.class)
                .addAnnotation(AnnotationSpec.builder(Implemented.class)
                        .addMember("value", "$T.class", serviceClassName)
                        .addMember("name", "$S", firstLowerCase(CodeService.CLASS_NAME))
                        .build())
                .addJavadoc("$L.java \n", CLASS_NAME)
                .addJavadoc("@author xchao \n");

        //@Inject
        //@Named("initInfoDao")
        //private InitInfoDao initInfoDao;
        builder.addField(FieldSpec.builder(daoClassName, firstLowerCase(CodeDao.CLASS_NAME), PRIVATE)
                .addAnnotation(Inject.class)
                .addAnnotation(AnnotationSpec.builder(Named.class)
                        .addMember("value", "$S", firstLowerCase(CodeDao.CLASS_NAME))
                        .build())
                .build());


        //@Override
        //public ${CodeDao.CLASS_NAME} get${CodeDao.CLASS_NAME}() {
        //    Objects.requireNonNull(initInfoDao);
        //    return initInfoDao;
        //}
        builder.addMethod(MethodSpec.methodBuilder("get" + CodeDao.CLASS_NAME)
                .addModifiers(PUBLIC)
                .returns(daoClassName)
                .addAnnotation(Override.class)
                .addStatement("return $L", firstLowerCase(CodeDao.CLASS_NAME))
                .build());


        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(Config.PACKAGE_NAME, builder.build()).build();
        javaFile.writeTo(new File(Config.CLASS_PATH));

        System.out.println("====================================");
        System.out.println("========= End Code Service Impl ====");
        System.out.println("====================================");
        System.out.println("\r\n");
    }
}
