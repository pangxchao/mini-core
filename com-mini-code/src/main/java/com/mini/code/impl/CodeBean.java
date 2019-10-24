package com.mini.code.impl;

import com.mini.code.Configure;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.Util;
import com.squareup.javapoet.*;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import static com.mini.code.util.Util.*;
import static com.mini.util.StringUtil.*;
import static javax.lang.model.element.Modifier.*;

public final class CodeBean {
    /**
     * 生成代码
     * @param configure   数据库与实体配置信息
     * @param info        所有类的信息
     * @param tableName   数据库表名
     * @param prefix      字段名前缀
     * @param fieldList   所有字段信息
     * @param PKFieldList 主键字段信息
     * @param FKFieldList 外键字段信息
     */

    protected static void run(Configure configure, ClassInfo info, String tableName, String prefix, List<Util.FieldInfo> fieldList,
            List<Util.FieldInfo> PKFieldList, List<Util.FieldInfo> FKFieldList) throws Exception {
        // 生成类信息
        TypeSpec.Builder builder = TypeSpec.classBuilder(info.beanName)
                .addModifiers(PUBLIC)
                .addSuperinterface(Serializable.class);
        // serialVersionUID 属性，IDEA 编辑器一般不需要该属性
        if (configure.generatorSerialVersionUID()) {
            builder.addField(FieldSpec.builder(long.class, "serialVersionUID")
                    .addModifiers(PRIVATE, STATIC, FINAL)
                    .initializer("$L", "-1L")
                    .build());
        }
        // 生成常量 TABLE 字段
        builder.addField(FieldSpec.builder(String.class, "TABLE")
                .addModifiers(PUBLIC, STATIC, FINAL)
                .addJavadoc(" 表名称 $L \n", tableName)
                .initializer("$S", tableName).build())
                .addJavadoc("$L.java \n", info.beanName)
                .addJavadoc("@author xchao \n");

        // 处理字段常量
        for (Util.FieldInfo fieldInfo : fieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            builder.addField(FieldSpec.builder(String.class, db_name)
                    .addModifiers(PUBLIC, STATIC, FINAL)
                    .initializer("$S ", fieldInfo.getColumnName())
                    .addJavadoc("$L \n", def(fieldInfo.getRemarks(), fieldInfo.getColumnName()))
                    .build());
        }

        // 处理属性
        for (Util.FieldInfo fieldInfo : fieldList) {
            String name = toJavaName(fieldInfo.getFieldName(), false);
            builder.addField(FieldSpec.builder(fieldInfo.getTypeClass(), name)
                    .addModifiers(PRIVATE)
                    .build());
        }

        // 处理 Getter Setter 方法
        for (Util.FieldInfo fieldInfo : fieldList) {
            String name = toJavaName(fieldInfo.getFieldName(), false);
            // Getter 方法
            builder.addMethod(MethodSpec.methodBuilder("get" + firstUpperCase(name))
                    .addModifiers(PUBLIC)
                    // 设置返回类型
                    .returns(fieldInfo.getTypeClass())
                    // 方法体内容
                    .addStatement("return $L", name)
                    .build());
            // Setter 方法
            builder.addMethod(MethodSpec.methodBuilder("set" + firstUpperCase(name))
                    .addModifiers(PUBLIC)
                    // 设置返回类型
                    .returns(void.class)
                    // 添加方法参数列表
                    .addParameter(fieldInfo.getTypeClass(), name)
                    // 添加方法体内容
                    .addStatement("this.$L = $L", name, name)
                    .build());
        }

        // 生成一个 Builder 的静态方法
        builder.addMethod(MethodSpec.methodBuilder("builder")
                .addModifiers(PUBLIC, STATIC)
                .returns(info.builderClass)
                .addStatement("return new $T()", info.builderClass)
                .build());

        // 生成静态的 AbstractBuilder 类
        builder.addType(abstractBuilder(info, fieldList).build());

        // 生成 Builder Class 类
        builder.addType(builder(info).build());

        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(info.beanPackage, builder.build()).build();
        javaFile.writeTo(new File(configure.getClassPath()));

        System.out.println("====================================");
        System.out.println("Code Bean : " + info.beanName + "\r\n");
    }

    // 生成 AbstractBuilder 对象
    private static TypeSpec.Builder abstractBuilder(ClassInfo info, List<Util.FieldInfo> fieldList) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(info.abstractBuilderName)
                .addTypeVariable(TypeVariableName.get("T"))
                .addModifiers(PUBLIC, ABSTRACT, STATIC);

        // 生成实体的不可修改属性
        builder.addField(FieldSpec.builder(info.beanClass, firstLowerCase(info.beanName))
                .addModifiers(PRIVATE, FINAL)
                .initializer("new $T()", info.beanClass)
                .build());

        // 生成一个静态的获取子类的方法
        builder.addMethod(MethodSpec.methodBuilder("getThis")
                .addModifiers(PROTECTED, ABSTRACT)
                .returns(TypeVariableName.get("T"))
                .build());

        // 生成 builder 方法
        builder.addMethod(MethodSpec.methodBuilder("builder")
                .addModifiers(PUBLIC)
                .returns(info.beanClass)
                .addStatement("return this.$L", firstLowerCase(info.beanName))
                .build());

        // 为每个属性生成一个方法
        for (Util.FieldInfo fieldInfo : fieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), false);
            builder.addMethod(MethodSpec.methodBuilder(name)
                    .addModifiers(PUBLIC, FINAL)
                    .returns(TypeVariableName.get("T"))
                    .addParameter(fieldInfo.getTypeClass(), name)
                    .addStatement("$L.set$L($L)", firstLowerCase(info.beanName), firstUpperCase(name), name)
                    .addStatement("return getThis()")
                    .build());
        }
        return builder;
    }


    // builder 实现
    private static TypeSpec.Builder builder(ClassInfo info) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(info.builderName)
                .superclass(ParameterizedTypeName.get(info.abstractBuilderClass, info.builderClass))
                .addModifiers(PRIVATE, STATIC);

        // 生成一个静态的获取子类的方法
        builder.addMethod(MethodSpec.methodBuilder("getThis")
                .addModifiers(PROTECTED)
                .addAnnotation(Override.class)
                .returns(info.builderClass)
                .addStatement("return this")
                .build());

        return builder;
    }

    /**
     * 生成java代码
     * @param configure 数据库与实体配置信息
     * @param bean      数据库表与实体关联配置
     * @param isCover   是否覆盖已存在的文件
     */
    public static void generator(Configure configure, Configure.BeanItem bean, boolean isCover) throws Exception {
        List<Util.FieldInfo> FKFileList = getImportedKeys(configure.getJdbcTemplate(), //
                configure.getDatabaseName(), bean.tableName, bean.prefix);
        List<Util.FieldInfo> PKFieldList = getPrimaryKey(configure.getJdbcTemplate(), //
                configure.getDatabaseName(), bean.tableName, bean.prefix);  //
        List<Util.FieldInfo> fieldList = getColumns(configure.getJdbcTemplate(), //
                configure.getDatabaseName(), bean.tableName, bean.prefix); //
        ClassInfo info = new ClassInfo(configure, bean.className);

        // 不存在或者覆盖时生成
        if (isCover || !Util.exists(configure, info.beanPackage, info.beanName)) {
            run(configure, info, bean.tableName, bean.prefix, fieldList, PKFieldList, FKFileList);
        }
    }
}
