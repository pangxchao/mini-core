package com.mini.web.util;

import static com.mini.logger.LoggerFactory.getLogger;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Executable;
import java.lang.reflect.Type;
import java.util.EventListener;

import com.mini.logger.Logger;
import com.mini.util.reflect.MiniParameter;
import com.mini.web.annotation.RequestName;
import com.mini.web.argument.ArgumentResolver;
import com.mini.web.config.Configure;
import com.mini.web.config.Configure.UnregisteredException;
import com.mini.web.interceptor.ActionInvocation;

public final class RequestParameter implements IStatus, ISession, EventListener {
    private static final Logger logger = getLogger(Configure.class);
    private final MiniParameter param;
    private final String name;

    public RequestParameter(MiniParameter parameter) {
        RequestName n = parameter.getAnnotation(RequestName.class);
        this.name  = n == null ? parameter.getName() : n.value();
        this.param = parameter;
    }

    public Class<?> getType() {
        return param.getType();
    }

    private Object getValue(ArgumentResolver resolver, ActionInvocation invoke) throws Exception {
        return resolver.value(getName(), getType(), invoke);
    }

    public Object getValue(Configure configure, ActionInvocation invoke) {
        try {
            ArgumentResolver r = configure.getResolver(getType());
            return getValue(r, invoke);
        } catch (NumberFormatException e) {
            return (byte) 0;
        } catch (UnregisteredException e) {
            logger.warn(e.getMessage());
        } catch (Exception ignored) {
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object object) {
        if (!(object instanceof RequestParameter)) {
            return param.equals(object);
        }
        RequestParameter p = (RequestParameter) object;
        return param.equals(p.param);
    }

    public int hashCode() {
        return param.hashCode();
    }

    @SuppressWarnings("unused")
    public boolean isNamePresent() {
        return param.isNamePresent();
    }

    public String toString() {
        return param.toString() + "@" + name;
    }

    @SuppressWarnings("unused")
    public Executable getDeclaringExecutable() {
        return param.getDeclaringExecutable();
    }

    @SuppressWarnings("unused")
    public int getModifiers() {
        return param.getModifiers();
    }

    @SuppressWarnings("unused")
    public Type getParameterizedType() {
        return param.getParameterizedType();
    }


    @SuppressWarnings("unused")
    public AnnotatedType getAnnotatedType() {
        return param.getAnnotatedType();
    }

    @SuppressWarnings("unused")
    public boolean isImplicit() {
        return param.isImplicit();
    }

    @SuppressWarnings("unused")
    public boolean isSynthetic() {
        return param.isSynthetic();
    }

    @SuppressWarnings("unused")
    public boolean isVarArgs() {
        return param.isVarArgs();
    }

    @SuppressWarnings("unused")
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        return param.getAnnotation(annotationClass);
    }

    @SuppressWarnings("unused")
    public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
        return param.getAnnotationsByType(annotationClass);
    }

    @SuppressWarnings("unused")
    public Annotation[] getDeclaredAnnotations() {
        return param.getDeclaredAnnotations();
    }

    @SuppressWarnings("unused")
    public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass) {
        return param.getDeclaredAnnotation(annotationClass);
    }

    @SuppressWarnings("unused")
    public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass) {
        return param.getDeclaredAnnotationsByType(annotationClass);
    }

    @SuppressWarnings("unused")
    public Annotation[] getAnnotations() {
        return param.getAnnotations();
    }
}
