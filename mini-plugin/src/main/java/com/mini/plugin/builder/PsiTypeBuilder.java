package com.mini.plugin.builder;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiType;

public final class PsiTypeBuilder extends AbstractBuilder<PsiTypeBuilder> {
    private final PsiType type;

    private PsiTypeBuilder(PsiType type) {
        this.type = type;
    }

    public static PsiTypeBuilder builder(PsiType type) {
        return new PsiTypeBuilder(type);
    }

    public static PsiTypeBuilder builder(PsiElementFactory factory, String text, Object... args) {
        return builder(factory.createTypeFromText(String.format(text, args), null));
    }

    public static PsiTypeBuilder builder(PsiElementFactory factory, String text, PsiElement context, Object... args) {
        return builder(factory.createTypeFromText(String.format(text, args), context));
    }

    @Override
    protected PsiTypeBuilder getThis() {
        return this;
    }

    public PsiType build() {
        return type;
    }
}
