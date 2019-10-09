package com.mini.code.impl;

import static com.mini.code.util.Util.getColumns;
import static com.mini.code.util.Util.getPrimaryKey;
import static javax.lang.model.element.Modifier.DEFAULT;
import static javax.lang.model.element.Modifier.PUBLIC;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import com.mini.code.Configure;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.Util;
import com.mini.util.StringUtil;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public final class CodeBase {
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
		TypeSpec.Builder builder = TypeSpec.interfaceBuilder(info.baseName).addModifiers(PUBLIC).addSuperinterface(Serializable.class)
				.addJavadoc("$L.java \n", info.baseName).addJavadoc("@author xchao \n");

		// 处理 Getter Setter 方法
		for (Util.FieldInfo fieldInfo : fieldList) {
			String name = StringUtil.toJavaName(fieldInfo.getFieldName(), false);
			// Getter 方法
			builder.addMethod(getter(fieldInfo, name).build());
			// Setter 方法
			builder.addMethod(setter(fieldInfo, name).build());
		}

		// 生成文件信息
		JavaFile javaFile = JavaFile.builder(info.basePackage, builder.build()).build();
		javaFile.writeTo(new File(configure.getClassPath()));

		System.out.println("====================================");
		System.out.println("Code Base : " + info.beanName + "\r\n");
	}

	// 生成 getter 方法
	private static MethodSpec.Builder getter(Util.FieldInfo fieldInfo, String name) {
		return MethodSpec.methodBuilder("get" + StringUtil.firstUpperCase(name)).addModifiers(PUBLIC, DEFAULT)
				// 设置返回类型
				.returns(fieldInfo.getTypeClass()).addException(UnsupportedOperationException.class)
				// 方法体内容
				.addStatement("throw new $T()", UnsupportedOperationException.class)
				// 生成方法 JAVA DOC
				.addJavadoc("$L. \n", StringUtil.def(fieldInfo.getRemarks(), "Gets the value of " + name))
				.addJavadoc("@return The value of $L \n", name);
	}

	// 生成setter方法
	private static MethodSpec.Builder setter(Util.FieldInfo fieldInfo, String name) {
		return MethodSpec.methodBuilder("set" + StringUtil.firstUpperCase(name)).addModifiers(PUBLIC, DEFAULT)
				// 设置返回类型
				.returns(void.class)
				// 添加方法参数列表
				.addParameter(fieldInfo.getTypeClass(), name).addException(UnsupportedOperationException.class)
				// 添加方法体内容
				.addStatement("throw new $T()", UnsupportedOperationException.class)
				// 生成方法 JAVA DOC
				.addJavadoc("$L. \n", StringUtil.def(fieldInfo.getRemarks(), "Sets the value of " + name))
				.addJavadoc("@param $L The value of $L \n", name, name);
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
		if (isCover || !Util.exists(configure, info.basePackage, info.baseName)) {
			run(configure, info, bean.tableName, bean.prefix, fieldList, pkFieldList);
		}
	}
}
