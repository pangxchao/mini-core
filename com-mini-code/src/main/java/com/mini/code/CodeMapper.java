package com.mini.code;

import com.mini.jdbc.mapper.IMapper;
import com.mini.jdbc.sql.SQLSelect;
import com.mini.util.StringUtil;
import com.squareup.javapoet.*;

import javax.inject.Singleton;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.mini.util.StringUtil.firstLowerCase;
import static com.mini.util.StringUtil.firstUpperCase;
import static java.lang.String.format;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

public final class CodeMapper {
    protected static void run(Configure configure, String className, String tableName, String prefix) throws Exception {
        System.out.println("====================================");
        System.out.println("========= Start Code Mapper ========");
        System.out.println("====================================");

        // Bean package Name
        String beanPackage = format("%s.entity", configure.getPackageName());
        String mapperPackage = format("%s.entity.mapper", configure.getPackageName());

        // Class Name
        String mapperClassName = String.format("%sMapper", className);

        // Class
        ClassName beanClass = ClassName.get(beanPackage, className);

        try (Connection connection = configure.getConnection()) {
            // 获取所有字段信息
            List<Util.FieldInfo> fieldList = Util.getColumns(connection, configure.getDatabaseName(), tableName, prefix);

            // 生成类信息
            TypeSpec.Builder builder = TypeSpec.classBuilder(mapperClassName)
                    .addModifiers(PUBLIC)
                    .addSuperinterface(ParameterizedTypeName.get(
                            ClassName.get(IMapper.class), beanClass))
                    .addAnnotation(Singleton.class)
                    .addJavadoc("$L.java \n", mapperClassName)
                    .addJavadoc("@author xchao \n");

            // 实现的方法
            MethodSpec.Builder method = MethodSpec.methodBuilder("get")
                    .addAnnotation(Override.class)
                    .addModifiers(PUBLIC)
                    .returns(beanClass)
                    .addParameter(ResultSet.class, "rs")
                    .addParameter(int.class, "number")
                    .addException(SQLException.class)
                    .addStatement("$T $L = new $T()", beanClass, firstLowerCase(className), beanClass);

            // 添加方法内容
            for (Util.FieldInfo fieldInfo : fieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = StringUtil.toJavaName(fieldInfo.getFieldName(), false);
                method.addComment(fieldInfo.getRemarks());
                method.addStatement("$L.set$L(rs.get$L($T.$L))", firstLowerCase(className), firstUpperCase(name),
                        StringUtil.firstUpperCase(fieldInfo.getTypeClass().getSimpleName()), beanClass, db_name);
            }
            method.addStatement("return $L", firstLowerCase(className));
            builder.addMethod(method.build());

            // 获取基础SQL的方法
            MethodSpec.Builder sqlMethod = MethodSpec.methodBuilder("sql")
                    .addModifiers(PUBLIC, STATIC)
                    .returns(SQLSelect.class)
                    .addCode("return new $T()", SQLSelect.class);
            for (Util.FieldInfo fieldInfo : fieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                sqlMethod.addCode("\n// $L", fieldInfo.getRemarks());
                sqlMethod.addCode("\n.keys($T.$L)", beanClass, db_name);
            }
            sqlMethod.addCode("\n// $L", "表名称");
            sqlMethod.addCode("\n.from($T.TABLE)", beanClass);
            sqlMethod.addCode(";");
            builder.addMethod(sqlMethod.build());

            // 生成文件信息
            JavaFile javaFile = JavaFile.builder(mapperPackage, builder.build()).build();
            javaFile.writeTo(new File(configure.getClassPath()));
        }

        System.out.println("====================================");
        System.out.println("========= End Code Mapper ==========");
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
