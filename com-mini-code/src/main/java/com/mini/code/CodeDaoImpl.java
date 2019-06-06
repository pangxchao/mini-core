package com.mini.code;

import com.mini.util.dao.IDao;
import com.mini.util.lang.StringUtil;
import com.squareup.javapoet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;

import static javax.lang.model.element.Modifier.*;

public final class CodeDaoImpl {
    public static final String CLASS_NAME = Config.JAVA_NAME + "DaoImpl";

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println("========= Start Code Dao Impl ======");
        System.out.println("====================================");

        ClassName mapperClassName = ClassName.get(Config.PACKAGE_NAME, CodeMapper.CLASS_NAME);
        ClassName daoClassName = ClassName.get(Config.PACKAGE_NAME, CodeDao.CLASS_NAME);
        // 生成类信息
        TypeSpec.Builder builder = TypeSpec.classBuilder(CLASS_NAME)
                .addModifiers(PUBLIC)
                .addSuperinterface(daoClassName)
                // 添加 Repository 注解信息
                .addAnnotation(AnnotationSpec.builder(Repository.class)
                        .addMember("value", "$S", StringUtil.firstLowerCase(CodeDao.CLASS_NAME))
                        .build())
                // 添加类说明
                .addJavadoc("$L.java \n", CLASS_NAME)
                .addJavadoc("@author xchao \n")
                // 添加 private final IDao daoTemplate; 属性
                .addField(FieldSpec.builder(IDao.class, "daoTemplate", PRIVATE, FINAL).build())
                // 添加 private final XxMapper xxMapper; 属性
                .addField(FieldSpec.builder(mapperClassName, StringUtil.firstLowerCase(
                        CodeMapper.CLASS_NAME), PRIVATE, FINAL).build())
                // 添加构造方法(IDao daoTemplate, XxMapper xxMapper)
                .addMethod(MethodSpec.constructorBuilder()
                        .addModifiers(PUBLIC)
                        .addAnnotation(Autowired.class)
                        .addParameter(IDao.class, "daoTemplate")
                        .addParameter(mapperClassName, StringUtil.firstLowerCase(CodeMapper.CLASS_NAME))
                        .addStatement("this.daoTemplate = daoTemplate")
                        .addStatement("this.$L = $L", StringUtil.firstLowerCase(CodeMapper.CLASS_NAME),
                                StringUtil.firstLowerCase(CodeMapper.CLASS_NAME))
                        .build())
                // 添加 public IDao getWriteDao 方法
                .addMethod(MethodSpec.methodBuilder("getWriteDao")
                        .addModifiers(PUBLIC)
                        .returns(IDao.class)
                        .addAnnotation(Override.class)
                        .addStatement("return daoTemplate")
                        .build())
                // 添加 public IDao getReadDao 方法
                .addMethod(MethodSpec.methodBuilder("getReadDao")
                        .addModifiers(PUBLIC)
                        .returns(IDao.class)
                        .addAnnotation(Override.class)
                        .addStatement("return daoTemplate")
                        .build())
                // 添加 public UserMapper getUserMapper 方法
                .addMethod(MethodSpec.methodBuilder("get" + CodeMapper.CLASS_NAME)
                        .addModifiers(PUBLIC)
                        .returns(mapperClassName)
                        .addAnnotation(Override.class)
                        .addStatement("return $L", StringUtil.firstLowerCase(CodeMapper.CLASS_NAME))
                        .build());
        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(Config.PACKAGE_NAME, builder.build()).build();
        javaFile.writeTo(new File(Config.CLASS_PATH));

        System.out.println("====================================");
        System.out.println("========= End Code Dao Impl ========");
        System.out.println("====================================");
        System.out.println("\r\n");
    }


}
