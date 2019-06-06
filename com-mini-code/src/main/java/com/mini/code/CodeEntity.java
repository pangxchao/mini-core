package com.mini.code;

import com.mini.util.dao.IDao;
import com.mini.util.lang.StringUtil;
import com.squareup.javapoet.*;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import static javax.lang.model.element.Modifier.*;

public final class CodeEntity {
    public static final String CLASS_NAME = Config.JAVA_NAME;

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println("========= Start Code Entity ========");
        System.out.println("====================================");
        try (IDao dao = Config.getDao()) {
            ClassName entityClassName = ClassName.get(Config.PACKAGE_NAME, CLASS_NAME);
            // 生成类信息
            TypeSpec.Builder builder = TypeSpec.classBuilder(CLASS_NAME)
                    .addModifiers(PUBLIC)
                    .addSuperinterface(Serializable.class)
                    // 生成  Serializable 的常量字段
                    .addField(FieldSpec.builder(long.class, "serialVersionUID")
                            .addModifiers(PRIVATE, STATIC, FINAL)
                            .initializer("$L", "-1L")
                            .build())
                    // 生成表名常量
                    .addField(FieldSpec.builder(String.class, "TABLE")
                            .addModifiers(PUBLIC, STATIC, FINAL)
                            .addJavadoc(" 表名称 $L \n", Config.DB_NAME)
                            .initializer("$S", Config.DB_NAME)
                            .build())
                    .addJavadoc("$L.java \n", CLASS_NAME)
                    .addJavadoc("@author xchao \n");


            // 获取所有字段信息
            List<Util.FieldInfo> fieldList = Util.getColumns(dao);

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

            // 处理Getter Setter 方法
            for (Util.FieldInfo fieldInfo : fieldList) {
                String name = StringUtil.toJavaName(fieldInfo.getFieldName(), false);
                builder.addMethod(MethodSpec.methodBuilder("get" + StringUtil.firstUpperCase(name))
                        .addModifiers(PUBLIC)
                        // 设置返回类型
                        .returns(fieldInfo.getTypeClass())
                        // 方法体内容
                        .addStatement("return $L", name)
                        // 生成方法 JAVA DOC
                        .addJavadoc("$L. \n", StringUtil.def(fieldInfo.getRemarks(), "Gets the value of " + name))
                        .addJavadoc("@return The value of $L \n", name)
                        .build());
                builder.addMethod(MethodSpec.methodBuilder("set" + StringUtil.firstUpperCase(name))
                        .addModifiers(PUBLIC)
                        // 设置返回类型
                        .returns(entityClassName)
                        // 添加方法参数列表
                        .addParameter(fieldInfo.getTypeClass(), name)
                        // 添加方法体内容
                        .addStatement("this.$L = $L", name, name)
                        .addStatement("return this")
                        // 生成方法 JAVA DOC
                        .addJavadoc("$L. \n", StringUtil.def(fieldInfo.getRemarks(), "Sets the value of " + name))
                        .addJavadoc("@param $L The value of $L \n", name, name)
                        .addJavadoc("@return {@code this} \n")
                        .build());
            }
            // 生成文件信息
            JavaFile javaFile = JavaFile.builder(Config.PACKAGE_NAME, builder.build()).build();
            javaFile.writeTo(new File(Config.CLASS_PATH));
        }

        System.out.println("====================================");
        System.out.println("========= End Code Entity ==========");
        System.out.println("====================================");
        System.out.println("\r\n");
    }


}
