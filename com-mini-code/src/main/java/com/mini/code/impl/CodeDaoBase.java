package com.mini.code.impl;

import com.mini.code.Configure;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.Util;
import com.mini.jdbc.BasicsDao;
import com.mini.jdbc.SQLBuilder;
import com.mini.jdbc.util.Paging;
import com.squareup.javapoet.*;

import java.io.File;
import java.util.List;

import static com.mini.code.util.Util.*;
import static com.mini.util.StringUtil.firstLowerCase;
import static com.mini.util.StringUtil.toJavaName;
import static javax.lang.model.element.Modifier.DEFAULT;
import static javax.lang.model.element.Modifier.PUBLIC;

public final class CodeDaoBase {
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
        TypeSpec.Builder builder = TypeSpec.interfaceBuilder(info.daoBaseName)
                .addModifiers(PUBLIC)
                .addSuperinterface(BasicsDao.class)
                .addJavadoc("$L.java \n", info.daoBaseName)
                .addJavadoc("@author xchao \n");

        // 生成 insert 方法
        builder.addMethod(insert(info, fieldList).build());

        // 生成 replace 方法
        builder.addMethod(replace(info, fieldList).build());

        // 生成 update 方法
        builder.addMethod(update(info, fieldList, PKFieldList).build());

        // 生成 delete 方法
        builder.addMethod(delete(info, PKFieldList).build());

        // 生成 deleteById 方法
        builder.addMethod(deleteById(info, PKFieldList).build());

        // 生成 queryById 方法
        builder.addMethod(queryById(info, PKFieldList).build());

        // 生成 queryAll 方法
        builder.addMethod(queryAll(info).build());

        // 生成 queryAll(Paging) 方法
        builder.addMethod(queryAllPaging(info).build());

        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(info.daoBasePackage, builder.build()).build();
        javaFile.writeTo(new File(configure.getClassPath()));

        System.out.println("====================================");
        System.out.println("Code Dao Base : " + info.beanName + "\r\n");
    }

    // 生成 insert 方法
    private static MethodSpec.Builder insert(ClassInfo info, List<Util.FieldInfo> fieldList) {
        MethodSpec.Builder method = MethodSpec.methodBuilder("insert")//
                .addModifiers(DEFAULT, PUBLIC)
                .returns(int.class)//
                .addAnnotation(AnnotationSpec.builder(SuppressWarnings.class)
                        .addMember("value", "DuplicatedCode")
                        .build())
                .addParameter(info.beanClass, firstLowerCase(info.beanName))//
                .addJavadoc("添加实体信息 \n")//
                .addJavadoc("@param $N 实体信息 \n", firstLowerCase(info.beanName))//
                .addJavadoc("@return 执行结果 \n");
        method.addCode("return execute(new $T() {{ \n", SQLBuilder.class);
        method.addStatement("\tinsert_into($T.TABLE)", info.beanClass);
        for (Util.FieldInfo fieldInfo : fieldList) {
            // 自动增长字段不处理
            if (fieldInfo.isAuto()) continue;
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), true);
            // 该字段为int或者long类型，不为主键但是为外键的时候需要特殊处理
            method.addCode("\t// $L \n", fieldInfo.getRemarks());
            method.addStatement("\tvalues($T.$L)", info.beanClass, db_name);
            method.addStatement("\tparams($L.get$L())", firstLowerCase(info.beanName), name);
        }
        method.addStatement("}})");
        return method;
    }

    // 生成 replace 方法
    private static MethodSpec.Builder replace(ClassInfo info, List<Util.FieldInfo> fieldList) {
        MethodSpec.Builder method = MethodSpec.methodBuilder("replace")//
                .addModifiers(DEFAULT, PUBLIC)//
                .returns(int.class)//
                .addAnnotation(AnnotationSpec.builder(SuppressWarnings.class)
                        .addMember("value", "DuplicatedCode")
                        .build())
                .addParameter(info.beanClass, firstLowerCase(info.beanName))//
                .addJavadoc("添加实体信息 \n")//
                .addJavadoc("@param $N 实体信息 \n", firstLowerCase(info.beanName))//
                .addJavadoc("@return 执行结果 \n");
        method.addCode("return execute(new $T() {{ \n", SQLBuilder.class);
        method.addStatement("\treplace_into($T.TABLE)", info.beanClass);
        for (Util.FieldInfo fieldInfo : fieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), true);
            method.addCode("\t// $L \n", fieldInfo.getRemarks());
            method.addStatement("\tvalues($T.$L)", info.beanClass, db_name);
            method.addStatement("\tparams($L.get$L())", firstLowerCase(info.beanName), name);
        }
        method.addStatement("}})");
        return method;
    }

    // 生成 update 方法
    private static MethodSpec.Builder update(ClassInfo info, List<Util.FieldInfo> fieldList, List<Util.FieldInfo> PKFieldList) {
        MethodSpec.Builder method = MethodSpec.methodBuilder("update")//
                .addModifiers(DEFAULT, PUBLIC).returns(int.class)//
                .addParameter(info.beanClass, firstLowerCase(info.beanName))//
                .addJavadoc("修改实体信息 \n")//
                .addJavadoc("@param $N 实体信息 \n", firstLowerCase(info.beanName))//
                .addJavadoc("@return 执行结果 \n");
        method.addCode("return execute(new $T() {{ \n", SQLBuilder.class);
        method.addStatement("\tupdate($T.TABLE)", info.beanClass);
        for (Util.FieldInfo fieldInfo : fieldList) {
            // 自动增长字段不处理
            if (fieldInfo.isAuto()) continue;
            // 其它字段信息
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), true);
            method.addCode("\t// $L \n", fieldInfo.getRemarks());
            method.addStatement("\tset($S, $T.$L)", "%s = ?", info.beanClass, db_name);
            method.addStatement("\tparams($L.get$L())", firstLowerCase(info.beanName), name);
        }
        for (Util.FieldInfo fieldInfo : PKFieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), true);
            method.addCode("\t// $L \n", fieldInfo.getRemarks());
            method.addStatement("\twhere($S, $T.$L)", "%s = ?", info.beanClass, db_name);
            method.addStatement("\tparams($L.get$L())", firstLowerCase(info.beanName), name);
        }
        method.addStatement("}})");
        return method;
    }

    // 生成 delete 方法
    private static MethodSpec.Builder delete(ClassInfo info, List<Util.FieldInfo> PKFieldList) {
        MethodSpec.Builder method = MethodSpec.methodBuilder("delete")//
                .addModifiers(DEFAULT, PUBLIC)//
                .returns(int.class)//
                .addParameter(info.beanClass, firstLowerCase(info.beanName))//
                .addJavadoc("删除实体信息 \n")//
                .addJavadoc("@param $N 实体信息 \n", firstLowerCase(info.beanName))//
                .addJavadoc("@return 执行结果 \n");
        method.addCode("return execute(new $T() {{ \n", SQLBuilder.class);
        method.addStatement("\tdelete().from($T.TABLE)", info.beanClass);
        for (Util.FieldInfo fieldInfo : PKFieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), true);
            method.addCode("\t// $L \n", fieldInfo.getRemarks());
            method.addStatement("\twhere($S, $T.$L)", "%s = ?", info.beanClass, db_name);
            method.addStatement("\tparams($L.get$L())", firstLowerCase(info.beanName), name);
        }
        method.addStatement("}})");
        return method;
    }

    // 生成 deleteById 方法
    private static MethodSpec.Builder deleteById(ClassInfo info, List<Util.FieldInfo> PKFieldList) {
        MethodSpec.Builder method = MethodSpec.methodBuilder("deleteById")//
                .addModifiers(DEFAULT, PUBLIC)//
                .returns(int.class)//
                .addJavadoc("根据ID删除实体信息 \n");
        for (Util.FieldInfo fieldInfo : PKFieldList) {
            String name = toJavaName(fieldInfo.getFieldName(), false);
            method.addParameter(fieldInfo.getTypeClass(), name);
            method.addJavadoc("@param $N $N \n", name, fieldInfo.getRemarks());
        }
        method.addJavadoc("@return 执行结果 \n");
        method.addCode("return execute(new $T() {{ \n", SQLBuilder.class);
        method.addStatement("\tdelete().from($T.TABLE)", info.beanClass);
        for (Util.FieldInfo fieldInfo : PKFieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), true);
            method.addCode("\t// $L \n", fieldInfo.getRemarks());
            method.addStatement("\twhere($S, $T.$L)", "%s = ?", info.beanClass, db_name);
            method.addStatement("\tparams($N)", firstLowerCase(name));
        }
        method.addStatement("}})");
        return method;
    }

    // 生成 queryById 方法
    private static MethodSpec.Builder queryById(ClassInfo info, List<Util.FieldInfo> PKFieldList) {
        MethodSpec.Builder method = MethodSpec.methodBuilder("queryById") //
                .addModifiers(DEFAULT, PUBLIC) //
                .returns(info.beanClass) //
                .addJavadoc("根据ID查询实体信息 \n");
        for (Util.FieldInfo fieldInfo : PKFieldList) {
            String name = toJavaName(fieldInfo.getFieldName(), false);
            method.addParameter(fieldInfo.getTypeClass(), name);
            method.addJavadoc("@param $N $N \n", name, fieldInfo.getRemarks());
        }
        method.addJavadoc("@return 实体信息 \n");
        method.addCode("return queryOne(new $T() {{ \n", info.sqlClass);
        for (Util.FieldInfo fieldInfo : PKFieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), true);
            method.addCode("\t// $L \n", fieldInfo.getRemarks());
            method.addStatement("\twhere($S, $T.$L)", "%s = ?", info.beanClass, db_name);
            method.addStatement("\tparams($N)", firstLowerCase(name));
        }
        method.addStatement("}}, $T::mapper)", info.beanClass);
        return method;

    }

    // 生成 queryAll 方法
    private static MethodSpec.Builder queryAll(ClassInfo info) {
        return MethodSpec.methodBuilder("queryAll").addModifiers(DEFAULT, PUBLIC)
                .returns(ParameterizedTypeName.get(ClassName.get(List.class), info.beanClass)) //
                .addJavadoc("查询所有实体信息 \n") //
                .addJavadoc("@return 实体信息列表 \n") //
                .addCode("return query(new $T() {{ \n", info.sqlClass) //
                .addCode("// \t   \n") //
                .addStatement("}}, $T::mapper)", info.beanClass);
    }

    // 生成 queryAll(Paging) 方法
    private static MethodSpec.Builder queryAllPaging(ClassInfo info) {
        return MethodSpec.methodBuilder("queryAll").addModifiers(DEFAULT, PUBLIC)
                .returns(ParameterizedTypeName.get(ClassName.get(List.class), info.beanClass)) //
                .addParameter(Paging.class, "paging") //
                .addJavadoc("查询所有实体信息 \n") //
                .addJavadoc("@param $N 分页工具\n", "paging") //
                .addJavadoc("@return 实体信息列表 \n") //
                .addCode("return query(paging, new $T() {{ \n", info.sqlClass) //
                .addCode("// \n") //
                .addStatement("}}, $T::mapper)", info.beanClass);
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
        if (isCover || !Util.exists(configure, info.daoBasePackage, info.daoBaseName)) {
            run(configure, info, bean.tableName, bean.prefix, fieldList, PKFieldList, FKFileList);
        }
    }
}
