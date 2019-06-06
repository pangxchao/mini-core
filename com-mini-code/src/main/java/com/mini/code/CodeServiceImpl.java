package com.mini.code;

import com.mini.util.lang.StringUtil;
import com.squareup.javapoet.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

import static javax.lang.model.element.Modifier.*;

public final class CodeServiceImpl {
    public static final String CLASS_NAME = Config.JAVA_NAME + "ServiceImpl";

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println("========= Start Code Service Impl ==");
        System.out.println("====================================");

        ClassName daoClassName = ClassName.get(Config.PACKAGE_NAME, CodeDao.CLASS_NAME);
        String daoName = StringUtil.firstLowerCase(CodeDao.CLASS_NAME);
        // 生成类信息
        TypeSpec.Builder builder = TypeSpec.classBuilder(CLASS_NAME)
                .addModifiers(PUBLIC)
                .addSuperinterface(ClassName.get(Config.PACKAGE_NAME, CodeService.CLASS_NAME))
                .addJavadoc("$L.java \n", CLASS_NAME)
                .addJavadoc("@author xchao \n")
                // 添加  private final XxDao xxDao;
                .addField(FieldSpec.builder(daoClassName, daoName, PRIVATE, FINAL).build())
                // 添加构造方法
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(PUBLIC)
                        .addAnnotation(Autowired.class)
                        .addParameter(daoClassName, daoName)
                        .addStatement("this.$L = $L", daoName, daoName)
                        .build())
                // 添加  public XxDao getXxDao 方法
                .addMethod(MethodSpec.methodBuilder("get" + CodeDao.CLASS_NAME)
                        .addModifiers(PUBLIC)
                        .returns(daoClassName)
                        .addAnnotation(Override.class)
                        .addStatement("return $L", daoName)
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
