package com.mini.util.reflect;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

public final class MiniParameter {
    @Nonnull
    private final Parameter parameter;
    private final String name;
    private Object value;

    public MiniParameter(@Nonnull Parameter parameter, String name) {
        this.parameter = parameter;
        this.name      = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean equals(Object object) {
        if (object instanceof MiniParameter) {
            return parameter.equals(((MiniParameter) object).parameter);
        }
        if (object instanceof Parameter) {
            return parameter.equals(object);
        }
        return false;
    }

    public int hashCode() {
        return this.parameter.hashCode();
    }

    public boolean isNamePresent() {
        return this.parameter.isNamePresent();
    }

    public String toString() {
        return this.parameter.toString();
    }

    public Executable getDeclaringExecutable() {
        return this.parameter.getDeclaringExecutable();
    }

    public int getModifiers() {
        return this.parameter.getModifiers();
    }

    public String getName() {
        return this.name;
    }

    public Type getParameterizedType() {
        return this.parameter.getParameterizedType();
    }

    public Class<?> getType() {
        return this.parameter.getType();
    }

    public AnnotatedType getAnnotatedType() {
        return this.parameter.getAnnotatedType();
    }

    public boolean isImplicit() {
        return this.parameter.isImplicit();
    }

    public boolean isSynthetic() {
        return this.parameter.isSynthetic();
    }

    public boolean isVarArgs() {
        return this.parameter.isVarArgs();
    }

    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return this.parameter.getAnnotation(annotationClass);
    }

    public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
        return this.parameter.getAnnotationsByType(annotationClass);
    }

    public Annotation[] getDeclaredAnnotations() {
        return this.parameter.getDeclaredAnnotations();
    }

    public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass) {
        return this.parameter.getDeclaredAnnotation(annotationClass);
    }

    public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass) {
        return this.parameter.getDeclaredAnnotationsByType(annotationClass);
    }

    public Annotation[] getAnnotations() {
        return this.parameter.getAnnotations();
    }
}
