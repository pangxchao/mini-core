package com.mini.code.impl.web;

import com.mini.code.Configure;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.MethodSpecBuilder;
import com.mini.code.util.Util;
import com.mini.jdbc.util.Paging;
import com.mini.util.StringUtil;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.MapModel;
import com.mini.web.model.factory.ModelType;
import com.squareup.javapoet.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static com.mini.code.util.Util.getColumns;
import static com.mini.code.util.Util.getPrimaryKey;
import static com.mini.util.StringUtil.firstLowerCase;
import static com.mini.util.StringUtil.toJavaName;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

public class CodeControllerMobile {
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
        TypeSpec.Builder builder = TypeSpec.classBuilder(info.mobileControllerName)
                .addModifiers(PUBLIC)
                .addAnnotation(Singleton.class)
                .addAnnotation(AnnotationSpec.builder(Controller.class)
                        .addMember("path", "$S", "mobile/" + firstLowerCase(info.beanName))
                        .addMember("url", "$S", "mobile/" + firstLowerCase(info.beanName))
                        .build())
                .addJavadoc("$L.java \n", info.mobileControllerName)
                .addJavadoc("@author xchao \n");

        // Service 依赖注入属性定义
        builder.addField(FieldSpec.builder(info.serviceClass, firstLowerCase(info.serviceName), PRIVATE)
                .addAnnotation(Inject.class)
                .build());

        // 实体列表查询方法
        builder.addMethod(MethodSpec.methodBuilder("list")
                .addModifiers(PUBLIC)
                .returns(void.class)
                .addParameter(MapModel.class, "model")
                .addParameter(Paging.class, "paging")
                .addAnnotation(AnnotationSpec.builder(Action.class)
                        .addMember("value", "$T.MAP", ModelType.class)
                        .addMember("url", "$S", "list.htm")
                        .build())
                .addJavadoc("实体列表查询方法 \n")
                .addJavadoc("@param model 数据模型渲染器 \n")
                .addJavadoc("@param paging 数据分页工具 \n")
                .addStatement("model.addData($S, $N.queryAll(paging))", "data", firstLowerCase(info.serviceName))
                .addStatement("model.addData($S, paging)", "paging")
                .build());

        //  添加实体信息
        builder.addMethod(MethodSpec.methodBuilder("insert")
                .addModifiers(PUBLIC)
                .returns(void.class)
                .addParameter(MapModel.class, "model")
                .addParameter(info.beanClass, firstLowerCase(info.beanName))
                .addAnnotation(AnnotationSpec.builder(Action.class)
                        .addMember("value", "$T.MAP", ModelType.class)
                        .addMember("url", "$S", "insert.htm")
                        .build())
                .addJavadoc("添加实体处理 \n")
                .addJavadoc("@param model 数据模型渲染器 \n")
                .addJavadoc("@param $N 实体信息 \n", firstLowerCase(info.beanName))
                .addStatement("$N.insert($N)", firstLowerCase(info.serviceName), firstLowerCase(info.beanName))
                .build());

        // 修改实体信息
        builder.addMethod(MethodSpec.methodBuilder("update")
                .addModifiers(PUBLIC)
                .returns(void.class)
                .addParameter(MapModel.class, "model")
                .addParameter(info.beanClass, firstLowerCase(info.beanName))
                .addAnnotation(AnnotationSpec.builder(Action.class)
                        .addMember("value", "$T.MAP", ModelType.class)
                        .addMember("url", "$S", "update.htm")
                        .build())
                .addJavadoc("修改实体处理 \n")
                .addJavadoc("@param model 数据模型渲染器 \n")
                .addJavadoc("@param $N 实体信息 \n", firstLowerCase(info.beanName))
                .addStatement("$N.update($N)", firstLowerCase(info.serviceName), firstLowerCase(info.beanName))
                .build());

        //  删除实体信息
        builder.addMethod(new MethodSpecBuilder("delete")
                .addModifiers(PUBLIC)
                .returns(void.class)
                .addParameter(MapModel.class, "model")
                .addAnnotation(AnnotationSpec.builder(Action.class)
                        .addMember("value", "$T.MAP", ModelType.class)
                        .addMember("url", "$S", "delete.htm")
                        .build())
                .addJavadoc("删除实体信息处理 \n")
                .addJavadoc("@param model 数据模型渲染器 \n")
                .addParameter(pkFieldList, true)
                .addStatement("$N.deleteById($N)", firstLowerCase(info.serviceName), //
                        StringUtil.join(",", pkFieldList.stream().map(fieldInfo -> //
                                toJavaName(fieldInfo.getFieldName(), false)) //
                                .collect(Collectors.toList())))
                .build());

        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(info.mobileControllerPackage, builder.build()).build();
        javaFile.writeTo(new File(configure.getClassPath()));

        System.out.println("====================================");
        System.out.println("Mobile Controller : " + info.beanName + "\r\n");
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
        if (isCover || !Util.exists(configure, info.mobileControllerPackage, info.mobileControllerName)) {
            run(configure, info, bean.tableName, bean.prefix, fieldList, pkFieldList);
        }
    }
}
