package com.mini.code.impl;

import com.mini.code.Configure;
import com.mini.code.Configure.BeanItem;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.MethodSpecBuilder;
import com.mini.code.util.TypeSpecBuilder;
import com.mini.jdbc.JdbcTemplate;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;

import static com.mini.util.StringUtil.firstLowerCase;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

@SuppressWarnings("DuplicatedCode")
public final class CodeDaoImpl {

    /**
     * 生成代码
     * @param configure 数据库与实体配置信息
     * @param info      所有类的信息
     * @param bean      数据库实体信息
     * @param cover     true-文件存在时覆盖，false-文件存在时不覆盖
     */
    public static void generator(Configure configure, ClassInfo info, BeanItem bean, boolean cover) throws Exception {
        if (!cover && configure.exists(info.daoImplPackage, info.daoImplName)) {
            return;
        }

        JavaFile.builder(info.daoImplPackage, TypeSpecBuilder
                // 类名称
                .classBuilder(info.daoImplName)
                // public 类
                .addModifiers(PUBLIC)
                // 实现 Dao 接口
                .addSuperinterface(info.daoClass)
                // 添加类注释文档
                .addJavadoc("$L.java \n", info.daoImplName)
                .addJavadoc("@author xchao \n")

                // 生成 Singleton 单例注解
                .addAnnotation(Singleton.class)

                //  生成 Named 名称限制注解
                .addAnnotation(AnnotationSpec.builder(Named.class)
                        .addMember("value", "$S", firstLowerCase(info.daoName))
                        .build())

                // 生成JdbcTemplate属性,并添加依赖注入注解
                .addField(FieldSpec.builder(JdbcTemplate.class, "jdbcTemplate", PRIVATE)
                        .addAnnotation(Inject.class)
                        .build())

                // 生成 writeTemplate() 方法
                .addMethod(MethodSpecBuilder
                        .methodBuilder("writeTemplate")
                        .addModifiers(PUBLIC)
                        .returns(JdbcTemplate.class)
                        .addAnnotation(Override.class)
                        .addStatement("return jdbcTemplate").build())

                // 生成 readTemplate() 方法
                .addMethod(MethodSpecBuilder
                        .methodBuilder("readTemplate")
                        .addModifiers(PUBLIC)
                        .returns(JdbcTemplate.class)
                        .addAnnotation(Override.class)
                        .addStatement("return jdbcTemplate")
                        .build())

                // 生成类
                .build())
                // 生成文件信息，并将文件信息转出到本地
                .build().writeTo(new File(configure.getClassPath()));

        System.out.println("====================================");
        System.out.println("Code Dao Impl : " + info.beanName + "\r\n");
    }
}
