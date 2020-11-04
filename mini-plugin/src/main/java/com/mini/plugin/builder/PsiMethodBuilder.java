package com.mini.plugin.builder;


import com.intellij.psi.*;
import com.intellij.psi.util.PsiUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class PsiMethodBuilder extends PsiAbstractBuilder<PsiMethodBuilder, PsiMethod> {
    private final PsiMethod method;

    private PsiMethodBuilder(PsiMethod method) {
        super(method.getProject());
        this.method = method;
    }

    public static PsiMethodBuilder builder(PsiMethod method) {
        return new PsiMethodBuilder(method);
    }

    public static PsiMethodBuilder createConstructor(PsiElementFactory factory, String name) {
        return builder(factory.createConstructor(name));
    }

    public static PsiMethodBuilder builder(PsiElementFactory factory, String name, PsiType returnType) {
        return builder(factory.createMethod(name, returnType));
    }

    public static PsiMethodBuilder builder(PsiElementFactory factory, String name, PsiType returnType, PsiElement context) {
        return builder(factory.createMethod(name, returnType, context));
    }

    public static PsiMethodBuilder builderFromText(PsiElementFactory factory, String format, Object... args) {
        return builder(factory.createMethodFromText(String.format(format, args), null));
    }

    public PsiMethodBuilder addModifiers(@NotNull @PsiModifier.ModifierConstant String... property) {
        Arrays.stream(property).forEach(prop -> PsiUtil.setModifierProperty(method, prop, true));
        return this;
    }

    public PsiMethodBuilder addAnnotation(String name) {
        PsiModifierList list = method.getModifierList();
        list.addAnnotation(name);
        return this;
    }

    public PsiMethodBuilder addAnnotation(PsiAnnotation element) {
        PsiModifierList list = method.getModifierList();
        list.add(element);
        return this;
    }

    public PsiMethodBuilder addTypeParameter(PsiTypeParameter element) {
        PsiTypeParameterList list = method.getTypeParameterList();
        Objects.requireNonNull(list).add(element);
        return this;
    }

    public PsiMethodBuilder addTypeParameterFromText(String format, Object... args) {
        return addTypeParameter(factory.createTypeParameterFromText(
                String.format(format, args), null));
    }

    public PsiMethodBuilder addParameter(PsiParameter element) {
        method.getParameterList().add(element);
        return this;
    }

    public PsiMethodBuilder addParameterFromText(String format, Object... args) {
        return addParameter(factory.createParameterFromText(
                String.format(format, args), null));
    }

    public PsiMethodBuilder addStatement(PsiStatement element) {
        PsiCodeBlock body = method.getBody();
        if (body == null) return this;
        body.add(element);
        return this;
    }

    public PsiMethodBuilder addStatementFromText(String format, Object... args) {
        return addStatement(factory.createStatementFromText( //
                String.format(format, args), null));
    }

    @Override
    protected PsiMethodBuilder getThis() {
        return this;
    }

    public PsiMethod build() {
        return method;
    }
}

