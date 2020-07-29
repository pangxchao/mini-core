package com.mini.plugin.builder.javapoet;

import com.mini.plugin.builder.AbstractBuilder;
import com.squareup.javapoet.*;
import com.squareup.javapoet.MethodSpec.Builder;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;
import java.util.Map;

public final class MethodSpecBuilder extends AbstractBuilder<MethodSpecBuilder> {
	private final Builder builder;
	
	private MethodSpecBuilder(Builder builder) {
		this.builder = builder;
	}
	
	public static MethodSpecBuilder methodBuilder(String name) {
		Builder builder = MethodSpec.methodBuilder(name);
		return new MethodSpecBuilder(builder);
	}
	
	public static MethodSpecBuilder constructorBuilder() {
		Builder builder = MethodSpec.constructorBuilder();
		return new MethodSpecBuilder(builder);
	}
	
	
	public MethodSpecBuilder addJavadoc(String format, Object... args) {
		builder.addJavadoc(format, args);
		return this;
	}
	
	
	public MethodSpecBuilder addJavadoc(CodeBlock block) {
		builder.addJavadoc(block);
		return this;
	}
	
	
	public MethodSpecBuilder addAnnotations(Iterable<AnnotationSpec> annotationSpecs) {
		builder.addAnnotations(annotationSpecs);
		return this;
	}
	
	
	public MethodSpecBuilder addAnnotation(AnnotationSpec annotationSpec) {
		builder.addAnnotation(annotationSpec);
		return this;
	}
	
	
	public MethodSpecBuilder addAnnotation(ClassName annotation) {
		builder.addAnnotation(annotation);
		return this;
	}
	
	
	public MethodSpecBuilder addAnnotation(Class<?> annotation) {
		builder.addAnnotation(annotation);
		return this;
	}
	
	
	public MethodSpecBuilder addModifiers(Modifier... modifiers) {
		builder.addModifiers(modifiers);
		return this;
	}
	
	
	public MethodSpecBuilder addModifiers(Iterable<Modifier> modifiers) {
		builder.addModifiers(modifiers);
		return this;
	}
	
	
	public MethodSpecBuilder addTypeVariables(Iterable<TypeVariableName> typeVariables) {
		builder.addTypeVariables(typeVariables);
		return this;
	}
	
	
	public MethodSpecBuilder addTypeVariable(TypeVariableName typeVariable) {
		builder.addTypeVariable(typeVariable);
		return this;
	}
	
	
	public MethodSpecBuilder returns(TypeName returnType) {
		builder.returns(returnType);
		return this;
	}
	
	
	public MethodSpecBuilder returns(Type returnType) {
		builder.returns(returnType);
		return this;
	}
	
	
	public MethodSpecBuilder addParameters(Iterable<ParameterSpec> parameterSpecs) {
		builder.addParameters(parameterSpecs);
		return this;
	}
	
	
	public MethodSpecBuilder addParameter(ParameterSpec parameterSpec) {
		builder.addParameter(parameterSpec);
		return this;
	}
	
	
	public MethodSpecBuilder addParameter(TypeName type, String name, Modifier... modifiers) {
		builder.addParameter(type, name, modifiers);
		return this;
	}
	
	
	public MethodSpecBuilder addParameter(Type type, String name, Modifier... modifiers) {
		builder.addParameter(type, name, modifiers);
		return this;
	}
	
	
	public MethodSpecBuilder varargs() {
		builder.varargs();
		return this;
	}
	
	
	public MethodSpecBuilder varargs(boolean varargs) {
		builder.varargs(varargs);
		return this;
	}
	
	
	public MethodSpecBuilder addExceptions(Iterable<? extends TypeName> exceptions) {
		builder.addExceptions(exceptions);
		return this;
	}
	
	
	public MethodSpecBuilder addException(TypeName exception) {
		builder.addException(exception);
		return this;
	}
	
	
	public MethodSpecBuilder addException(Type exception) {
		builder.addException(exception);
		return this;
	}
	
	
	public MethodSpecBuilder addCode(String format, Object... args) {
		builder.addCode(format, args);
		return this;
	}
	
	
	public MethodSpecBuilder addNamedCode(String format, Map<String, ?> args) {
		builder.addNamedCode(format, args);
		return this;
	}
	
	
	public MethodSpecBuilder addCode(CodeBlock codeBlock) {
		builder.addCode(codeBlock);
		return this;
	}
	
	
	public MethodSpecBuilder addComment(String format, Object... args) {
		builder.addComment(format, args);
		return this;
	}
	
	
	public MethodSpecBuilder defaultValue(String format, Object... args) {
		builder.defaultValue(format, args);
		return this;
	}
	
	
	public MethodSpecBuilder defaultValue(CodeBlock codeBlock) {
		builder.defaultValue(codeBlock);
		return this;
	}
	
	
	public MethodSpecBuilder beginControlFlow(String controlFlow, Object... args) {
		builder.beginControlFlow(controlFlow, args);
		return this;
	}
	
	
	public MethodSpecBuilder nextControlFlow(String controlFlow, Object... args) {
		builder.nextControlFlow(controlFlow, args);
		return this;
	}
	
	
	public MethodSpecBuilder endControlFlow() {
		builder.endControlFlow();
		return this;
	}
	
	
	public MethodSpecBuilder endControlFlow(String controlFlow, Object... args) {
		builder.endControlFlow(controlFlow, args);
		return this;
	}
	
	
	public MethodSpecBuilder addStatement(String format, Object... args) {
		builder.addStatement(format, args);
		return this;
	}
	
	
	public MethodSpecBuilder addStatement(CodeBlock codeBlock) {
		builder.addStatement(codeBlock);
		return this;
	}
	
	@Override
	protected MethodSpecBuilder getThis() {
		return this;
	}
	
	public final MethodSpec build() {
		return builder.build();
	}
}
