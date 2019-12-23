package com.mini.code.util;

import com.squareup.javapoet.*;
import com.squareup.javapoet.FieldSpec.Builder;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;


public final class FieldSpecBuilder extends AbstractBuilder<FieldSpecBuilder> {
	private final Builder builder;

	private FieldSpecBuilder(Builder builder) {
		this.builder = builder;
	}

	public static FieldSpecBuilder builder(TypeName type, String name, Modifier... modifiers) {
		Builder builder = FieldSpec.builder(type, name, modifiers);
		return new FieldSpecBuilder(builder);
	}

	public static FieldSpecBuilder builder(Type type, String name, Modifier... modifiers) {
		Builder builder = FieldSpec.builder(type, name, modifiers);
		return new FieldSpecBuilder(builder);
	}


	public FieldSpecBuilder addJavadoc(String format, Object... args) {
		builder.addJavadoc(format, args);
		return this;
	}


	public FieldSpecBuilder addJavadoc(CodeBlock block) {
		builder.addJavadoc(block);
		return this;
	}


	public FieldSpecBuilder addAnnotations(Iterable<AnnotationSpec> annotationSpecs) {
		builder.addAnnotations(annotationSpecs); return this;
	}


	public FieldSpecBuilder addAnnotation(AnnotationSpec annotationSpec) {
		builder.addAnnotation(annotationSpec);
		return this;
	}


	public FieldSpecBuilder addAnnotation(ClassName annotation) {
		builder.addAnnotation(annotation);
		return this;
	}


	public FieldSpecBuilder addAnnotation(Class<?> annotation) {
		builder.addAnnotation(annotation);
		return this;
	}


	public FieldSpecBuilder addModifiers(Modifier... modifiers) {
		builder.addModifiers(modifiers);
		return this;
	}


	public FieldSpecBuilder initializer(String format, Object... args) {
		builder.initializer(format, args);
		return this;
	}


	public FieldSpecBuilder initializer(CodeBlock codeBlock) {
		builder.initializer(codeBlock);
		return this;
	}

	@Override
	protected FieldSpecBuilder getThis() {
		return this;
	}

	public final FieldSpec build() {
		return builder.build();
	}
}
