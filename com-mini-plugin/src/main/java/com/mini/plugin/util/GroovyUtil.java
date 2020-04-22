package com.mini.plugin.util;

import com.intellij.openapi.vfs.VirtualFile;
import com.mini.plugin.config.TableInfo;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;

import java.io.*;
import java.util.EventListener;
import java.util.List;

import static com.intellij.openapi.ui.Messages.showWarningDialog;
import static com.mini.plugin.util.Constants.GENERATED_ERROR;
import static com.squareup.javapoet.ClassName.get;

public final class GroovyUtil implements Serializable, EventListener {
	public static void generate(String template, TableInfo tableInfo, VirtualFile file) {
		try {
			ClassLoader parentLoader = GroovyUtil.class.getClassLoader();
			GroovyClassLoader loader = new GroovyClassLoader(parentLoader);
			((JavaFile) new GroovyShell(loader, new Binding() {{
				setVariable("tableInfo", tableInfo);
			}}).evaluate(template)).writeTo(new File(file.getPath()));
		} catch (GroovyRuntimeException | IOException e) {
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			showWarningDialog(writer.toString(),
					GENERATED_ERROR);
			throw ThrowsUtil.hidden(e);
		}
	}
	
	public static <T> TypeName getParameterizedTypeName(Class<T> clazz, TypeName... typeNames) {
		return ParameterizedTypeName.get(ClassName.get(List.class), typeNames);
	}
	
	public static TypeName getParameterizedTypeName(ClassName className, TypeName... typeNames) {
		return ParameterizedTypeName.get(className, typeNames);
	}
	
	public static ClassName dateFormatUtilClass() {
		return get("com.mini.core.util", "DateFormatUtil");
	}
	
	public static ClassName columnClass() {
		return get("com.mini.core.jdbc.annotation", "Column");
	}
	
	public static ClassName commentClass() {
		return get("com.mini.core.jdbc.annotation", "Comment");
	}
	
	public static ClassName autoClass() {
		return get("com.mini.core.jdbc.annotation", "Auto");
	}
	
	public static ClassName createAtClass() {
		return get("com.mini.core.jdbc.annotation", "CreateAt");
	}
	
	public static ClassName updateAtClass() {
		return get("com.mini.core.jdbc.annotation", "UpdateAt");
	}
	
	public static ClassName delClass() {
		return get("com.mini.core.jdbc.annotation", "Del");
	}
	
	public static ClassName idClass() {
		return get("com.mini.core.jdbc.annotation", "Id");
	}
	
	public static ClassName joinClass() {
		return get("com.mini.core.jdbc.annotation", "Join");
	}
	
	public static ClassName lockClass() {
		return get("com.mini.core.jdbc.annotation", "Lock");
	}
	
	public static ClassName tableClass() {
		return get("com.mini.core.jdbc.annotation", "Table");
	}
	
	public static ClassName jdbcInterfaceClass() {
		return get("com.mini.core.jdbc", "JdbcInterface");
	}
	
	public static ClassName jdbcTemplateClass() {
		return get("com.mini.core.jdbc", "JdbcTemplate");
	}
	
	public static ClassName abstractDaoClass() {
		return get("com.mini.core.jdbc", "AbstractDao");
	}
	
	public static ClassName sqlBuilderClass() {
		return get("com.mini.core.jdbc.builder", "SQLBuilder");
	}
	
	public static ClassName beanMapperClass() {
		return get("com.mini.core.jdbc.mapper", "BeanMapper");
	}
	
	public static ClassName pagingClass() {
		return get("com.mini.core.jdbc.model", "Paging");
	}
	
	public static ClassName paramClass() {
		return get("com.mini.core.web.annotation", "Param");
	}
	
	public static ClassName nonnullClass() {
		return get("javax.annotation", "Nonnull");
	}
	
	public static ClassName singletonClass() {
		return get("javax.inject", "Singleton");
	}
	
	public static ClassName injectClass() {
		return get("javax.inject", "Inject");
	}
	
	public static ClassName namedClass() {
		return get("javax.inject", "Named");
	}
	
	public static ClassName lombokEqualsAndHashCodeClass() {
		return get("lombok", "EqualsAndHashCode");
	}
	
	public static ClassName lombokToStringClass() {
		return get("lombok", "ToString");
	}
	
	public static ClassName lombokGetterClass() {
		return get("lombok", "Getter");
	}
	
	public static ClassName lombokSetterClass() {
		return get("lombok", "Setter");
	}
	
	public static ClassName lombokDataClass() {
		return get("lombok", "Data");
	}
	
	public static ClassName lombokSuperBuilderClass() {
		return get("lombok.experimental", "SuperBuilder");
	}
	
	public static ClassName lombokTolerateClass() {
		return get("lombok.experimental", "Tolerate");
	}
	
	public static String beanName(TableInfo table) {
		return table.getEntityName();
	}
	
	public static String beanPackage(TableInfo table) {
		return table.getPackageName() + ".entity";
	}
	
	public static ClassName beanClass(TableInfo table) {
		return get(beanPackage(table), beanName(table));
	}
	
	public static String builderName(TableInfo table) {
		return table.getEntityName() + "Builder";
	}
	
	public static ClassName builderClass(TableInfo table) {
		return get(beanPackage(table), beanName(table),
				builderName(table));
	}
	
	public static String daoBaseName(TableInfo table) {
		return table.getEntityName() + "BaseDao";
	}
	
	public static String daoBasePackage(TableInfo table) {
		return table.getPackageName() + ".dao.base";
	}
	
	public static ClassName daoBaseClass(TableInfo table) {
		return get(daoBasePackage(table), daoBaseName(table));
	}
	
	public static String daoName(TableInfo table) {
		return table.getEntityName() + "Dao";
	}
	
	public static String daoPackage(TableInfo table) {
		return table.getPackageName() + ".dao";
	}
	
	public static ClassName daoClass(TableInfo table) {
		return get(daoPackage(table), daoName(table));
	}
	
	public static String daoImplName(TableInfo table) {
		return table.getEntityName() + "DaoImpl";
	}
	
	public static String daoImplPackage(TableInfo table) {
		return table.getPackageName() + ".dao.impl";
	}
	
	public static ClassName daoImplClass(TableInfo table) {
		return get(daoImplPackage(table), daoImplName(table));
	}
}
