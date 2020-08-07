package com.mini.plugin.builder.javapoet;

import com.squareup.javapoet.*;
import com.squareup.javapoet.TypeSpec.Builder;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;

public final class TypeSpecBuilder extends AbstractBuilder<TypeSpecBuilder> {
	private final Builder builder;
	
	private TypeSpecBuilder(Builder builder) {
		this.builder = builder;
	}
	
	public static TypeSpecBuilder classBuilder(String name) {
		Builder builder = TypeSpec.classBuilder(name);
		return new TypeSpecBuilder(builder);
	}
	
	public static TypeSpecBuilder classBuilder(ClassName className) {
		Builder builder = TypeSpec.classBuilder(className);
		return new TypeSpecBuilder(builder);
	}
	
	public static TypeSpecBuilder interfaceBuilder(String name) {
		Builder builder = TypeSpec.interfaceBuilder(name);
		return new TypeSpecBuilder(builder);
	}
	
	public static TypeSpecBuilder interfaceBuilder(ClassName className) {
		Builder builder = TypeSpec.interfaceBuilder(className);
		return new TypeSpecBuilder(builder);
	}
	
	public static TypeSpecBuilder enumBuilder(String name) {
		Builder builder = TypeSpec.enumBuilder(name);
		return new TypeSpecBuilder(builder);
	}
	
	public static TypeSpecBuilder enumBuilder(ClassName className) {
		Builder builder = TypeSpec.enumBuilder(className);
		return new TypeSpecBuilder(builder);
	}
	
	public static TypeSpecBuilder anonymousClassBuilder(String typeArgumentsFormat, Object... args) {
		Builder builder = TypeSpec.anonymousClassBuilder(typeArgumentsFormat, args);
		return new TypeSpecBuilder(builder);
	}
	
	public static TypeSpecBuilder anonymousClassBuilder(CodeBlock typeArguments) {
		Builder builder = TypeSpec.anonymousClassBuilder(typeArguments);
		return new TypeSpecBuilder(builder);
	}
	
	public static TypeSpecBuilder annotationBuilder(String name) {
		Builder builder = TypeSpec.annotationBuilder(name);
		return new TypeSpecBuilder(builder);
	}
	
	public static TypeSpecBuilder annotationBuilder(ClassName className) {
		Builder builder = TypeSpec.annotationBuilder(className);
		return new TypeSpecBuilder(builder);
	}
	
	public TypeSpecBuilder addJavadoc(String format, Object... args) {
		builder.addJavadoc(format, args);
		return this;
	}
	
	
	public TypeSpecBuilder addJavadoc(CodeBlock block) {
		builder.addJavadoc(block);
		return this;
	}
	
	
	public TypeSpecBuilder addAnnotations(Iterable<AnnotationSpec> annotationSpecs) {
		builder.addAnnotations(annotationSpecs);
		return this;
	}
	
	
	public TypeSpecBuilder addAnnotation(AnnotationSpec annotationSpec) {
		builder.addAnnotation(annotationSpec);
		return this;
	}
	
	
	public TypeSpecBuilder addAnnotation(ClassName annotation) {
		builder.addAnnotation(annotation);
		return this;
	}
	
	
	public TypeSpecBuilder addAnnotation(Class<?> annotation) {
		builder.addAnnotation(annotation);
		return this;
	}
	
	
	public TypeSpecBuilder addModifiers(Modifier... modifiers) {
		builder.addModifiers(modifiers);
		return this;
	}
	
	
	public TypeSpecBuilder addTypeVariables(Iterable<TypeVariableName> typeVariables) {
		builder.addTypeVariables(typeVariables);
		return this;
	}
	
	
	public TypeSpecBuilder addTypeVariable(TypeVariableName typeVariable) {
		builder.addTypeVariable(typeVariable);
		return this;
	}
	
	
	public TypeSpecBuilder superclass(TypeName superclass) {
		builder.superclass(superclass);
		return this;
	}
	
	
	public TypeSpecBuilder superclass(Type superclass) {
		builder.superclass(superclass);
		return this;
	}
	
	
	public TypeSpecBuilder addSuperinterfaces(Iterable<? extends TypeName> superinterfaces) {
		builder.addSuperinterfaces(superinterfaces);
		return this;
	}
	
	
	public TypeSpecBuilder addSuperinterface(TypeName superinterface) {
		builder.addSuperinterface(superinterface);
		return this;
	}
	
	
	public TypeSpecBuilder addSuperinterface(Type superinterface) {
		builder.addSuperinterface(superinterface);
		return this;
	}
	
	
	public TypeSpecBuilder addEnumConstant(String name) {
		builder.addEnumConstant(name);
		return this;
	}
	
	
	public TypeSpecBuilder addEnumConstant(String name, TypeSpec typeSpec) {
		builder.addEnumConstant(name, typeSpec);
		return this;
	}
	
	
	public TypeSpecBuilder addFields(Iterable<FieldSpec> fieldSpecs) {
		builder.addFields(fieldSpecs);
		return this;
	}
	
	
	public TypeSpecBuilder addField(FieldSpec fieldSpec) {
		builder.addField(fieldSpec);
		return this;
	}
	
	
	public TypeSpecBuilder addField(TypeName type, String name, Modifier... modifiers) {
		builder.addField(type, name, modifiers);
		return this;
	}
	
	
	public TypeSpecBuilder addField(Type type, String name, Modifier... modifiers) {
		builder.addField(type, name, modifiers);
		return this;
	}
	
	
	public TypeSpecBuilder addStaticBlock(CodeBlock block) {
		builder.addStaticBlock(block);
		return this;
	}
	
	
	public TypeSpecBuilder addInitializerBlock(CodeBlock block) {
		builder.addInitializerBlock(block);
		return this;
	}
	
	
	public TypeSpecBuilder addMethods(Iterable<MethodSpec> methodSpecs) {
		builder.addMethods(methodSpecs);
		return this;
	}
	
	
	public TypeSpecBuilder addMethod(MethodSpec methodSpec) {
		builder.addMethod(methodSpec);
		return this;
	}
	
	
	public TypeSpecBuilder addTypes(Iterable<TypeSpec> typeSpecs) {
		builder.addTypes(typeSpecs);
		return this;
	}
	
	
	public TypeSpecBuilder addType(TypeSpec typeSpec) {
		builder.addType(typeSpec);
		return this;
	}
	
	
	public TypeSpecBuilder addOriginatingElement(Element originatingElement) {
		builder.addOriginatingElement(originatingElement);
		return this;
	}
	
	@Override
	protected TypeSpecBuilder getThis() {
		return this;
	}
	
	public final TypeSpec build() {
		return builder.build();
	}
}
