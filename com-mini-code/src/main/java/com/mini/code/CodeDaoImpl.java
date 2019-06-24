package com.mini.code;

import com.mini.dao.IDao;
import com.mini.dao.IDaoTemplate;
import com.mini.dao.sql.SQLDelete;
import com.mini.dao.sql.SQLInsert;
import com.mini.dao.sql.SQLSelect;
import com.mini.dao.sql.SQLUpdate;
import com.mini.inject.annotation.Implemented;
import com.mini.util.StringUtil;
import com.squareup.javapoet.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.sql.SQLException;
import java.util.List;

import static com.mini.util.StringUtil.*;
import static javax.lang.model.element.Modifier.*;

public final class CodeDaoImpl {
    public static final String CLASS_NAME = Config.JAVA_NAME + "DaoImpl";

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println("========= Start Code Dao Impl ======");
        System.out.println("====================================");

        ClassName mapperClassName = ClassName.get(Config.PACKAGE_NAME, CodeMapper.CLASS_NAME);
        ClassName beanClassName = ClassName.get(Config.PACKAGE_NAME, CodeBean.CLASS_NAME);
        ClassName daoClassName = ClassName.get(Config.PACKAGE_NAME, CodeDao.CLASS_NAME);
        try (IDao dao = Config.getDao()) {
            // /**
            //  * ${CLASS_NAME}.java
            //  * @author xchao
            //  */
            // @Singleton
            // @Implemented(value="${CodeDao.CLASS_NAME}", name="${firstLowerCaseCodeDao.CLASS_NAME)}")
            // public class ${CLASS_NAME} implement ${CodeDao.CLASS_NAME}
            TypeSpec.Builder builder = TypeSpec.classBuilder(CLASS_NAME)
                    .addModifiers(PUBLIC)
                    .addSuperinterface(daoClassName)
                    .addAnnotation(Singleton.class)
                    .addAnnotation(AnnotationSpec.builder(Implemented.class)
                            .addMember("value", "$T.class", daoClassName)
                            .addMember("name", "$S", firstLowerCase(CodeDao.CLASS_NAME))
                            .build())
                    .addJavadoc("$L.java \n", CLASS_NAME)
                    .addJavadoc("@author xchao \n");

            // @Inject
            // @named("daoTemplate")
            // private IDaoTemplate daoTemplate;
            builder.addField(FieldSpec.builder(IDaoTemplate.class, "daoTemplate", PRIVATE)
                    .addAnnotation(Inject.class)
                    .addAnnotation(AnnotationSpec.builder(Named.class)
                            .addMember("value", "daoTemplate").build())
                    .build());

            // @Inject
            // @Named("daoTemplate")
            // private ${CodeMapper.CLASS_NAME} ${CodeMapper.CLASS_NAME};
            builder.addField(FieldSpec.builder(mapperClassName, firstLowerCase(CodeMapper.CLASS_NAME), PRIVATE)
                    .addAnnotation(Inject.class)
                    .addAnnotation(AnnotationSpec.builder(Named.class)
                            .addMember("value", firstLowerCase(CodeMapper.CLASS_NAME)).build())
                    .build());

            // @Override
            // public IDaoTemplate getWriteDaoTemplate() {
            //      return daoTemplate;
            // }
            builder.addMethod(MethodSpec.methodBuilder("getWriteDaoTemplate")
                    .addModifiers(PUBLIC)
                    .returns(IDaoTemplate.class)
                    .addAnnotation(Override.class)
                    .addStatement("return daoTemplate")
                    .build());

            // @Override
            // public IDaoTemplate getReadDaoTemplate() {
            //      return daoTemplate;
            // }
            builder.addMethod(MethodSpec.methodBuilder("getReadDaoTemplate")
                    .addModifiers(PUBLIC)
                    .returns(IDaoTemplate.class)
                    .addAnnotation(Override.class)
                    .addStatement("return daoTemplate")
                    .build());

            // @Override
            // public ${CodeMapper.CLASS_NAME} getMapper() {
            //      return ${firstLowerCase(CodeMapper.CLASS_NAME)};
            // }
            builder.addMethod(MethodSpec.methodBuilder("getMapper")
                    .addModifiers(PUBLIC)
                    .returns(mapperClassName)
                    .addAnnotation(Override.class)
                    .addStatement("return $L", firstLowerCase(CodeMapper.CLASS_NAME))
                    .build());

            // 定义 method 对象
            // 获取所有字段信息
            MethodSpec.Builder method;
            List<Util.FieldInfo> fieldList = Util.getColumns(dao);
            List<Util.FieldInfo> PKFieldList = Util.getPrimaryKey(dao);

            //@Override
            //public int insert(${CodeBean.CLASS_NAME} ${firstLowerCase(CodeBean.CLASS_NAME)}) throws SQLException {
            //    SQLInsert insert = new SQLInsert(${CodeBean.CLASS_NAME}.TABLE);
            //    // setting field....
            //    return execute(insert);
            //}
            method = MethodSpec.methodBuilder("insert")
                    .addModifiers(PUBLIC)
                    .returns(int.class)
                    .addParameter(beanClassName, firstLowerCase(CodeBean.CLASS_NAME))
                    .addException(SQLException.class)
                    .addAnnotation(Override.class)
                    .addStatement("$T insert = new $T($T.TABLE)", SQLInsert.class, SQLInsert.class, beanClassName);
            for (Util.FieldInfo fieldInfo : fieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = toJavaName(fieldInfo.getFieldName(), true);
                method.addCode("// $L \n", fieldInfo.getRemarks());
                method.addStatement("insert.put($T.$L).params($L.get$L())", beanClassName, db_name, firstLowerCase(CodeBean.CLASS_NAME), name);
            }
            method.addStatement("return execute(insert)");
            builder.addMethod(method.build());

            //@Override
            //public int update(${CodeBean.CLASS_NAME} ${firstLowerCase(CodeBean.CLASS_NAME)}) throws SQLException {
            //    SQLUpdate update = new SQLUpdate().from(${CodeBean.CLASS_NAME}.TABLE);
            //    // setting field....
            //    // add where field....
            //    return execute(update);
            //}
            method = MethodSpec.methodBuilder("update")
                    .addModifiers(PUBLIC)
                    .returns(int.class)
                    .addParameter(beanClassName, firstLowerCase(CodeBean.CLASS_NAME))
                    .addException(SQLException.class)
                    .addAnnotation(Override.class)
                    .addStatement("$T update = new $T().from($T.TABLE)", SQLUpdate.class, SQLUpdate.class, beanClassName);
            for (Util.FieldInfo fieldInfo : fieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = toJavaName(fieldInfo.getFieldName(), true);
                method.addCode("// $L \n", fieldInfo.getRemarks());
                method.addStatement("update.put($T.$L).params($L.get$L())", beanClassName, db_name, firstLowerCase(CodeBean.CLASS_NAME), name);
            }
            method.addCode("\n");
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = toJavaName(fieldInfo.getFieldName(), true);
                method.addStatement("update.where($T.$L).params($L.get$L())", beanClassName, db_name, firstLowerCase(CodeBean.CLASS_NAME), name);
            }
            method.addStatement("return execute(update)");
            builder.addMethod(method.build());


            //@Override
            //public int delete(${CodeBean.CLASS_NAME} ${firstLowerCase(CodeBean.CLASS_NAME)}) throws SQLException {
            //    SQLDelete delete = new SQLDelete().from(${CodeBean.CLASS_NAME}.TABLE);
            //   // add where field....
            //    return execute(delete);
            //}
            method = MethodSpec.methodBuilder("delete")
                    .addModifiers(PUBLIC)
                    .returns(int.class)
                    .addParameter(beanClassName, firstLowerCase(CodeBean.CLASS_NAME))
                    .addException(SQLException.class)
                    .addAnnotation(Override.class)
                    .addStatement("$T delete = new $T().from($T.TABLE)", SQLDelete.class, SQLDelete.class, beanClassName);
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = toJavaName(fieldInfo.getFieldName(), true);
                method.addStatement("delete.where($T.$L).params($L.get$L())", beanClassName, db_name, firstLowerCase(CodeBean.CLASS_NAME), name);
            }
            method.addStatement("return execute(delete)");
            builder.addMethod(method.build());

            //public int deleteById(PKField...) throws SQLException {
            //    SQLDelete delete = new SQLDelete().from(${CodeBean.CLASS_NAME}.TABLE);
            //    // add where field....
            //    return execute(delete);
            //}
            method = MethodSpec.methodBuilder("deleteById")
                    .addModifiers(DEFAULT, PUBLIC)
                    .returns(int.class)
                    .addException(SQLException.class);
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String name = toJavaName(fieldInfo.getFieldName(), false);
                method.addJavadoc("@param $L $L \n", name, def(fieldInfo.getRemarks(), name));
                method.addParameter(fieldInfo.getTypeClass(), name);
            }
            method.addStatement("$T delete = new $T().from($T.TABLE)", SQLDelete.class, SQLDelete.class, beanClassName);
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = toJavaName(fieldInfo.getFieldName(), false);
                method.addStatement("delete.where($T.$L).params($L)", beanClassName, db_name, name);
            }
            method.addStatement("return execute(delete)");
            builder.addMethod(method.build());

            //public ${CodeBean.CLASS_NAME} queryById(PKField...) throws SQLException {
            //    SQLSelect select = ${CodeMapper.CLASS_NAME}.sql();
            //    // add where field....
            //    return queryOne(select);
            //}
            method = MethodSpec.methodBuilder("queryById")
                    .addModifiers(PUBLIC)
                    .returns(beanClassName)
                    .addException(SQLException.class);
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String name = toJavaName(fieldInfo.getFieldName(), false);
                method.addJavadoc("@param $L $L \n", name, def(fieldInfo.getRemarks(), name));
                method.addParameter(fieldInfo.getTypeClass(), name);
            }
            method.addStatement("$T select = $T.sql()", SQLSelect.class, mapperClassName);
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = toJavaName(fieldInfo.getFieldName(), false);
                method.addStatement("select.where($T.$L).params($L)", beanClassName, db_name, name);
            }
            method.addStatement("return queryOne(select)");
            builder.addMethod(method.build());

            // 生成文件信息
            JavaFile javaFile = JavaFile.builder(Config.PACKAGE_NAME, builder.build()).build();
            javaFile.writeTo(new File(Config.CLASS_PATH));

            System.out.println("====================================");
            System.out.println("========= End Code Dao Impl ========");
            System.out.println("====================================");
            System.out.println("\r\n");
        }
    }


}
