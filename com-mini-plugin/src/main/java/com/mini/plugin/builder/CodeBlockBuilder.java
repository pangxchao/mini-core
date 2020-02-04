package com.mini.plugin.builder;

import com.squareup.javapoet.CodeBlock;

import java.util.Map;

public class CodeBlockBuilder extends AbstractBuilder<CodeBlockBuilder> {
	private final CodeBlock.Builder builder;
	
	private CodeBlockBuilder(CodeBlock.Builder builder) {
		this.builder = builder;
	}
	
	public static CodeBlockBuilder builder() {
		return new CodeBlockBuilder(CodeBlock.builder());
	}
	
	public final CodeBlockBuilder addNamed(String format, Map<String, ?> arguments) {
		builder.addNamed(format, arguments);
		return getThis();
	}
	
	public final CodeBlockBuilder add(String format, Object... args) {
		builder.add(format, args);
		return getThis();
	}
	
	public final CodeBlockBuilder beginControlFlow(String controlFlow, Object... args) {
		builder.beginControlFlow(controlFlow, args);
		return getThis();
	}
	
	public final CodeBlockBuilder nextControlFlow(String controlFlow, Object... args) {
		builder.nextControlFlow(controlFlow, args);
		return getThis();
	}
	
	public final CodeBlockBuilder endControlFlow() {
		builder.endControlFlow();
		return getThis();
	}
	
	public final CodeBlockBuilder endControlFlow(String controlFlow, Object... args) {
		builder.endControlFlow(controlFlow, args);
		return getThis();
	}
	
	public final CodeBlockBuilder addStatement(String format, Object... args) {
		builder.addStatement(format, args);
		return getThis();
	}
	
	public final CodeBlockBuilder addStatement(CodeBlock codeBlock) {
		builder.addStatement(codeBlock);
		return getThis();
	}
	
	public final CodeBlockBuilder add(CodeBlock codeBlock) {
		builder.add(codeBlock);
		return getThis();
	}
	
	public final CodeBlockBuilder indent() {
		builder.indent();
		return getThis();
	}
	
	public final CodeBlockBuilder unindent() {
		builder.unindent();
		return getThis();
	}
	
	public final CodeBlock build() {
		return builder.build();
	}
	
	protected CodeBlockBuilder getThis() {
		return this;
	}
}
