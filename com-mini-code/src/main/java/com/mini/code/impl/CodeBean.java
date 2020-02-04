package com.mini.code.impl;

import com.mini.code.Configure;
import com.mini.code.Configure.BeanItem;
import com.mini.code.Configure.ClassInfo;
import com.mini.code.util.FieldSpecBuilder;
import com.mini.code.util.MethodSpecBuilder;
import com.mini.code.util.TypeSpecBuilder;
import com.mini.core.holder.jdbc.Column;
import com.mini.core.holder.jdbc.Comment;
import com.mini.core.holder.jdbc.Id;
import com.mini.core.holder.jdbc.Table;
import com.mini.core.holder.web.Param;
import com.mini.core.util.DateFormatUtil;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.tools.JavaFileObject;
import java.io.File;
import java.io.Serializable;
import java.util.Date;

import static com.mini.core.util.StringUtil.firstUpperCase;
import static com.mini.core.util.StringUtil.toJavaName;
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
			// 添加 @Param 注解
			.addAnnotation(Param.class)
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
				.initializer("$S", bean.tableName)
				.addAnnotation(AnnotationSpec.builder(Comment.class)
					.addMember("value", "$S", "表名称" + bean.tableName)
					.build())
				.build())
			
			// 生成字段常量
			.forAdd(info.getFieldList(), (builder, fieldInfo) -> {
				String fieldName = fieldInfo.getColumnName().toUpperCase();
				builder.addField(FieldSpec.builder(String.class, fieldName)
					.addModifiers(PUBLIC, STATIC, FINAL)
					.initializer("$S ", fieldInfo.getColumnName())
					.addAnnotation(AnnotationSpec.builder(Comment.class)
						.addMember("value", "$S", fieldInfo.getRemarks())
						.build())
					.build());
			})
			
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
						.addMember("value", "$L", fieldInfo.getColumnName().toUpperCase())
						.build())
					.build());
			})
			
			// 生成日期格式化扩展方法
			.forAdd(info.getFieldList(), (builder, fieldInfo) -> { //
				builder.ifAdd(Date.class.isAssignableFrom(fieldInfo.getTypeClass()), m -> {
					String name = toJavaName(fieldInfo.getFieldName(), false);
					builder.addMethod(MethodSpecBuilder.methodBuilder("get" + firstUpperCase(name) + "_DT")
						.addModifiers(PUBLIC, FINAL)
						.returns(String.class)
						.addStatement("return $T.formatDateTime($N)", DateFormatUtil.class, name)
						.build());
					
					builder.addMethod(MethodSpecBuilder.methodBuilder("get" + firstUpperCase(name) + "_D")
						.addModifiers(PUBLIC, FINAL)
						.returns(String.class)
						.addStatement("return $T.formatDate($N)", DateFormatUtil.class, name)
						.build());
					
					builder.addMethod(MethodSpecBuilder.methodBuilder("get" + firstUpperCase(name) + "_T")
						.addModifiers(PUBLIC, FINAL)
						.returns(String.class)
						.addStatement("return $T.formatTime($N)", DateFormatUtil.class, name)
						.build());
				});
			})
			
			// 生成静态无参数 builder 方法
			.addMethod(MethodSpecBuilder.methodBuilder("builder")
				.addModifiers(PUBLIC, STATIC)
				.returns(info.getBuilderClass())
				.addStatement("return new $T(new $T())", info.getBuilderClass(), info.getBeanClass())
				.build())
			
			// 生成静态 copy builder 方法
			.addMethod(MethodSpecBuilder.methodBuilder("builder")
				.addModifiers(PUBLIC, STATIC)
				.returns(info.getBuilderClass())
				.addParameter(info.getBeanClass(), "copy")
				.addCode("return $T.builder()", info.getBeanClass())
				.forAdd(info.getFieldList(), (method, fieldInfo) -> {
					String name = toJavaName(fieldInfo.getFieldName(), false);
					method.addCode("\n\t .$L(copy.get$L())", name, firstUpperCase(name));
				})
				.addStatement("")
				.build())
			
			// 生成静态 Builder 类对象
			.addType(TypeSpecBuilder.classBuilder(info.getBuilderClass())
				.addModifiers(PUBLIC, STATIC)
				// 私有属性
				.addField(FieldSpecBuilder.builder(info.getBeanClass(), info.getBeanName())
					.addModifiers(PRIVATE, FINAL)
					.build())
				// 构造方法
				.addMethod(MethodSpec.constructorBuilder()
					.addModifiers(PROTECTED)
					.addParameter(info.getBeanClass(), info.getBeanName())
					.addStatement("this.$L = $L", info.getBeanName(), info.getBeanName())
					.build())
				// 为每个属性生成一个 Setter 方法
				.forAdd(info.getFieldList(), (builder, fieldInfo) -> {
					String name = toJavaName(fieldInfo.getFieldName(), false);
					builder.addMethod(MethodSpecBuilder.methodBuilder(name)
						.addModifiers(PUBLIC)
						.returns(info.getBuilderClass())
						.addParameter(fieldInfo.getTypeClass(), name)
						.addStatement("$L.set$L($L)", info.getBeanName(), firstUpperCase(name), name)
						.addStatement("return this")
						.build());
				})
				// 生成 builder 方法
				.addMethod(MethodSpecBuilder.methodBuilder("build")
					.addModifiers(PUBLIC)
					.returns(info.getBeanClass())
					.addAnnotation(Nonnull.class)
					.addStatement("return $L", info.getBeanName())
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
