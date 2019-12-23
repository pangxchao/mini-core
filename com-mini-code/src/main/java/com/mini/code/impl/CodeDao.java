package com.mini.code.impl;

import com.google.inject.ImplementedBy;
import com.mini.code.Configure;
import com.mini.code.Configure.BeanItem;
import com.mini.code.Configure.ClassInfo;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.File;

import static javax.lang.model.element.Modifier.PUBLIC;

@Getter
@Setter
@Builder
public final class CodeDao {
    private String a;


    /**
	 * 生成代码
	 * @param configure 数据库与实体配置信息
	 * @param info      所有类的信息
	 * @param bean      数据库实体信息
	 * @param cover     true-文件存在时覆盖，false-文件存在时不覆盖
	 */
	public static void generator(Configure configure, ClassInfo info, BeanItem bean, boolean cover) throws Exception {
		if (!cover && configure.exists(info.getDaoPackage(), info.getDaoName())) {
			return;
		}

		JavaFile.builder(info.getDaoPackage(), TypeSpec
			// 接口名称
			.interfaceBuilder(info.getDaoName())
			// public 接口
			.addModifiers(PUBLIC)
			// 继承 DaoBase 接口
			.addSuperinterface(info.getBaseDaoClass())
			// 类注释文档
			.addJavadoc("$L.java \n", info.getDaoName())
			.addJavadoc("@author xchao \n")

			// 生成 ImplementedBy 默认实现指定注解
			.addAnnotation(AnnotationSpec.builder(ImplementedBy.class)
				.addMember("value", "$T.class", info.getDaoImplClass())
				.build())

			// 生成类
			.build())
			// 生成文件信息，并将文件信息转出到本地
			.build().writeTo(new File(configure.getClassPath()));

		System.out.println("====================================");
		System.out.println("Code Dao : " + info.getBeanName() + "\r\n");
	}
}
