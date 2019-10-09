package com.mini.code.impl;

import static com.mini.code.util.Util.getColumns;
import static com.mini.code.util.Util.getPrimaryKey;
import static javax.lang.model.element.Modifier.PUBLIC;

import java.io.File;
import java.util.List;

import com.google.inject.ImplementedBy;
import com.mini.code.Configure;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.Util;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

public final class CodeDao {
	/**
	 * 生成代码
	 * @param configure   数据库与实体配置信息
	 * @param info        所有类的信息
	 * @param tableName   数据库表名
	 * @param prefix      字段名前缀
	 * @param fieldList   所有字段信息
	 * @param pkFieldList 主键字段信息
	 */

	protected static void run(Configure configure, ClassInfo info, String tableName, String prefix, //
			List<Util.FieldInfo> fieldList, List<Util.FieldInfo> pkFieldList) throws Exception {
		// 生成类信息
		TypeSpec.Builder builder = TypeSpec.interfaceBuilder(info.daoName).addModifiers(PUBLIC).addSuperinterface(info.daoBaseClass)
				.addAnnotation(AnnotationSpec.builder(ImplementedBy.class).addMember("value", "$T.class", info.daoImplClass).build())
				.addJavadoc("$L.java \n", info.daoName).addJavadoc("@author xchao \n");

		// 生成文件信息
		JavaFile javaFile = JavaFile.builder(info.daoPackage, builder.build()).build();
		javaFile.writeTo(new File(configure.getClassPath()));

		System.out.println("====================================");
		System.out.println("Code Dao : " + info.beanName + "\r\n");
	}

	/**
	 * 生成java代码
	 * @param configure 数据库与实体配置信息
	 * @param bean      数据库表与实体关联配置
	 * @param isCover   是否覆盖已存在的文件
	 */
	public static void generator(Configure configure, Configure.BeanItem bean, boolean isCover) throws Exception {
		List<Util.FieldInfo> pkFieldList = getPrimaryKey(configure.getJdbcTemplate(), //
				configure.getDatabaseName(), bean.tableName, bean.prefix);  //
		List<Util.FieldInfo> fieldList = getColumns(configure.getJdbcTemplate(), //
				configure.getDatabaseName(), bean.tableName, bean.prefix); //
		ClassInfo info = new ClassInfo(configure, bean.className);

		// 不存在或者覆盖时生成
		if (isCover || !Util.exists(configure, info.daoPackage, info.daoName)) {
			run(configure, info, bean.tableName, bean.prefix, fieldList, pkFieldList);
		}
	}
}
