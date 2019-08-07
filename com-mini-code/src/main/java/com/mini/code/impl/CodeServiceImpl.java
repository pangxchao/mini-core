package com.mini.code.impl;

import com.mini.code.Configure;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.Util;
import com.squareup.javapoet.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.util.List;

import static com.mini.code.util.Util.getColumns;
import static com.mini.code.util.Util.getPrimaryKey;
import static com.mini.util.StringUtil.firstLowerCase;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

public final class CodeServiceImpl {
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
        TypeSpec.Builder builder = TypeSpec.classBuilder(info.serviceImplName)
                .addModifiers(PUBLIC)
                .addSuperinterface(info.serviceClass)
                .addAnnotation(Singleton.class)
                .addAnnotation(AnnotationSpec.builder(Named.class)
                        .addMember("value", "$S", firstLowerCase(info.serviceName))
                        .build())
                .addJavadoc("$L.java \n", info.serviceImplName)
                .addJavadoc("@author xchao \n");

        // 生成Dao属性
        builder.addField(FieldSpec.builder(info.daoClass, firstLowerCase(info.daoName), PRIVATE).build());

        // 生成setDao方法，并添加依赖注入注解
        builder.addMethod(MethodSpec.methodBuilder("set" + info.daoName)
                .addModifiers(PUBLIC)
                .returns(void.class)
                .addAnnotation(Inject.class)
                .addParameter(info.daoClass, firstLowerCase(info.daoName))
                .addStatement("this.$N = $N", firstLowerCase(info.daoName), firstLowerCase(info.daoName))
                .build());

        // 生成getDao方法
        builder.addMethod(MethodSpec.methodBuilder("get" + info.daoName)
                .addModifiers(PUBLIC)
                .returns(info.daoClass)
                .addAnnotation(Override.class)
                .addStatement("return $L", firstLowerCase(info.daoName))
                .build());

        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(info.serviceImplPackage, builder.build()).build();
        javaFile.writeTo(new File(configure.getClassPath()));

        System.out.println("====================================");
        System.out.println("Code Service Impl : " + info.beanName + "\r\n");
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
        if (isCover || !Util.exists(configure, info.serviceImplPackage, info.serviceImplName)) {
            run(configure, info, bean.tableName, bean.prefix, fieldList, pkFieldList);
        }
    }
}
