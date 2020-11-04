package com.mini.plugin.builder;


import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import static com.intellij.psi.util.PsiUtil.setModifierProperty;
import static java.util.Arrays.stream;

public class PsiFieldBuilder extends PsiAbstractBuilder<PsiFieldBuilder, PsiField> {

    private final PsiField field;

    private PsiFieldBuilder(PsiField field) {
        super(field.getProject());
        this.field = field;
    }

    public static PsiFieldBuilder builder(PsiField field) {
        return new PsiFieldBuilder(field);
    }

    public static PsiFieldBuilder builder(PsiElementFactory factory, String name, PsiType type) {
        return builder(factory.createField(name, type));
    }

    public PsiFieldBuilder addAnnotation(String name) {
        PsiModifierList list = field.getModifierList();
        if (list == null) return this;
        list.addAnnotation(name);
        return this;
    }

    public PsiFieldBuilder addAnnotation(PsiAnnotation element) {
        PsiModifierList list = field.getModifierList();
        if (list == null) return this;
        list.add(element);
        return this;
    }

    public PsiFieldBuilder addModifiers(@NotNull @PsiModifier.ModifierConstant String... property) {
        stream(property).forEach(prop -> setModifierProperty(field, prop, true));
        return this;
    }

    public PsiFieldBuilder setInitializer(PsiExpression initializer) {
        field.setInitializer(initializer);
        return this;
    }

    @Override
    protected PsiFieldBuilder getThis() {
        return this;
    }

    @Override
    public PsiField build() {
        return field;
    }
}
