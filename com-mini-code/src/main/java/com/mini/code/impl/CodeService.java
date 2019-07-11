package com.mini.code.impl;

import com.google.inject.ImplementedBy;
import com.mini.code.Configure;
import com.squareup.javapoet.*;

import java.io.File;

import static java.lang.String.format;
import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

public final class CodeService {
    protected static void run(Configure configure, String className, String tableName, String prefix) throws Exception {
        //  Package Name
        String serviceImplPackage = format("%s.service.impl", configure.getPackageName());
        String servicePackage = format("%s.service", configure.getPackageName());
        String daoPackage = format("%s.dao", configure.getPackageName());

        // Class Name
        String serviceImplClassName = String.format("%sServiceImpl", className);
        String serviceClassName = String.format("%sService", className);
        String daoClassName = String.format("%sDao", className);

        // Class
        ClassName serviceImplClass = ClassName.get(serviceImplPackage, serviceImplClassName);
        ClassName daoClass = ClassName.get(daoPackage, daoClassName);

        // /**
        //  * ${JAVA_NAME}.java
        //  * @author xchao
        //  */
        // public interface ${serviceClassName}
        TypeSpec.Builder builder = TypeSpec.interfaceBuilder(serviceClassName)
                .addModifiers(PUBLIC)
                .addAnnotation(AnnotationSpec.builder(ImplementedBy.class)
                        .addMember("value", "$T.class", serviceImplClass)
                        .build())
                .addJavadoc("$L.java \n", serviceClassName)
                .addJavadoc("@author xchao \n");

        // InitInfoDao getInitInfoDao();
        builder.addMethod(MethodSpec.methodBuilder("get" + daoClassName)
                .addModifiers(PUBLIC, ABSTRACT)
                .returns(daoClass)
                .build());

        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(servicePackage, builder.build()).build();
        javaFile.writeTo(new File(configure.getClassPath()));

        System.out.println("====================================");
        System.out.println("Code Service : " + className + "\r\n");
    }

    public static void generator(Configure configure, Configure.BeanItem bean) throws Exception {
        run(configure, bean.className, bean.tableName, bean.prefix);
    }
}
