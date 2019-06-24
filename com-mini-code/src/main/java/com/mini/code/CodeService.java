package com.mini.code;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

public final class CodeService {
    public static final String CLASS_NAME = Config.JAVA_NAME + "Service";

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println("========= Start Code Service =======");
        System.out.println("====================================");


        // /**
        //  * ${JAVA_NAME}.java
        //  * @author xchao
        //  */
        // public interface ${JAVA_NAME}
        TypeSpec.Builder builder = TypeSpec.interfaceBuilder(CLASS_NAME)
                .addModifiers(PUBLIC)
                .addJavadoc("$L.java \n", CLASS_NAME)
                .addJavadoc("@author xchao \n");

        // InitInfoDao getInitInfoDao();
        builder.addMethod(MethodSpec.methodBuilder("get" + CodeDao.CLASS_NAME)
                .addModifiers(PUBLIC, ABSTRACT)
                .returns(ClassName.get(Config.PACKAGE_NAME, CodeDao.CLASS_NAME))
                .build());

        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(Config.PACKAGE_NAME, builder.build()).build();
        javaFile.writeTo(new File(Config.CLASS_PATH));

        System.out.println("====================================");
        System.out.println("========= End Code Service =========");
        System.out.println("====================================");
        System.out.println("\r\n");
    }
}
