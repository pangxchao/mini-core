package com.mini.code;

import com.mini.dao.IBase;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.File;

import static javax.lang.model.element.Modifier.PUBLIC;

public final class CodeDao {
    public static final String CLASS_NAME = Config.JAVA_NAME + "Dao";

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println("========= Start Code Dao ===========");
        System.out.println("====================================");
        ClassName beanClassName = ClassName.get(Config.PACKAGE_NAME, CodeBean.CLASS_NAME);
        TypeSpec.Builder builder = TypeSpec.interfaceBuilder(CLASS_NAME)
                .addModifiers(PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IBase.class), beanClassName))
                .addJavadoc("$L.java \n", CLASS_NAME)
                .addJavadoc("@author xchao \n");

        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(Config.PACKAGE_NAME, builder.build()).build();
        javaFile.writeTo(new File(Config.CLASS_PATH));

        System.out.println("====================================");
        System.out.println("========= End Code  Dao ============");
        System.out.println("====================================");
        System.out.println("\r\n");
    }
}
