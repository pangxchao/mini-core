package com.mini.code;

import com.mini.util.StringUtil;
import com.squareup.javapoet.*;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

import static java.lang.String.format;
import static javax.lang.model.element.Modifier.*;

public final class CodeBean {
    protected static void run(Configure configure, String className, String tableName, String prefix) throws Exception {
        System.out.println("====================================");
        System.out.println("========= Start Code Bean ==========");
        System.out.println("====================================");

        //  Package Name
        String basePackage = format("%s.entity.base", configure.getPackageName());
        String beanPackage = format("%s.entity", configure.getPackageName());

        // Class Name
        String baseClassName = StringUtil.format("%sBase", className);

        // Class
        ClassName baseClass = ClassName.get(basePackage, baseClassName);
        ClassName beanClass = ClassName.get(beanPackage, className);

        try (Connection connection = configure.getConnection()) {
            // 获取所有字段信息
            List<Util.FieldInfo> fieldList = Util.getColumns(connection, configure.getDatabaseName(), tableName, prefix);

            // 生成类信息
            TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                    .addModifiers(PUBLIC)
                    .addSuperinterface(ParameterizedTypeName.get(baseClass, beanClass))
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
                        .returns(beanClass)
                        // 添加方法参数列表
                        .addParameter(fieldInfo.getTypeClass(), name)
                        .addAnnotation(Override.class)
                        // 添加方法体内容
                        .addStatement("this.$L = $L", name, name)
                        .addStatement("return this")
                        .build());
            }
            // 生成文件信息
            JavaFile javaFile = JavaFile.builder(beanPackage, builder.build()).build();
            javaFile.writeTo(new File(configure.getClassPath()));
        }
        System.out.println("====================================");
        System.out.println("========= End Code Bean ============");
        System.out.println("====================================");
        System.out.println("\r\n");
    }

    public static void main(String[] args) throws Exception {
        Configure configure = Config.getConfigure();
        for (String[] bean : configure.getDatabaseBeans()) {
            run(configure, bean[0], bean[1], bean[2]);
        }
    }
}
