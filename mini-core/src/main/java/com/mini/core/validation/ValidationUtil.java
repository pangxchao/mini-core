package com.mini.core.validation;

import com.google.inject.Injector;
import com.mini.core.util.holder.ClassHolder;
import com.mini.core.util.reflect.MiniParameter;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.*;

import static java.util.Optional.ofNullable;

public final class ValidationUtil implements EventListener {

    @SuppressWarnings("unchecked")
    public static <A extends Annotation, T> void validate(Injector injector, A annotation, T value) {
        ofNullable(annotation).map(A::annotationType).map(clazz -> { //
            return clazz.getAnnotation(Constraint.class);
        }).ifPresent(constraint -> {
            var v = injector.getInstance(constraint.value());
            ((ConstraintValidation<A>) v).validate(annotation, value);
        });
    }

    public static <T> void validate(Injector injector, MiniParameter parameter, T value) {
        Optional.ofNullable(parameter)
                .map(MiniParameter::getAnnotations)
                .stream()
                .flatMap(Arrays::stream)
                .forEach(f -> validate(injector, f, value));
    }

    public static <T> void validate(Injector injector, Parameter parameter, T value) {
        Optional.ofNullable(parameter)
                .map(Parameter::getAnnotations)
                .stream()
                .flatMap(Arrays::stream)
                .forEach(f -> validate(injector, f, value));
    }

    public static <T> void validate(Injector injector, Field field, T value) {
        Optional.ofNullable(field)
                .map(AccessibleObject::getAnnotations)
                .stream()
                .flatMap(Arrays::stream)
                .forEach(f -> validate(injector, f, value));
    }

    public static <T> void validate(Injector injector, Class<T> type, Object object) {
        Optional.ofNullable(ClassHolder.create(type))
                .map(it -> it.getFields().values())
                .stream()
                .flatMap(Collection::stream)
                .filter(h -> Objects.nonNull(h.getField()))
                .forEach(h -> validate(injector, h.getField(), h.getValue(object)));
    }

    public static <T> void validate(Injector injector, T object) {
        validate(injector, object.getClass(), object);
    }
}
