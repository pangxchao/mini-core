package com.mini.plugin.action;


import com.intellij.codeInsight.CodeInsightActionHandler;
import com.intellij.codeInsight.actions.BaseCodeInsightAction;
import com.intellij.codeInsight.generation.PsiFieldMember;
import com.intellij.ide.util.MemberChooser;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiUtil;
import com.mini.plugin.builder.psi.PsiClassBuilder;
import com.mini.plugin.builder.psi.PsiFieldBuilder;
import com.mini.plugin.builder.psi.PsiMethodBuilder;
import com.mini.plugin.builder.psi.PsiTypeBuilder;
import com.mini.plugin.util.Constants;
import com.mini.plugin.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.intellij.openapi.util.text.StringUtil.defaultIfEmpty;
import static com.intellij.psi.JavaPsiFacade.getInstance;
import static com.intellij.psi.PsiModifier.*;
import static com.intellij.psi.util.PropertyUtilBase.findGetterForField;
import static com.intellij.psi.util.PropertyUtilBase.findSetterForField;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

public class BuilderAction extends BaseCodeInsightAction implements Serializable {
	
	@Nullable
	private static PsiElement getParent(@NotNull Editor editor, @NotNull PsiFile file) {
		return Optional.of(editor.getCaretModel().getOffset())
			.map(file::findElementAt).orElse(null);
	}
	
	@Nullable
	private static PsiClass getParentToClass(@NotNull Editor editor, @NotNull PsiFile file) {
		return ofNullable(getParent(editor, file)).map(PsiElement::getParent)
			.filter(ele -> (ele instanceof PsiClass)).map(e -> (PsiClass) e)
			.filter(clazz -> !clazz.hasModifierProperty(ABSTRACT))
			.orElse(null);
	}
	
	@Override
	protected boolean isValidForFile(@NotNull Project project, @NotNull Editor editor, @NotNull PsiFile file) {
		return getParentToClass(editor, file) != null;
	}
	
	@NotNull
	@Override
	protected CodeInsightActionHandler getHandler() {
		return (project, editor, file) -> ofNullable(getParentToClass(editor, file)).ifPresent(clazz -> {
			PsiFieldMember[] members = Arrays.stream(clazz.getAllFields())
				.filter(field -> !(field.hasModifierProperty(FINAL) && field.hasInitializer()))
				.filter(field -> !field.hasModifierProperty(STATIC))
				.map(PsiFieldMember::new).toArray(PsiFieldMember[]::new);
			
			// 弹出属性选择器
			MemberChooser<PsiFieldMember> chooser = new MemberChooser<>(members, false, true, project);
			chooser.setTitle(Constants.BUILDER_TITLE);
			if (!chooser.showAndGet()) return;
			
			// 获取选择的所有字段并生成对应的方法
			Optional.ofNullable(chooser.getSelectedElements()).ifPresent(memberList -> {
				PsiElementFactory factory = getInstance(project).getElementFactory();
				ApplicationManager.getApplication().runWriteAction(() -> {
					// 生成默认构造方法
					generateBuilderConstructor(factory, clazz);
					// 生成 builder 方法
					generateBuilderMethod(factory, clazz);
					// 生成 builder copy 方法
					generateBuilderCopyMethod(factory, clazz, memberList);
					// Builder Class
					generateBuilderClass(factory, clazz, memberList);
					// 刷新代码，并格式化
					JavaCodeStyleManager.getInstance(project).shortenClassReferences(file);
					CodeStyleManager.getInstance(project).reformat(clazz);
				});
			});
		});
	}
	
	// 生成构造方法
	private static void generateBuilderConstructor(PsiElementFactory factory, PsiClass clazz) {
		if (!PsiUtil.hasDefaultConstructor(clazz) && clazz.getName() != null) {
			PsiClassBuilder.builder(clazz).addBefore(PsiMethodBuilder
					.createConstructor(factory, clazz.getName())
					.addModifiers(PRIVATE).build(),
				PsiClass::getRBrace);
		}
	}
	
	// 生成 builder 方法
	private static void generateBuilderMethod(PsiElementFactory factory, PsiClass clazz) {
		PsiClassBuilder.builder(clazz).addBefore(PsiMethodBuilder.builder(factory, "builder",
			PsiTypeBuilder.builder(factory, "%s.Builder", clazz.getName()).build())
			.addModifiers(PUBLIC, STATIC)
			.addStatementFromText("return new Builder(new %s());", clazz.getName())
			.build(), PsiClass::getRBrace);
	}
	
	// 生成 builder 方法
	private static void generateBuilderCopyMethod(PsiElementFactory factory, PsiClass clazz, List<PsiFieldMember> list) {
		PsiClassBuilder.builder(clazz).addBefore(PsiMethodBuilder.builder(factory, "builder",
			PsiTypeBuilder.builder(factory, "%s.Builder", clazz.getName()).build())
			.addModifiers(PUBLIC, STATIC)
			.addParameter(factory.createParameter("copy", factory.createType(clazz)))
			.addStatementFromText("Builder builder = new Builder(new %s());", clazz.getName())
			.forAdd(list, (builder, member) -> {
				PsiField field = member.getElement();
				PsiMethod getter = findGetterForField(field);
				builder.ifElseAdd(getter == null, it -> { //
					builder.addStatementFromText("builder.%s(copy.%s);", //
						field.getName(), field.getName());
				}, it -> {
					requireNonNull(getter);
					builder.addStatementFromText("builder.%s(copy.%s());", //
						field.getName(), getter.getName());
				});
			})
			.addStatementFromText("return builder;")
			.build(), PsiClass::getRBrace);
	}
	
	// 生成 builder 方法
	private static void generateBuilderClass(PsiElementFactory factory, PsiClass clazz, List<PsiFieldMember> list) {
		PsiTypeElement superType = Optional.ofNullable(clazz.getSuperClass())
			.map(cls -> cls.findMethodsByName("builder", true))
			.filter(methods -> methods.length > 0).map(methods -> {
				for (PsiMethod method : methods) {
					if (method.getReturnType() == null) {
						continue;
					}
					if (!method.hasModifierProperty(STATIC)) {
						continue;
					}
					PsiParameterList params = method.getParameterList();
					if (params.getParametersCount() > 1) {
						continue;
					}
					if (params.getParametersCount() <= 0) {
						return method.getReturnTypeElement();
					}
					if (params.getParameters()[0].getType() //
						.equals(clazz.getSuperClass())) {
						return method.getReturnTypeElement();
					}
				}
				return null;
			}).orElse(null);
		
		
		String infoName = StringUtil.firstLowerCase(defaultIfEmpty(clazz.getName(), "info"));
		PsiClassBuilder.builder(clazz).addBefore(PsiClassBuilder.builder(factory, "Builder")
			.addModifiers(PUBLIC, STATIC)
			// 是否继承上级 Builder
			.ifAdd(superType != null, it -> {
				Objects.requireNonNull(superType);
				it.superClass(superType);
			})
			// 生成实体类的属性信息
			.addBefore(PsiFieldBuilder.builder(factory, infoName, factory.createType(clazz))
				.addModifiers(PRIVATE, FINAL)
				.build(), PsiClass::getRBrace)
			
			// 生成构造方法
			.addBefore(PsiMethodBuilder.createConstructor(factory, "Builder")
				.addModifiers(PROTECTED).addParameter(factory.createParameter(infoName, //
					factory.createType(clazz)))
				.ifAdd(superType != null, it -> { //
					it.addStatementFromText("super(%s);", infoName);
				})
				.addStatementFromText("this.%s = %s;", infoName, infoName)
				.build(), PsiClass::getRBrace)
			
			// 字段构造器
			.forAdd(list, (builder, member) -> {
				PsiField field = member.getElement();
				PsiMethod setter = findSetterForField(field);
				String fieldName = requireNonNull(field.getName());
				// 每个字段添加一个方法
				builder.addBefore(PsiMethodBuilder.builder(factory, fieldName, factory //
					.createTypeFromText("Builder", null))
					.addModifiers(PUBLIC).addParameter(factory.createParameter(fieldName, field.getType()))
					.ifElseAdd(setter == null, it -> { //
						it.addStatementFromText("%s.%s = %s;", infoName, //
							fieldName, fieldName);
					}, it -> { //
						String setName = requireNonNull(setter).getName();
						it.addStatementFromText("%s.%s(%s);", infoName, setName, fieldName);
					})
					.addStatementFromText("return this;")
					.build(), PsiClass::getRBrace);
			})
			// build 方法
			.addBefore(PsiMethodBuilder.builder(factory, "build", factory.createType(clazz))
				.addModifiers(PUBLIC).addStatementFromText("return %s;", infoName)
				.build(), PsiClass::getRBrace)
			
			.build(), PsiClass::getRBrace);
	}
}
