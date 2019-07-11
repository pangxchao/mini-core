package com.mini.code;

import com.google.inject.ImplementedBy;
import com.mini.jdbc.BasicsDao;
import com.squareup.javapoet.*;

import java.io.File;

import static java.lang.String.format;
import static javax.lang.model.element.Modifier.PUBLIC;

public final class CodeDao {
    protected static void run(Configure configure, String className, String tableName, String prefix) throws Exception {
        //  Package Name
        String daoImplPackage = format("%s.dao.impl", configure.getPackageName());
        String beanPackage = format("%s.entity", configure.getPackageName());
        String daoPackage = format("%s.dao", configure.getPackageName());

        // Class Name
        String daoImplClassName = String.format("%sDaoImpl", className);
        String daoClassName = String.format("%sDao", className);

        // Class
        ClassName daoImplClass = ClassName.get(daoImplPackage, daoImplClassName);
        ClassName beanClass = ClassName.get(beanPackage, className);

        ///**
        // * ${daoClassName}.java
        // * @author xchao
        // */
        // public interface ${daoClassName}
        TypeSpec.Builder builder = TypeSpec.interfaceBuilder(daoClassName)
                .addModifiers(PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(BasicsDao.class), beanClass))
                .addAnnotation(AnnotationSpec.builder(ImplementedBy.class)
                        .addMember("value", "$T.class", daoImplClass)
                        .build())
                .addJavadoc("$L.java \n", daoClassName)
                .addJavadoc("@author xchao \n");

        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(daoPackage, builder.build()).build();
        javaFile.writeTo(new File(configure.getClassPath()));

        System.out.println("====================================");
        System.out.println("Code Dao : " + className + "\r\n");
    }

    public static void main(String[] args) throws Exception {
        Configure configure = Config.getConfigure();
        for (String[] bean : configure.getDatabaseBeans()) {
            run(configure, bean[0], bean[1], bean[2]);
        }
    }
}
