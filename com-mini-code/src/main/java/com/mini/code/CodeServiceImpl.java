package com.mini.code;

import com.squareup.javapoet.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;

import static com.mini.util.StringUtil.firstLowerCase;
import static java.lang.String.format;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PUBLIC;

public final class CodeServiceImpl {
    protected static void run(Configure configure, String className, String tableName, String prefix) throws Exception {
        System.out.println("====================================");
        System.out.println("========= Start Code Service Impl ==");
        System.out.println("====================================");

        //  Package Name
        String serviceImplPackage = format("%s.service.impl", configure.getPackageName());
        String servicePackage = format("%s.service", configure.getPackageName());
        String daoPackage = format("%s.dao", configure.getPackageName());

        // Class Name
        String serviceImplClassName = String.format("%sServiceImpl", className);
        String serviceClassName = String.format("%sService", className);
        String daoClassName = String.format("%sDao", className);

        // Class
        ClassName serviceClass = ClassName.get(servicePackage, serviceClassName);
        ClassName daoClass = ClassName.get(daoPackage, daoClassName);

        // /**
        //  * ${serviceImplClassName}.java
        //  * @author xchao
        //  */
        // @Component
        TypeSpec.Builder builder = TypeSpec.classBuilder(serviceImplClassName)
                .addModifiers(PUBLIC)
                .addSuperinterface(serviceClass)
                .addAnnotation(Singleton.class)
                .addJavadoc("$L.java \n", serviceImplClassName)
                .addJavadoc("@author xchao \n");

        //@Inject
        //private ${daoClassName} firstLowerCase(${daoClassName});
        builder.addField(FieldSpec.builder(daoClass, firstLowerCase(daoClassName), PRIVATE).build());

        // @Inject
        // public void set${daoClassName}) {
        //    this.${firstLowerCase(className)} = ${firstLowerCase(className)};
        // }
        builder.addMethod(MethodSpec.methodBuilder("set" + daoClassName)
                .addModifiers(PUBLIC)
                .returns(void.class)
                .addAnnotation(Inject.class)
                .addParameter(daoClass, firstLowerCase(daoClassName))
                .addStatement("this.$N = $N", firstLowerCase(daoClassName), firstLowerCase(daoClassName))
                .build());

        //@Override
        //public ${daoClassName} get${daoClassName}() {
        //    Objects.requireNonNull(firstLowerCase(${daoClassName}));
        //    return firstLowerCase(${daoClassName});
        //}
        builder.addMethod(MethodSpec.methodBuilder("get" + daoClassName)
                .addModifiers(PUBLIC)
                .returns(daoClass)
                .addAnnotation(Override.class)
                .addStatement("return $L", firstLowerCase(daoClassName))
                .build());


        // 生成文件信息
        JavaFile javaFile = JavaFile.builder(serviceImplPackage, builder.build()).build();
        javaFile.writeTo(new File(configure.getClassPath()));

        System.out.println("====================================");
        System.out.println("========= End Code Service Impl ====");
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
