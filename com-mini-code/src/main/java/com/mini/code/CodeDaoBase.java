package com.mini.code;

import com.mini.util.dao.IBase;
import com.mini.util.dao.IDao;
import com.mini.util.dao.sql.SQLDelete;
import com.mini.util.dao.sql.SQLInsert;
import com.mini.util.dao.sql.SQLSelect;
import com.mini.util.dao.sql.SQLUpdate;
import com.mini.util.lang.StringUtil;
import com.squareup.javapoet.*;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import static javax.lang.model.element.Modifier.*;

public final class CodeDaoBase {
    public static final String CLASS_NAME = Config.JAVA_NAME + "DaoBase";

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println("========= Start Code Dao Base ======");
        System.out.println("====================================");
        try (IDao dao = Config.getDao()) {
            ClassName entityClassName = ClassName.get(Config.PACKAGE_NAME, CodeEntity.CLASS_NAME);
            ClassName mapperClassName = ClassName.get(Config.PACKAGE_NAME, CodeMapper.CLASS_NAME);
            // 生成类信息
            TypeSpec.Builder builder = TypeSpec.interfaceBuilder(CLASS_NAME)
                    .addModifiers(PUBLIC)
                    .addSuperinterface(ParameterizedTypeName.get(
                            ClassName.get(IBase.class), entityClassName))
                    .addJavadoc("$L.java \n", CLASS_NAME)
                    .addJavadoc("@author xchao \n");

            MethodSpec.Builder method;
            // 获取所有字段信息
            List<Util.FieldInfo> fieldList = Util.getColumns(dao);
            List<Util.FieldInfo> PKFieldList = Util.getPrimaryKey(dao);

            // 生成获取 getXXXMapper的方法
            method = MethodSpec.methodBuilder("get" + CodeMapper.CLASS_NAME)
                    .addModifiers(PUBLIC, ABSTRACT)
                    .returns(mapperClassName)
                    .addJavadoc("获取 Mapper 对象 \n")
                    .addJavadoc("@return #Mapper \n");
            MethodSpec mapperMethod = method.build();
            builder.addMethod(mapperMethod);

            // 生成 insert 方法
            method = MethodSpec.methodBuilder("insert")
                    .addModifiers(DEFAULT, PUBLIC)
                    .returns(void.class)
                    .addParameter(entityClassName, StringUtil.firstLowerCase(CodeEntity.CLASS_NAME))
                    .addException(SQLException.class)
                    .addJavadoc("添加实体信息 \n")
                    .addJavadoc("@param $L 实体信息 \n", StringUtil.firstLowerCase(CodeEntity.CLASS_NAME))
                    .addStatement("$T insert = new $T($T.TABLE)", SQLInsert.class, SQLInsert.class, entityClassName);
            for (Util.FieldInfo fieldInfo : fieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = StringUtil.toJavaName(fieldInfo.getFieldName(), true);
                method.addCode("// $L \n", fieldInfo.getRemarks());
                method.addStatement("insert.put($T.$L).params($L.get$L())", entityClassName,
                        db_name, StringUtil.firstLowerCase(CodeEntity.CLASS_NAME), name);
            }
            method.addStatement("execute(insert)");
            builder.addMethod(method.build());


            // 生成 update 方法
            method = MethodSpec.methodBuilder("update")
                    .addModifiers(DEFAULT, PUBLIC)
                    .returns(void.class)
                    .addParameter(entityClassName, StringUtil.firstLowerCase(CodeEntity.CLASS_NAME))
                    .addException(SQLException.class)
                    .addJavadoc("修改实体信息 \n")
                    .addJavadoc("@param $L 实体信息 \n", StringUtil.firstLowerCase(CodeEntity.CLASS_NAME))
                    .addStatement("$T update = new $T().from($T.TABLE)", SQLUpdate.class, SQLUpdate.class, entityClassName);
            for (Util.FieldInfo fieldInfo : fieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = StringUtil.toJavaName(fieldInfo.getFieldName(), true);
                method.addCode("// $L \n", fieldInfo.getRemarks());
                method.addStatement("update.put($T.$L).params($L.get$L())", entityClassName,
                        db_name, StringUtil.firstLowerCase(CodeEntity.CLASS_NAME), name);
            }
            method.addCode("\n");
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = StringUtil.toJavaName(fieldInfo.getFieldName(), true);
                method.addStatement("update.where($T.$L).params($L.get$L())", entityClassName,
                        db_name, StringUtil.firstLowerCase(CodeEntity.CLASS_NAME), name);
            }
            method.addStatement("execute(update)");
            builder.addMethod(method.build());


            // 生成 delete 方法
            method = MethodSpec.methodBuilder("delete")
                    .addModifiers(DEFAULT, PUBLIC)
                    .returns(void.class)
                    .addParameter(entityClassName, StringUtil.firstLowerCase(CodeEntity.CLASS_NAME))
                    .addException(SQLException.class)
                    .addJavadoc("删除实体信息 \n")
                    .addJavadoc("@param $L 实体信息 \n", StringUtil.firstLowerCase(CodeEntity.CLASS_NAME))
                    .addStatement("$T delete = new $T().from($T.TABLE)", SQLDelete.class, SQLDelete.class, entityClassName);
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = StringUtil.toJavaName(fieldInfo.getFieldName(), true);
                method.addStatement("delete.where($T.$L).params($L.get$L())", entityClassName,
                        db_name, StringUtil.firstLowerCase(CodeEntity.CLASS_NAME), name);
            }
            method.addStatement("execute(delete)");
            builder.addMethod(method.build());

            // 生成 deleteById 方法
            method = MethodSpec.methodBuilder("deleteById")
                    .addModifiers(DEFAULT, PUBLIC)
                    .returns(void.class)
                    .addException(SQLException.class)
                    .addJavadoc("根据ID删除数据 \n");
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String name = StringUtil.toJavaName(fieldInfo.getFieldName(), false);
                method.addJavadoc("@param $L $L \n", name, StringUtil.def(fieldInfo.getRemarks(), name));
                method.addParameter(fieldInfo.getTypeClass(), name);
            }
            method.addStatement("$T delete = new $T().from($T.TABLE)", SQLDelete.class, SQLDelete.class, entityClassName);
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = StringUtil.toJavaName(fieldInfo.getFieldName(), false);
                method.addStatement("delete.where($T.$L).params($L)", entityClassName, db_name, name);
            }
            method.addStatement("execute(delete)");
            builder.addMethod(method.build());


            // 生成 queryById 方法
            method = MethodSpec.methodBuilder("queryById")
                    .addModifiers(DEFAULT, PUBLIC)
                    .returns(entityClassName)
                    .addException(SQLException.class)
                    .addJavadoc("根据ID 查询实体信息 \n");
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String name = StringUtil.toJavaName(fieldInfo.getFieldName(), false);
                method.addJavadoc("@param $L $L \n", name, StringUtil.def(fieldInfo.getRemarks(), name));
                method.addParameter(fieldInfo.getTypeClass(), name);
            }
            method.addJavadoc("@return 实体信息 \n");
            method.addStatement("$T select = $T.sql()", SQLSelect.class, mapperClassName);
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = StringUtil.toJavaName(fieldInfo.getFieldName(), false);
                method.addStatement("select.where($T.$L).params($L)", entityClassName, db_name, name);
            }
            method.addStatement("return queryOne(select, $N())", mapperMethod);
            builder.addMethod(method.build());

            // 生成文件信息
            JavaFile javaFile = JavaFile.builder(Config.PACKAGE_NAME, builder.build()).build();
            javaFile.writeTo(new File(Config.CLASS_PATH));
        }

        System.out.println("====================================");
        System.out.println("========= End Code  Dao Base =======");
        System.out.println("====================================");
        System.out.println("\r\n");
    }
}
