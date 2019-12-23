package com.mini.code.impl;

import com.mini.code.Configure;
import com.mini.code.Configure.BeanItem;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.FieldSpecBuilder;
import com.mini.code.util.MethodSpecBuilder;
import com.mini.code.util.TypeSpecBuilder;
import com.mini.core.jdbc.annotation.Column;
import com.mini.core.jdbc.annotation.Id;
import com.mini.core.jdbc.annotation.Table;
import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.util.JdbcUtil;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import lombok.Data;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mini.core.util.StringUtil.*;
import static javax.lang.model.element.Modifier.*;

public final class CodeBean {
	/**
	 * 生成代码
	 * @param configure 数据库与实体配置信息
	 * @param info      所有类的信息
	 * @param bean      数据库实体信息
	 * @param cover     true-文件存在时覆盖，false-文件存在时不覆盖
	 */
	public static void generator(Configure configure, ClassInfo info, BeanItem bean, boolean cover) throws Exception {
		if (!cover && configure.exists(info.getBeanPackage(), info.getBeanName())) {
			return;
		}

		JavaFile.builder(info.getBeanPackage(), TypeSpecBuilder
			// 类名称
			.classBuilder(info.getBeanClass())
			// public 类
			.addModifiers(PUBLIC)
			// 实现 Serializable 接口
			.addSuperinterface(Serializable.class)
			// 添加 @Data 注解
			.addAnnotation(Data.class)
			// 添加 @Table 注解
			.addAnnotation(AnnotationSpec.builder(Table.class)
				.addMember("value", "$S", bean.tableName)
				.build())
			// 添加类注释文档
			.addJavadoc("$L.java \n", info.getBeanName())
			.addJavadoc("@author xchao \n")

			// serialVersionUID 属性，IDEA 编辑器一般不需要该属性
			.ifAdd(configure.generatorSerialVersionUID(), (builder) -> //
				builder.addField(FieldSpec.builder(long.class, "serialVersionUID")
					.addModifiers(PRIVATE, STATIC, FINAL)
					.initializer("$L", "-1L")
					.build()))

			// 生成常量 TABLE 字段
			.addField(FieldSpec.builder(String.class, "TABLE")
				.addModifiers(PUBLIC, STATIC, FINAL)
				.addJavadoc(" 表名称 $L \n", bean.tableName)
				.initializer("$S", bean.tableName)
				.build())

			// 生成字段常量
			.forAdd(info.getFieldList(), (builder, fieldInfo) -> {
				String db_name = fieldInfo.getFieldName().toUpperCase();
				builder.addField(FieldSpec.builder(String.class, db_name)
					.addModifiers(PUBLIC, STATIC, FINAL)
					.initializer("$S ", fieldInfo.getColumnName())
					.addJavadoc("$L \n", defaultIfEmpty(fieldInfo.getRemarks(), fieldInfo.getColumnName()))
					.build());
			})

			// 生成默认无参构造方法
			.addMethod(MethodSpec.constructorBuilder()
				.addModifiers(PUBLIC)
				.build())

			// 生成属性信息
			.forAdd(info.getFieldList(), (builder, fieldInfo) -> {
				String name = toJavaName(fieldInfo.getFieldName(), false);
				builder.addField(FieldSpecBuilder.builder(fieldInfo.getTypeClass(), name, PRIVATE)
					// @Id 注解
					.ifAdd(info.getPKFieldList().stream().anyMatch(v ->    //
							v.getColumnName().equals(fieldInfo.getColumnName())), //
						field -> field.addAnnotation(Id.class))
					// @Column 注解
					.addAnnotation(AnnotationSpec.builder(Column.class)
						.addMember("value", "$S", fieldInfo.getColumnName())
						.build())
					.build());
			})

			// 生成私有 Builder 构造方法
			.addMethod(MethodSpecBuilder.constructorBuilder()
				.addModifiers(PRIVATE)
				.addParameter(info.getBuilderClass(), "builder")
				.forAdd(info.getFieldList(), (method, fieldInfo) -> {
					String name = toJavaName(fieldInfo.getFieldName(), false);
					method.addStatement("set$L(builder.$L)", firstUpperCase(name), name);
				}).build())

			// 生成静态无参数 newBuilder 方法
			.addMethod(MethodSpecBuilder.methodBuilder("newBuilder")
				.addModifiers(PUBLIC, STATIC)
				.returns(info.getBuilderClass())
				.addStatement("return new $T()", info.getBuilderClass())
				.build())

			// 生成静态 copy newBuilder 方法
			.addMethod(MethodSpecBuilder.methodBuilder("newBuilder")
				.addModifiers(PUBLIC, STATIC)
				.returns(info.getBuilderClass())
				.addParameter(info.getBeanClass(), "copy")
				.addStatement("$T builder = new $T()", info.getBuilderClass(), info.getBuilderClass())
				.forAdd(info.getFieldList(), (method, fieldInfo) -> {
					String name = toJavaName(fieldInfo.getFieldName(), false);
					method.addStatement("builder.$L = copy.get$L()", name, firstUpperCase(name));
				})
				.addStatement("return builder")
				.build())

			// 生成静态 Mapper mapper 方法
			.addMethod(MethodSpecBuilder.methodBuilder("mapper")
				.addModifiers(PUBLIC, STATIC)
				.returns(info.getBeanClass())
				.addParameter(ResultSet.class, "rs")
				.addParameter(int.class, "number")
				.addException(SQLException.class)
				.addStatement("$T builder = $T.newBuilder()", info.getBuilderClass(), info.getBeanClass())
				.forAdd(info.getFieldList(), (method, fieldInfo) -> {
					String type_name = fieldInfo.getMapperGetName();
					String db_name = fieldInfo.getFieldName().toUpperCase();
					String name = toJavaName(fieldInfo.getFieldName(), false);
					method.addStatement("builder.$L = $T.get$L(rs, $L)", name, JdbcUtil.class, type_name, db_name);
				}).addStatement("return builder.build()")
				.build())

			// 生成静态 Builder 类对象
			.addType(TypeSpecBuilder.classBuilder(info.getBuilderClass())
				.addModifiers(PUBLIC, STATIC, FINAL)
				// 生成所有字段的属性
				.forAdd(info.getFieldList(), (builder, fieldInfo) -> {
					String name = toJavaName(fieldInfo.getFieldName(), false);
					builder.addField(fieldInfo.getTypeClass(), name, PRIVATE);
				})
				// 生成私有无参构造方法
				.addMethod(MethodSpecBuilder.constructorBuilder()
					.addModifiers(PRIVATE)
					.build())
				// 为每个属性生成一个 Setter 方法
				.forAdd(info.getFieldList(), (builder, fieldInfo) -> {
					String name = toJavaName(fieldInfo.getFieldName(), false);
					builder.addMethod(MethodSpecBuilder.methodBuilder("set" + firstUpperCase(name))
						.addModifiers(PUBLIC, FINAL)
						.returns(info.getBuilderClass())
						.addParameter(fieldInfo.getTypeClass(), name)
						.addStatement("this.$L = $L", name, name)
						.addStatement("return this")
						.build());

				})
				// 生成 builder 方法
				.addMethod(MethodSpecBuilder.methodBuilder("build")
					.addModifiers(PUBLIC, FINAL)
					.returns(info.getBeanClass())
					.addAnnotation(Nonnull.class)
					.addStatement("return new $T(this)", info.getBeanClass())
					.build())
				.build())

			// 生成静态的 SQLBuilder 类对象
			.addType(TypeSpecBuilder.classBuilder(info.getSQLClass())
				.addModifiers(PUBLIC, STATIC)
				.superclass(SQLBuilder.class)
				// 生成默认 PROTECTED 无参构造方法
				.addMethod(MethodSpecBuilder.constructorBuilder()
					.addModifiers(PROTECTED)
					.forAdd(info.getFieldList(), (method, fieldInfo) -> {
						String db_name = fieldInfo.getFieldName().toUpperCase();
						method.addStatement("select($L)", db_name);
					})
					.addStatement("from(TABLE)")
					.build())
				.build())

			// 生成类
			.build())
			// 生成文件信息，并将文件信息转出到本地
			.build().writeTo(new File(configure.getClassPath()));

		System.out.println("====================================");
		System.out.println("Code Bean : " + info.getBeanName() + "\r\n");
	}
}
