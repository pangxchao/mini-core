package com.mini.plugin.builder;

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

    public static PsiClassBuilder builderFromText(@NotNull PsiElementFactory factory, @NotNull String format, Object... args) {
        return builder(factory.createClassFromText(String.format(format, args), null));
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

    public PsiClassBuilder addTypeParameterFromText(String format, Object... args) {
        return addTypeParameter(factory.createTypeParameterFromText(
                String.format(format, args), null));
    }

    @SuppressWarnings({"DuplicatedCode", "UnusedReturnValue"})
    public PsiClassBuilder superClass(PsiClass element) {
        PsiReferenceList list = clazz.getExtendsList();
        if (list == null) return this;
        if (!(list.getPrevSibling() instanceof PsiKeyword)) {
            clazz.addBefore(factory.createKeyword("extends"), list);
        }
        list.add(element);
        return this;
    }

    @SuppressWarnings({"DuplicatedCode", "UnusedReturnValue"})
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
