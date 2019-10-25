package com.mini.code.impl;

import com.mini.code.Configure;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.Util;
import com.mini.jdbc.SQLBuilder;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                .addSuperinterface(Serializable.class)
                .addJavadoc("$L.java \n", info.beanName)
                .addJavadoc("@author xchao \n");

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
                .initializer("$S", tableName)
                .build());

        // 处理字段常量
        const_builder(fieldList, builder);

        // 生成默认无参构造方法
        builder.addMethod(MethodSpec.constructorBuilder()
                .addModifiers(PUBLIC)
                .build());

        // 处理属性
        prop_builder(fieldList, builder);

        // 为每个属性生成 Getter Setter 方法
        getter_setter(fieldList, builder);

        // 生成私有 Builder 构造方法
        builder.addMethod(private_builder_method(info, fieldList).build());

        // 生成静态无参数 newBuilder 方法
        builder.addMethod(MethodSpec.methodBuilder("newBuilder")
                .addModifiers(PUBLIC, STATIC)
                .returns(info.builderClass)
                .addStatement("return new $T()", info.builderClass)
                .build());

        // 生成静态 copy newBuilder 方法
        builder.addMethod(copy_builder(info, fieldList)
                .build());

        // 生成静态 Mapper mapper 方法
        builder.addMethod(mapper_builder(info, fieldList).build());

        // 生成静态的 Builder 类
        builder.addType(builder(info, fieldList).build());

        // 生成静态的 SQLBuilder 类
        builder.addType(sql_builder(info, fieldList).build());

        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(info.beanPackage, builder.build()).build();
        javaFile.writeTo(new File(configure.getClassPath()));

        System.out.println("====================================");
        System.out.println("Code Bean : " + info.beanName + "\r\n");
    }


    // 生成属性
    private static void prop_builder(List<FieldInfo> fieldList, TypeSpec.Builder builder) {
        for (FieldInfo fieldInfo : fieldList) {
            String name = toJavaName(fieldInfo.getFieldName(), false);
            builder.addField(FieldSpec.builder(fieldInfo.getTypeClass(), name)
                    .addModifiers(PRIVATE)
                    .build());
        }
    }

    // 生成字段常量
    private static void const_builder(List<FieldInfo> fieldList, TypeSpec.Builder builder) {
        for (FieldInfo fieldInfo : fieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            builder.addField(FieldSpec.builder(String.class, db_name)
                    .addModifiers(PUBLIC, STATIC, FINAL)
                    .initializer("$S ", fieldInfo.getColumnName())
                    .addJavadoc("$L \n", def(fieldInfo.getRemarks(), fieldInfo.getColumnName()))
                    .build());
        }
    }

    // 生成setter setter 方法
    private static void getter_setter(List<FieldInfo> fieldList, TypeSpec.Builder builder) {
        for (FieldInfo fieldInfo : fieldList) {
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
    }

    // 生成私有 Builder 构造方法
    private static MethodSpec.Builder private_builder_method(ClassInfo info, List<FieldInfo> fieldList) {
        MethodSpec.Builder method = MethodSpec.constructorBuilder()
                .addModifiers(PRIVATE)
                .addParameter(info.builderClass, "builder");
        for (FieldInfo fieldInfo : fieldList) {
            String name = toJavaName(fieldInfo.getFieldName(), false);
            method.addStatement("set$L(builder.$L)", firstUpperCase(name), name);
        }
        return method;
    }

    // 生成 copy 构造方法
    private static MethodSpec.Builder copy_builder(ClassInfo info, List<FieldInfo> fieldList) {
        MethodSpec.Builder method = MethodSpec.methodBuilder("newBuilder")
                .addModifiers(PUBLIC, STATIC)
                .returns(info.builderClass)
                .addParameter(info.beanClass, "copy")
                .addStatement("$T builder = new $T()", info.builderClass, info.builderClass);
        for (FieldInfo fieldInfo : fieldList) {
            String name = toJavaName(fieldInfo.getFieldName(), false);
            method.addStatement("builder.$L = copy.get$L()", name, firstUpperCase(name));
        }
        method.addStatement("return builder");
        return method;
    }

    // 生成 Mapper 方法
    private static MethodSpec.Builder mapper_builder(ClassInfo info, List<FieldInfo> fieldList) {
        MethodSpec.Builder method = MethodSpec.methodBuilder("mapper")
                .addModifiers(PUBLIC, STATIC)
                .returns(info.beanClass)
                .addParameter(ResultSet.class, "rs")
                .addParameter(int.class, "number")
                .addException(SQLException.class)
                .addStatement("$T builder = $T.newBuilder()", info.builderClass, info.beanClass);
        for (FieldInfo fieldInfo : fieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), false);
            String type_name = firstUpperCase(fieldInfo.getTypeClass().getSimpleName());
            method.addStatement("builder.$L = rs.get$L($L)", name, type_name, db_name);
        }
        method.addStatement("return builder.build()");
        return method;
    }

    // 生成 Builder 对象
    private static TypeSpec.Builder builder(ClassInfo info, List<Util.FieldInfo> fieldList) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(info.builderName)
                .addModifiers(PUBLIC, STATIC, FINAL);

        // 为每个字段生成一个属性
        prop_builder(fieldList, builder);

        // 生成私有无参构造方法
        builder.addMethod(MethodSpec.constructorBuilder()
                .addModifiers(PRIVATE)
                .build());

        // 为每个属性生成一个方法
        for (Util.FieldInfo fieldInfo : fieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), false);
            builder.addMethod(MethodSpec.methodBuilder(name)
                    .addModifiers(PUBLIC, FINAL)
                    .returns(info.builderClass)
                    .addParameter(fieldInfo.getTypeClass(), name)
                    .addStatement("this.$L = $L", name, name)
                    .addStatement("return this")
                    .build());
        }

        // 生成 builder 方法
        builder.addMethod(MethodSpec.methodBuilder("build")
                .addModifiers(PUBLIC, FINAL)
                .returns(info.beanClass)
                .addAnnotation(Nonnull.class)
                .addStatement("return new $T(this)", info.beanClass)
                .build());

        return builder;
    }

    // 生成 SQL Builder 对象
    private static TypeSpec.Builder sql_builder(ClassInfo info, List<Util.FieldInfo> fieldList) {
        MethodSpec.Builder method = MethodSpec.constructorBuilder()
                .addModifiers(PROTECTED);
        // 生成查询每个字体的SQL
        for (Util.FieldInfo fieldInfo : fieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), false);
            method.addStatement("select($L)", db_name);
        }
        method.addStatement("select(TABLE)");
        return TypeSpec.classBuilder(info.sqlName)
                .addModifiers(PUBLIC, STATIC)
                .superclass(SQLBuilder.class)
                .addMethod(method.build());
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
