package com.mini.code.impl;

import com.mini.code.Configure;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.MethodSpecBuilder;
import com.mini.code.util.Util;
import com.mini.jdbc.util.Paging;
import com.mini.util.StringUtil;
import com.squareup.javapoet.*;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static com.mini.code.util.Util.getColumns;
import static com.mini.code.util.Util.getPrimaryKey;
import static com.mini.util.StringUtil.firstLowerCase;
import static com.mini.util.StringUtil.toJavaName;
import static javax.lang.model.element.Modifier.*;

public final class CodeServiceBase {
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
        TypeSpec.Builder builder = TypeSpec.interfaceBuilder(info.serviceBaseName)
                .addModifiers(PUBLIC)
                .addJavadoc("$L.java \n", info.serviceBaseName)
                .addJavadoc("@author xchao \n");

        // 生成 getDao() 方法
        builder.addMethod(MethodSpec.methodBuilder("get" + info.daoName)
                .addModifiers(PUBLIC, ABSTRACT)
                .returns(info.daoClass)
                .addJavadoc("获取$N对象 \n", info.daoName)
                .addJavadoc("@return $N对象 \n", info.daoName)
                .build());

        // 生成 insert 方法
        builder.addMethod(MethodSpec.methodBuilder("insert")
                .addModifiers(DEFAULT, PUBLIC)
                .returns(int.class)
                .addParameter(info.beanClass, firstLowerCase(info.beanName))
                .addJavadoc("添加实体信息 \n")
                .addJavadoc("@param $N 实体信息 \n", firstLowerCase(info.beanName))
                .addJavadoc("@return 执行结果 \n")
                .addStatement("return get$N().insert($N)", info.daoName, firstLowerCase(info.beanName))
                .build());

        // 生成 update 方法
        builder.addMethod(MethodSpec.methodBuilder("update")
                .addModifiers(DEFAULT, PUBLIC)
                .returns(int.class)
                .addParameter(info.beanClass, firstLowerCase(info.beanName))
                .addJavadoc("修改实体信息 \n")
                .addJavadoc("@param $N 实体信息 \n", firstLowerCase(info.beanName))
                .addJavadoc("@return 执行结果 \n")
                .addStatement("return get$N().update($N)", info.daoName, firstLowerCase(info.beanName))
                .build());

        // 生成 delete 方法
        builder.addMethod(MethodSpec.methodBuilder("delete")
                .addModifiers(DEFAULT, PUBLIC)
                .returns(int.class)
                .addParameter(info.beanClass, firstLowerCase(info.beanName))
                .addJavadoc("删除实体信息 \n")
                .addJavadoc("@param $N 实体信息 \n", firstLowerCase(info.beanName))
                .addJavadoc("@return 执行结果 \n")
                .addStatement("return get$N().delete($N)", info.daoName, firstLowerCase(info.beanName))
                .build());

        // 生成 deleteById 方法
        builder.addMethod(new MethodSpecBuilder("deleteById")
                .addModifiers(DEFAULT, PUBLIC)
                .returns(int.class)
                .addJavadoc("根据ID删除实体信息 \n")
                .addParameter(pkFieldList, true)
                .addJavadoc("@return 执行结果 \n")
                .addStatement("return get$N().deleteById($N)", info.daoName, //
                        StringUtil.join(",", pkFieldList.stream().map(fieldInfo -> //
                                toJavaName(fieldInfo.getFieldName(), false)) //
                                .collect(Collectors.toList()))) //
                .build());

        // 生成 queryById 方法
        builder.addMethod(new MethodSpecBuilder("queryById")
                .addModifiers(DEFAULT, PUBLIC)
                .returns(info.beanClass)
                .addJavadoc("根据ID查询实体信息 \n")
                .addParameter(pkFieldList, true)
                .addJavadoc("@return 实体信息 \n")
                .addStatement("return get$N().queryById($N)", info.daoName, //
                        StringUtil.join(",", pkFieldList.stream().map(fieldInfo -> //
                                toJavaName(fieldInfo.getFieldName(), false)) //
                                .collect(Collectors.toList()))) //
                .build());

        // 生成 queryAll 方法
        builder.addMethod(MethodSpec.methodBuilder("queryAll")
                .addModifiers(DEFAULT, PUBLIC)
                .returns(ParameterizedTypeName.get(ClassName.get(List.class), info.beanClass))
                .addJavadoc("查询所有实体信息 \n")
                .addJavadoc("@return 实体信息列表 \n")
                .addStatement("return get$N().queryAll()", info.daoName)
                .build());

        // 生成 queryAll(Paging) 方法
        builder.addMethod(MethodSpec.methodBuilder("queryAll")
                .addModifiers(DEFAULT, PUBLIC)
                .returns(ParameterizedTypeName.get(ClassName.get(List.class), info.beanClass))
                .addParameter(Paging.class, "paging")
                .addJavadoc("查询所有实体信息 \n")
                .addJavadoc("@param $N 分布工具\n", "paging")
                .addJavadoc("@return 实体信息列表 \n")
                .addStatement("return get$N().queryAll($N)", info.daoName, "paging")
                .build());

        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(info.serviceBasePackage, builder.build()).build();
        javaFile.writeTo(new File(configure.getClassPath()));

        System.out.println("====================================");
        System.out.println("Code Service : " + info.beanName + "\r\n");
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
        if (isCover || !Util.exists(configure, info.serviceBasePackage, info.serviceBaseName)) {
            run(configure, info, bean.tableName, bean.prefix, fieldList, pkFieldList);
        }
    }
}
