package com.mini.code;

import com.mini.dao.IDao;
import com.mini.dao.IMapper;
import com.mini.dao.sql.SQLSelect;
import com.mini.util.StringUtil;
import com.squareup.javapoet.*;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

public final class CodeMapper {
    public static final String CLASS_NAME = Config.JAVA_NAME + "Mapper";

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println("========= Start Code Mapper ========");
        System.out.println("====================================");
        try (IDao dao = Config.getDao()) {
            ClassName entityClassName = ClassName.get(Config.PACKAGE_NAME, CodeBean.CLASS_NAME);
            ClassName mapperClassName = ClassName.get(Config.PACKAGE_NAME, CLASS_NAME);
            String infoName = StringUtil.firstLowerCase(CodeBean.CLASS_NAME);

            // 生成类信息
            TypeSpec.Builder builder = TypeSpec.classBuilder(CLASS_NAME)
                    .addModifiers(PUBLIC)
                    .addSuperinterface(ParameterizedTypeName.get(
                            ClassName.get(IMapper.class), entityClassName))
                    .addJavadoc("$L.java \n", CLASS_NAME)
                    .addJavadoc("@author xchao \n");

            // 实现的方法
            MethodSpec.Builder method = MethodSpec.methodBuilder("execute")
                    .addAnnotation(Override.class)
                    .addModifiers(PUBLIC)
                    .returns(entityClassName)
                    .addParameter(ResultSet.class, "rs")
                    .addParameter(int.class, "number")
                    .addException(SQLException.class)
                    .addStatement("$T $L = new $T()", entityClassName, infoName, entityClassName);

            // 获取所有字段信息
            List<Util.FieldInfo> fieldList = Util.getColumns(dao);
            for (Util.FieldInfo fieldInfo : fieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = StringUtil.toJavaName(fieldInfo.getFieldName(), false);
                method.addComment(fieldInfo.getRemarks());
                method.addStatement("$L.set$L(rs.get$L($T.$L))", infoName,
                        StringUtil.firstUpperCase(name),
                        StringUtil.firstUpperCase(fieldInfo.getTypeClass().getSimpleName()),
                        entityClassName, db_name);
            }
            method.addStatement("return $L", infoName);
            builder.addMethod(method.build());

            // 获取基础SQL的方法
            MethodSpec.Builder sqlMethod = MethodSpec.methodBuilder("sql")
                    .addModifiers(PUBLIC, STATIC)
                    .returns(SQLSelect.class)
                    .addCode("return new $T()", SQLSelect.class)
                    .addCode("\n// $L", "表名称")
                    .addCode("\n.from($T.TABLE)", entityClassName);
            for (Util.FieldInfo fieldInfo : fieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                sqlMethod.addCode("\n// $L", fieldInfo.getRemarks());
                sqlMethod.addCode("\n.keys($T.$L)", entityClassName, db_name);
            }
            sqlMethod.addCode(";");
            builder.addMethod(sqlMethod.build());

            // 生成文件信息
            JavaFile javaFile = JavaFile.builder(Config.PACKAGE_NAME, builder.build()).build();
            javaFile.writeTo(new File(Config.CLASS_PATH));
        }

        System.out.println("====================================");
        System.out.println("========= End Code Mapper ==========");
        System.out.println("====================================");
        System.out.println("\r\n");
    }

}
