package com.mini.code.impl.web;

import com.mini.code.Configure;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.Util;
import com.mini.jdbc.util.Paging;
import com.mini.util.DateUtil;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.MapModel;
import com.mini.web.model.PageModel;
import com.mini.web.model.factory.ModelType;
import com.mini.web.util.IStatus;
import com.squareup.javapoet.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.util.*;

import static com.mini.code.util.Util.*;
import static com.mini.util.StringUtil.*;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

@SuppressWarnings("DuplicatedCode")
public class CodeController {
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
    protected static void run(Configure configure, ClassInfo info, String tableName, String prefix, List<FieldInfo> fieldList,
            List<FieldInfo> PKFieldList, List<FieldInfo> FKFieldList) throws Exception {
        // 生成类信息
        TypeSpec.Builder builder = TypeSpec.classBuilder(info.controllerName)
                .addModifiers(PUBLIC)
                .addSuperinterface(IStatus.class)
                .addAnnotation(Singleton.class)
                .addAnnotation(AnnotationSpec.builder(Controller.class)
                        .addMember("path", "$S", "back/" + firstLowerCase(info.beanName))
                        .addMember("url", "$S", "back/" + firstLowerCase(info.beanName))
                        .build())
                .addJavadoc("$L.java \n", info.controllerName)
                .addJavadoc("@author xchao \n");

        // Service 依赖注入属性定义
        builder.addField(FieldSpec.builder(info.daoClass, firstLowerCase(info.daoName), PRIVATE)
                .addAnnotation(Inject.class)
                .build());

        // 实体列表首页方法
        builder.addMethod(MethodSpec.methodBuilder("index")
                .addModifiers(PUBLIC).returns(void.class)
                .addParameter(PageModel.class, "model")
                .addAnnotation(AnnotationSpec.builder(Action.class)
                        .addMember("url", "$S", "index.htm")
                        .build())
                .addJavadoc("实体列表首页 \n")
                .addJavadoc("@param model 数据模型渲染器 \n")
                .build());

        // 实体列表数据分页方法
        MethodSpec.Builder method = MethodSpec.methodBuilder("pages")
                .addModifiers(PUBLIC).returns(void.class)
                .addParameter(MapModel.class, "model")
                .addParameter(Paging.class, "paging")
                .addAnnotation(AnnotationSpec.builder(Action.class)
                        .addMember("value", "$T.MAP", ModelType.class)
                        .addMember("url", "$S", "pages.htm")
                        .build())
                .addJavadoc("实体列表数据分页 \n")
                .addJavadoc("@param model 数据模型渲染器 \n")
                .addJavadoc("@param paging 数据分页工具 \n");
        method.addStatement("$T<$T> list = $N.queryAll(paging)", List.class, info.beanClass, firstLowerCase(info.daoName));
        method.addCode("model.addData($S, list.stream().map($N-> { \n", "data", firstLowerCase(info.beanName));
        method.addStatement("\t$T<$T, String> map = new $T<>()", Map.class, String.class, HashMap.class);
        for (FieldInfo fieldInfo : fieldList) {
            String name = toJavaName(fieldInfo.getFieldName(), false);
            if (Date.class.isAssignableFrom(fieldInfo.getTypeClass())) {
                method.addStatement("\tmap.put($S, $T.formatDateTime($N.get$L()))", name, DateUtil.class, //
                        firstLowerCase(info.beanName), firstUpperCase(name));
            } else if (String.class.isAssignableFrom(fieldInfo.getTypeClass())) {
                method.addStatement("\tmap.put($S, $N.get$L())", name, firstLowerCase(info.beanName), //
                        firstUpperCase(name));
            } else {
                method.addStatement("\tmap.put($S, String.valueOf($N.get$L()))", name, //
                        firstLowerCase(info.beanName), firstUpperCase(name));
            }
        }
        method.addStatement("\treturn map");
        method.addStatement("}).toArray())");
        method.addStatement("model.addData($S, $N.getTotal())", "count", "paging")
                .addStatement("model.addData($S, $S)", "msg", "获取数据成功")
                .addStatement("model.addData($S, 0)", "code");
        builder.addMethod(method.build());

        //  添加实体信息处理
        builder.addMethod(insert(info, fieldList).build());

        //  修改实体信息处理
        builder.addMethod(update(info, fieldList).build());

        //  删除实体信息
        builder.addMethod(delete(info, PKFieldList).build());

        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(info.controllerPackage, builder.build()).build();
        javaFile.writeTo(new File(configure.getClassPath()));

        System.out.println("====================================");
        System.out.println("Back Controller : " + info.beanName + "\r\n");
    }

    //  添加实体信息处理
    private static MethodSpec.Builder insert(ClassInfo info, List<Util.FieldInfo> fieldList) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("insert")
                .addModifiers(PUBLIC)
                .returns(void.class)
                .addAnnotation(AnnotationSpec.builder(Action.class)
                        .addMember("value", "$T.MAP", ModelType.class)
                        .addMember("url", "$S", "insert.htm")
                        .build())
                .addParameter(MapModel.class, "model")
                .addJavadoc("添加实体处理 \n")
                .addJavadoc("@param model 数据模型渲染器 \n");
        for (Util.FieldInfo fieldInfo : fieldList) {
            String name = toJavaName(fieldInfo.getFieldName(), false);
            builder.addParameter(fieldInfo.getTypeClass(), name);
            builder.addJavadoc("@param $N $N \n", name, fieldInfo.getRemarks());
        }
        builder.addStatement("$T builder = $T.newBuilder()", info.builderClass, info.beanClass);
        for (Util.FieldInfo fieldInfo : fieldList) {
            String name = toJavaName(fieldInfo.getFieldName(), false);
            builder.addStatement("builder.set$L($L)", firstUpperCase(name), name);
        }
        builder.addStatement("$N.insert(builder.build())", firstLowerCase(info.daoName));
        return builder;
    }

    //  修改实体信息处理
    private static MethodSpec.Builder update(ClassInfo info, List<Util.FieldInfo> fieldList) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("update")
                .addModifiers(PUBLIC)
                .returns(void.class)
                .addAnnotation(AnnotationSpec.builder(Action.class)
                        .addMember("value", "$T.MAP", ModelType.class)
                        .addMember("url", "$S", "update.htm")
                        .build())
                .addParameter(MapModel.class, "model")
                .addJavadoc("修改实体处理 \n")
                .addJavadoc("@param model 数据模型渲染器 \n");
        for (Util.FieldInfo fieldInfo : fieldList) {
            String name = toJavaName(fieldInfo.getFieldName(), false);
            builder.addParameter(fieldInfo.getTypeClass(), name);
            builder.addJavadoc("@param $N $N \n", name, fieldInfo.getRemarks());
        }
        builder.addStatement("$T builder = $T.newBuilder()", info.builderClass, info.beanClass);
        for (Util.FieldInfo fieldInfo : fieldList) {
            String name = toJavaName(fieldInfo.getFieldName(), false);
            builder.addStatement("builder.set$L($L)", firstUpperCase(name), name);
        }
        builder.addStatement("$N.update(builder.build())", firstLowerCase(info.daoName));
        return builder;
    }

    //  删除实体信息
    private static MethodSpec.Builder delete(ClassInfo info, List<FieldInfo> PKFieldList) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("delete")
                .addModifiers(PUBLIC)
                .returns(void.class)
                .addParameter(MapModel.class, "model")
                .addAnnotation(AnnotationSpec.builder(Action.class)
                        .addMember("value", "$T.MAP", ModelType.class)
                        .addMember("url", "$S", "delete.htm")
                        .build())
                .addJavadoc("删除实体信息处理 \n")
                .addJavadoc("@param model 数据模型渲染器 \n");
        List<String> PKParams = new ArrayList<>();
        for (FieldInfo fieldInfo : PKFieldList) {
            String name = toJavaName(fieldInfo.getFieldName(), false);
            builder.addParameter(fieldInfo.getTypeClass(), name);
            builder.addJavadoc("@param $N $N \n", name, fieldInfo.getRemarks());
            PKParams.add(name);
        }
        builder.addStatement("$N.deleteById($N)", firstLowerCase(info.daoName), join(", ", PKParams));
        return builder;
    }

    /**
     * 生成java代码
     * @param configure 数据库与实体配置信息
     * @param bean      数据库表与实体关联配置
     * @param isCover   是否覆盖已存在的文件
     */
    public static void generator(Configure configure, Configure.BeanItem bean, boolean isCover) throws Exception {
        List<FieldInfo> FKFileList = getImportedKeys(configure.getJdbcTemplate(), //
                configure.getDatabaseName(), bean.tableName, bean.prefix);
        List<FieldInfo> PKFieldList = getPrimaryKey(configure.getJdbcTemplate(), //
                configure.getDatabaseName(), bean.tableName, bean.prefix);  //
        List<FieldInfo> fieldList = getColumns(configure.getJdbcTemplate(), //
                configure.getDatabaseName(), bean.tableName, bean.prefix); //
        ClassInfo info = new ClassInfo(configure, bean.className);

        // 不存在或者覆盖时生成
        if (isCover || !Util.exists(configure, info.controllerPackage, info.controllerName)) {
            run(configure, info, bean.tableName, bean.prefix, fieldList, PKFieldList, FKFileList);
        }
    }
}
