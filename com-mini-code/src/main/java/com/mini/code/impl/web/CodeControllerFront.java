package com.mini.code.impl.web;

import com.mini.code.Configure;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.Util;
import com.mini.jdbc.util.Paging;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.PageModel;
import com.squareup.javapoet.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.util.List;

import static com.mini.code.util.Util.getColumns;
import static com.mini.code.util.Util.getPrimaryKey;
import static com.mini.util.StringUtil.firstLowerCase;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

public class CodeControllerFront {
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
        TypeSpec.Builder builder = TypeSpec.classBuilder(info.frontControllerName)
                .addModifiers(PUBLIC)
                .addAnnotation(Singleton.class)
                .addAnnotation(AnnotationSpec.builder(Controller.class)
                        .addMember("path", "$S", "front/" + firstLowerCase(info.beanName))
                        .addMember("url", "$S", "front/" + firstLowerCase(info.beanName))
                        .build())
                .addJavadoc("$L.java \n", info.frontControllerName)
                .addJavadoc("@author xchao \n");

        // Service 依赖注入属性定义
        builder.addField(FieldSpec.builder(info.serviceClass, firstLowerCase(info.serviceName), PRIVATE)
                .addAnnotation(Inject.class)
                .build());

        // 实体列表首页方法
        builder.addMethod(MethodSpec.methodBuilder("index")
                .addModifiers(PUBLIC)
                .returns(void.class)
                .addParameter(PageModel.class, "model")
                .addAnnotation(AnnotationSpec.builder(Action.class)
                        .addMember("url", "$S", "index.htm")
                        .build())
                .addJavadoc("实体列表首页 \n")
                .addJavadoc("@param model 数据模型渲染器 \n")
                .build());

        // 实体列表数据分页方法
        builder.addMethod(MethodSpec.methodBuilder("pages")
                .addModifiers(PUBLIC)
                .returns(void.class)
                .addParameter(PageModel.class, "model")
                .addParameter(Paging.class, "paging")
                .addAnnotation(AnnotationSpec.builder(Action.class)
                        .addMember("url", "$S", "pages.htm")
                        .build())
                .addJavadoc("实体列表数据分页 \n")
                .addJavadoc("@param model 数据模型渲染器 \n")
                .addJavadoc("@param paging 数据分页工具 \n")
                .addStatement("model.addData($S, $N.queryAll(paging))", "data", firstLowerCase(info.serviceName))
                .addStatement("model.addData($S, $N)", "paging", "paging")
                .build());

        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(info.frontControllerPackage, builder.build()).build();
        javaFile.writeTo(new File(configure.getClassPath()));

        System.out.println("====================================");
        System.out.println("Front Controller : " + info.beanName + "\r\n");
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
        if (isCover || !Util.exists(configure, info.frontControllerPackage, info.frontControllerName)) {
            run(configure, info, bean.tableName, bean.prefix, fieldList, pkFieldList);
        }
    }
}
