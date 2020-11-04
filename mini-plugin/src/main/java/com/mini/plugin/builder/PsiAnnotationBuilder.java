package com.mini.plugin.builder;
import com.intellij.psi.PsiAnnotation;

public class PsiAnnotationBuilder extends PsiAbstractBuilder<PsiAnnotationBuilder, PsiAnnotation> {
    private final PsiAnnotation annotation;

    private PsiAnnotationBuilder(PsiAnnotation annotation) {
        super(annotation.getProject());
        this.annotation = annotation;
    }

    @Override
    protected PsiAnnotationBuilder getThis() {
        return this;
    }

    @Override
    public PsiAnnotation build() {
        return annotation;
    }
}
