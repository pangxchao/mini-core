package com.mini.code.impl;

import com.mini.code.Configure;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.Util;
import com.mini.jdbc.SQLBuilder;
import com.mini.jdbc.mapper.IMapper;
import com.mini.util.StringUtil;
import com.squareup.javapoet.*;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.mini.code.util.Util.getColumns;
import static com.mini.code.util.Util.getPrimaryKey;
import static com.mini.util.StringUtil.*;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

public final class CodeMapper {
    /**
     * 生成代码
     * @param configure   数据库与实体配置信息
     * @param info        所有类的信息
     * @param tableName   数据库表名
     * @param prefix      字段名前缀
     * @param fieldList   所有字段信息
     * @param pkFieldList 主键字段信息
     */
    protected static void run(Configure configure, ClassInfo info, String tableName, String prefix, //
            List<Util.FieldInfo> fieldList, List<Util.FieldInfo> pkFieldList) throws Exception {
        // 生成类信息
        TypeSpec.Builder builder = TypeSpec.classBuilder(info.mapperName)
                .addModifiers(PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(IMapper.class), info.beanClass))
                .addAnnotation(Singleton.class)
                .addAnnotation(AnnotationSpec.builder(Named.class)
                        .addMember("value", "$S", firstLowerCase(info.mapperName))
                        .build())
                .addJavadoc("$L.java \n", info.mapperName)
                .addJavadoc("@author xchao \n");

        // 实现的方法 get(ResultSet rs, int number)
        builder.addMethod(get(info, fieldList) //
                .build());

        // builder 实现
        builder.addType(builder(info, fieldList)
                .build());

        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(info.mapperPackage, builder.build()).build();
        javaFile.writeTo(new File(configure.getClassPath()));

        System.out.println("====================================");
        System.out.println("Code Mapper : " + info.beanName + "\r\n");
    }

    // 实现 get(ResultSet rs, int number) 方法
    private static MethodSpec.Builder get(ClassInfo info, List<Util.FieldInfo> fieldList) {
        MethodSpec.Builder method = MethodSpec.methodBuilder("get")
                .addAnnotation(Override.class)
                .addModifiers(PUBLIC)
                .returns(info.beanClass)
                .addParameter(ResultSet.class, "rs")
                .addParameter(int.class, "number")
                .addException(SQLException.class);
        method.addStatement("$T $L = new $T()", info.beanClass, firstLowerCase(info.beanName), info.beanClass);
        for (Util.FieldInfo fieldInfo : fieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = StringUtil.toJavaName(fieldInfo.getFieldName(), false);
            method.addComment(fieldInfo.getRemarks());
            method.addStatement("$L.set$L(rs.get$L($T.$L))", //
                    firstLowerCase(info.beanName), //
                    firstUpperCase(name), //
                    firstUpperCase(fieldInfo.getTypeClass().getSimpleName()), //
                    info.beanClass, //
                    db_name); //
        }
        method.addStatement("return $L", firstLowerCase(info.beanName));
        return method;
    }

    // builder 实现
    private static TypeSpec.Builder builder(ClassInfo info, List<Util.FieldInfo> fieldList) {
        MethodSpec.Builder method = MethodSpec.constructorBuilder()
                .addModifiers(PUBLIC);
        for (Util.FieldInfo fieldInfo : fieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            method.addCode("// $L \n", fieldInfo.getRemarks());
            method.addStatement("select($T.$L)", info.beanClass, db_name);
        }
        method.addCode("// $L \n", "表名称");
        method.addStatement("from($T.TABLE)", info.beanClass);
        return TypeSpec.classBuilder(format("%sBuilder", info.beanName))
                .addModifiers(PUBLIC, STATIC)
                .superclass(SQLBuilder.class)
                .addJavadoc("$N.java \n", format("%sBuilder", info.beanName))
                .addJavadoc("@author xchao \n")
                .addMethod(method.build());
    }

    /**
     * 生成java代码
     * @param configure 数据库与实体配置信息
     * @param bean      数据库表与实体关联配置
     * @param isCover   是否覆盖已存在的文件
     */
    public static void generator(Configure configure, Configure.BeanItem bean, boolean isCover) throws Exception {
        List<Util.FieldInfo> pkFieldList = getPrimaryKey(configure.getJdbcTemplate(), //
                configure.getDatabaseName(), bean.tableName, bean.prefix);  //
        List<Util.FieldInfo> fieldList = getColumns(configure.getJdbcTemplate(), //
                configure.getDatabaseName(), bean.tableName, bean.prefix); //
        ClassInfo info = new ClassInfo(configure, bean.className);

        // 不存在或者覆盖时生成
        if (isCover || !Util.exists(configure, info.mapperPackage, info.mapperName)) {
            run(configure, info, bean.tableName, bean.prefix, fieldList, pkFieldList);
        }
    }
}
