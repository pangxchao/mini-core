package com.mini.code;

import com.mini.jdbc.sql.SQLDelete;
import com.mini.jdbc.sql.SQLInsert;
import com.mini.jdbc.sql.SQLSelect;
import com.mini.jdbc.sql.SQLUpdate;
import com.mini.jdbc.template.DaoTemplate;
import com.squareup.javapoet.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import static com.mini.util.StringUtil.*;
import static java.lang.String.format;
import static javax.lang.model.element.Modifier.*;

public final class CodeDaoImpl {
    protected static void run(Configure configure, String className, String tableName, String prefix) throws Exception {
        System.out.println("====================================");
        System.out.println("========= Start Code Dao Impl ======");
        System.out.println("====================================");

        // Bean package Name
        String mapperPackage = format("%s.entity.mapper", configure.getPackageName());
        String daoImplPackage = format("%s.dao.impl", configure.getPackageName());
        String beanPackage = format("%s.entity", configure.getPackageName());
        String daoPackage = format("%s.dao", configure.getPackageName());

        // Class Name
        String daoClassName = String.format("%sDao", className);
        String mapperClassName = String.format("%sMapper", className);
        String daoImplClassName = String.format("%sDaoImpl", className);

        // Class
        ClassName mapperClass = ClassName.get(mapperPackage, mapperClassName);
        ClassName beanClass = ClassName.get(beanPackage, className);
        ClassName daoClass = ClassName.get(daoPackage, daoClassName);

        // 定义 method 对象
        // 获取所有字段信息

        try (Connection connection = configure.getConnection()) {
            MethodSpec.Builder method;
            List<Util.FieldInfo> fieldList = Util.getColumns(connection, configure.getDatabaseName(), tableName, prefix);
            List<Util.FieldInfo> PKFieldList = Util.getPrimaryKey(connection, configure.getDatabaseName(), tableName, prefix);

            // /**
            //  * ${CLASS_NAME}.java
            //  * @author xchao
            //  */
            // @Component
            // public class ${CLASS_NAME} implement ${CodeDao.CLASS_NAME}
            TypeSpec.Builder builder = TypeSpec.classBuilder(daoImplClassName)
                    .addModifiers(PUBLIC)
                    .addSuperinterface(daoClass)
                    .addAnnotation(AnnotationSpec.builder(Component.class)
                            .addMember("value", "$S", firstLowerCase(daoClassName))
                            .build())
                    .addJavadoc("$L.java \n", daoImplClassName)
                    .addJavadoc("@author xchao \n");

            // private IDaoTemplate daoTemplate;
            builder.addField(FieldSpec.builder(DaoTemplate.class, "daoTemplate", PRIVATE, FINAL).build());

            // private ${CodeMapper.CLASS_NAME} ${CodeMapper.CLASS_NAME};
            builder.addField(FieldSpec.builder(mapperClass, firstLowerCase(mapperClassName), PRIVATE, FINAL).build());

            // public ${daoImplClassName}(DaoTemplate daoTemplate, ${mapperClassName} firstLowerCase(mapperClassName)) {
            //     this.daoTemplate = daoTemplate;
            //     this.firstLowerCase(mapperClassName) = firstLowerCase(mapperClassName);
            // }
            builder.addMethod(MethodSpec.constructorBuilder()
                    .addModifiers(PUBLIC)
                    .addParameter(DaoTemplate.class, "daoTemplate")
                    .addParameter(mapperClass, firstLowerCase(mapperClassName))
                    .addStatement("this.$N = $N", "daoTemplate", "daoTemplate")
                    .addStatement("this.$N = $N", firstLowerCase(mapperClassName), firstLowerCase(mapperClassName))
                    .build());

            // @Override
            // public IDaoTemplate getWriteDaoTemplate() {
            //      return daoTemplate;
            // }
            builder.addMethod(MethodSpec.methodBuilder("getWriteTemplate")
                    .addModifiers(PUBLIC)
                    .returns(DaoTemplate.class)
                    .addAnnotation(Override.class)
                    .addStatement("return daoTemplate")
                    .build());

            // @Override
            // public IDaoTemplate getReadDaoTemplate() {
            //      return daoTemplate;
            // }
            builder.addMethod(MethodSpec.methodBuilder("getReadTemplate")
                    .addModifiers(PUBLIC)
                    .returns(DaoTemplate.class)
                    .addAnnotation(Override.class)
                    .addStatement("return daoTemplate")
                    .build());

            // @Override
            // public ${CodeMapper.CLASS_NAME} getMapper() {
            //      return ${firstLowerCase(CodeMapper.CLASS_NAME)};
            // }
            builder.addMethod(MethodSpec.methodBuilder("getMapper")
                    .addModifiers(PUBLIC)
                    .returns(mapperClass)
                    .addAnnotation(Override.class)
                    .addStatement("return $L", firstLowerCase(mapperClassName))
                    .build());


            //@Override
            //public int insert(${CodeBean.CLASS_NAME} ${firstLowerCase(CodeBean.CLASS_NAME)})  {
            //    SQLInsert insert = new SQLInsert(${CodeBean.CLASS_NAME}.TABLE);
            //    // setting field....
            //    return execute(insert);
            //}
            method = MethodSpec.methodBuilder("insert")
                    .addModifiers(PUBLIC)
                    .returns(int.class)
                    .addParameter(beanClass, firstLowerCase(className))
                    .addAnnotation(Override.class)
                    .addStatement("$T insert = new $T($T.TABLE)", SQLInsert.class, SQLInsert.class, beanClass);
            for (Util.FieldInfo fieldInfo : fieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = toJavaName(fieldInfo.getFieldName(), true);
                method.addCode("// $L \n", fieldInfo.getRemarks());
                method.addStatement("insert.put($T.$L).params($L.get$L())", beanClass, db_name, firstLowerCase(className), name);
            }
            method.addStatement("return execute(insert)");
            builder.addMethod(method.build());

            //@Override
            //public int update(${CodeBean.CLASS_NAME} ${firstLowerCase(CodeBean.CLASS_NAME)})  {
            //    SQLUpdate update = new SQLUpdate().from(${CodeBean.CLASS_NAME}.TABLE);
            //    // setting field....
            //    // add where field....
            //    return execute(update);
            //}
            method = MethodSpec.methodBuilder("update")
                    .addModifiers(PUBLIC)
                    .returns(int.class)
                    .addParameter(beanClass, firstLowerCase(className))
                    .addAnnotation(Override.class)
                    .addStatement("$T update = new $T().from($T.TABLE)", SQLUpdate.class, SQLUpdate.class, beanClass);
            for (Util.FieldInfo fieldInfo : fieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = toJavaName(fieldInfo.getFieldName(), true);
                method.addCode("// $L \n", fieldInfo.getRemarks());
                method.addStatement("update.put($T.$L).params($L.get$L())", beanClass, db_name, firstLowerCase(className), name);
            }
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = toJavaName(fieldInfo.getFieldName(), true);
                method.addCode("// $L \n", fieldInfo.getRemarks());
                method.addStatement("update.where($T.$L).params($L.get$L())", beanClass, db_name, firstLowerCase(className), name);
            }
            method.addStatement("return execute(update)");
            builder.addMethod(method.build());


            //@Override
            //public int delete(${CodeBean.CLASS_NAME} ${firstLowerCase(CodeBean.CLASS_NAME)})  {
            //    SQLDelete delete = new SQLDelete().from(${CodeBean.CLASS_NAME}.TABLE);
            //   // add where field....
            //    return execute(delete);
            //}
            method = MethodSpec.methodBuilder("delete")
                    .addModifiers(PUBLIC)
                    .returns(int.class)
                    .addParameter(beanClass, firstLowerCase(className))
                    .addAnnotation(Override.class)
                    .addStatement("$T delete = new $T().from($T.TABLE)", SQLDelete.class, SQLDelete.class, beanClass);
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = toJavaName(fieldInfo.getFieldName(), true);
                method.addStatement("delete.where($T.$L).params($L.get$L())", beanClass, db_name, firstLowerCase(className), name);
            }
            method.addStatement("return execute(delete)");
            builder.addMethod(method.build());

            //public int deleteById(PKField...)   {
            //    SQLDelete delete = new SQLDelete().from(${CodeBean.CLASS_NAME}.TABLE);
            //    // add where field....
            //    return execute(delete);
            //}
            method = MethodSpec.methodBuilder("deleteById")
                    .addModifiers(PUBLIC)
                    .returns(int.class)
                    .addJavadoc("根据ID删除实体信息 \n");
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String name = toJavaName(fieldInfo.getFieldName(), false);
                method.addJavadoc("@param $L $L \n", name, def(fieldInfo.getRemarks(), name));
                method.addParameter(fieldInfo.getTypeClass(), name);
            }
            method.addStatement("$T delete = new $T().from($T.TABLE)", SQLDelete.class, SQLDelete.class, beanClass);
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = toJavaName(fieldInfo.getFieldName(), false);
                method.addStatement("delete.where($T.$L).params($L)", beanClass, db_name, name);
            }
            method.addStatement("return execute(delete)");
            builder.addMethod(method.build());

            //public ${CodeBean.CLASS_NAME} queryById(PKField...)   {
            //    SQLSelect select = ${CodeMapper.CLASS_NAME}.sql();
            //    // add where field....
            //    return queryOne(select);
            //}
            method = MethodSpec.methodBuilder("queryById")
                    .addModifiers(PUBLIC)
                    .returns(beanClass)
                    .addJavadoc("根据ID查询实体信息 \n");
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String name = toJavaName(fieldInfo.getFieldName(), false);
                method.addJavadoc("@param $L $L \n", name, def(fieldInfo.getRemarks(), name));
                method.addParameter(fieldInfo.getTypeClass(), name);
            }
            method.addStatement("$T select = $T.sql()", SQLSelect.class, mapperClass);
            for (Util.FieldInfo fieldInfo : PKFieldList) {
                String db_name = fieldInfo.getFieldName().toUpperCase();
                String name = toJavaName(fieldInfo.getFieldName(), false);
                method.addStatement("select.where($T.$L).params($L)", beanClass, db_name, name);
            }
            method.addStatement("return queryOne(select)");
            builder.addMethod(method.build());

            // 生成文件信息
            JavaFile javaFile = JavaFile.builder(daoImplPackage, builder.build()).build();
            javaFile.writeTo(new File(configure.getClassPath()));
        }
        System.out.println("====================================");
        System.out.println("========= End Code Dao Impl ========");
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
