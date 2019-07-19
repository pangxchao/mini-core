package com.mini.code.impl;

import com.mini.code.Configure;
import com.mini.code.util.Util;
import com.mini.util.StringUtil;
import com.squareup.javapoet.*;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import static java.lang.String.format;
import static javax.lang.model.element.Modifier.*;

public final class CodeBean {
    protected static void run(Configure configure, String className, String tableName, String prefix) throws Exception {
        //  Package Name
        String basePackage = format("%s.entity.base", configure.getPackageName());
        String beanPackage = format("%s.entity", configure.getPackageName());

        // Class Name
        String baseClassName = StringUtil.format("%sBase", className);

        // Class
        ClassName baseClass = ClassName.get(basePackage, baseClassName);
        // ClassName beanClass = ClassName.get(beanPackage, className);

        // 获取所有字段信息
        List<Util.FieldInfo> fieldList = Util.getColumns(configure.getJdbcTemplate(), //
                configure.getDatabaseName(), tableName, prefix);

        // 生成类信息
        TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                .addModifiers(PUBLIC)
                //.addSuperinterface(ParameterizedTypeName.get(baseClass, beanClass))
                .addSuperinterface(baseClass)
                .addSuperinterface(Serializable.class)
                // 生成  Serializable 的常量字段
                .addField(FieldSpec.builder(long.class, "serialVersionUID")
                        .addModifiers(PRIVATE, STATIC, FINAL)
                        .initializer("$L", "-1L")
                        .build())
                // 生成表名常量
                .addField(FieldSpec.builder(String.class, "TABLE")
                        .addModifiers(PUBLIC, STATIC, FINAL)
                        .addJavadoc(" 表名称 $L \n", tableName)
                        .initializer("$S", tableName)
                        .build())
                .addJavadoc("$L.java \n", className)
                .addJavadoc("@author xchao \n");

        // 处理字段常量
        for (Util.FieldInfo fieldInfo : fieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            builder.addField(FieldSpec.builder(String.class, db_name)
                    .addModifiers(PUBLIC, STATIC, FINAL)
                    // 设置初始值
                    .initializer("$S ", fieldInfo.getColumnName())
                    // 注释文档
                    .addJavadoc("$L \n", StringUtil.def(fieldInfo.getRemarks(), fieldInfo.getColumnName()))
                    .build());
        }

        // 处理属性
        for (Util.FieldInfo fieldInfo : fieldList) {
            String name = StringUtil.toJavaName(fieldInfo.getFieldName(), false);
            builder.addField(FieldSpec.builder(fieldInfo.getTypeClass(), name)
                    .addModifiers(PRIVATE)
                    .build());
        }

        // 处理 Getter Setter 方法
        for (Util.FieldInfo fieldInfo : fieldList) {
            String name = StringUtil.toJavaName(fieldInfo.getFieldName(), false);

            // Getter 方法
            builder.addMethod(MethodSpec.methodBuilder("get" + StringUtil.firstUpperCase(name))
                    .addModifiers(PUBLIC)
                    // 设置返回类型
                    .returns(fieldInfo.getTypeClass())
                    .addAnnotation(Override.class)
                    // 方法体内容
                    .addStatement("return $L", name)
                    .build());

            // Setter 方法
            builder.addMethod(MethodSpec.methodBuilder("set" + StringUtil.firstUpperCase(name))
                    .addModifiers(PUBLIC)
                    // 设置返回类型
                    .returns(void.class)
                    // 添加方法参数列表
                    .addParameter(fieldInfo.getTypeClass(), name)
                    .addAnnotation(Override.class)
                    // 添加方法体内容
                    .addStatement("this.$L = $L", name, name)
                    .build());
        }
        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(beanPackage, builder.build()).build();
        javaFile.writeTo(new File(configure.getClassPath()));

        System.out.println("====================================");
        System.out.println("Code Bean : " + className + "\r\n");
    }

    public static void generator(Configure configure, Configure.BeanItem bean) throws Exception {
        run(configure, bean.className, bean.tableName, bean.prefix);
    }
}
