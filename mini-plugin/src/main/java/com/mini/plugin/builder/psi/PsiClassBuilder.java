package com.mini.plugin.builder.psi;

import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import static com.intellij.psi.util.PsiUtil.setModifierProperty;
import static java.util.Arrays.stream;

public final class PsiClassBuilder extends PsiAbstractBuilder<PsiClassBuilder, PsiClass> {
	private final PsiClass clazz;
	
	private PsiClassBuilder(PsiClass clazz) {
		super(clazz.getProject());
		this.clazz = clazz;
	}
	
	public static PsiClassBuilder builder(@NotNull PsiClass psiClass) {
		return new PsiClassBuilder(psiClass);
	}
	
	public static PsiClassBuilder builder(@NotNull PsiElementFactory factory, @NotNull String name) {
		return builder(factory.createClass(name));
	}
	
	public static PsiClassBuilder builder(@NotNull PsiElementFactory factory, @NotNull String text, PsiElement context) {
		return builder(factory.createClassFromText(text, context));
	}
	
	public PsiClassBuilder addAnnotation(String name) {
		PsiModifierList list = clazz.getModifierList();
		if (list == null) return this;
		list.addAnnotation(name);
		return this;
	}
	
	public PsiClassBuilder addAnnotation(PsiAnnotation element) {
		PsiModifierList list = clazz.getModifierList();
		if (list == null) return this;
		list.add(element);
		return this;
	}
	
	public PsiClassBuilder addModifiers(@NotNull @PsiModifier.ModifierConstant String... property) {
		stream(property).forEach(prop -> setModifierProperty(clazz, prop, true));
		return this;
	}
	
	public PsiClassBuilder addTypeParameter(PsiTypeParameter element) {
		PsiTypeParameterList list = clazz.getTypeParameterList();
		if (list == null) return this;
		list.add(element);
		return this;
	}
	
	public PsiClassBuilder superClass(PsiClass element) {
		PsiReferenceList list = clazz.getExtendsList();
		if (list == null) return this;
		if (!(list.getPrevSibling() instanceof PsiKeyword)) {
			clazz.addBefore(factory.createKeyword("extends"), list);
		}
		list.add(element);
		return this;
	}
	
	public PsiClassBuilder superClass(PsiTypeElement element) {
		PsiReferenceList list = clazz.getExtendsList();
		if (list == null) return this;
		if (!(list.getPrevSibling() instanceof PsiKeyword)) {
			clazz.addBefore(factory.createKeyword("extends"), list);
		}
		
		list.add(element);
		return this;
	}
	
	public PsiClassBuilder addSuperInterface(PsiClass element) {
		PsiReferenceList list = clazz.getImplementsList();
		if (list == null) return this;
		if (!(list.getPrevSibling() instanceof PsiKeyword)) {
			clazz.addBefore(factory.createKeyword("implements"), list);
		}
		list.add(element);
		return this;
	}
	
	public PsiClassBuilder addField(PsiField element) {
		this.addBefore(element, PsiClass::getRBrace);
		return this;
	}
	
	public PsiClassBuilder addStaticBlock(PsiCodeBlock element) {
		this.add(element);
		return this;
	}
	
	public PsiClassBuilder addInitializerBlock(PsiCodeBlock element) {
		this.add(element);
		return this;
	}
	
	public PsiClassBuilder addMethod(PsiMethod element) {
		this.addBefore(element, PsiClass::getRBrace);
		return this;
	}
	
	public PsiClassBuilder addType(PsiClass element) {
		this.addBefore(element, PsiClass::getRBrace);
		return this;
	}
	
	@Override
	protected final PsiClassBuilder getThis() {
		return this;
	}
	
	public PsiClass build() {
		return clazz;
	}
}
