package com.mini.util.reflect;


import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static javassist.bytecode.LocalVariableAttribute.tag;

public class ParameterNameDiscovererJavassist implements ParameterNameDiscoverer {

    @Nonnull
    @Override
    public String[] getParameterNames(Method method) {
        try {
            // 该方法没有参数的时候返回空的字符串数组
            Class<?>[] types = method.getParameterTypes();
            if (types.length == 0) return new String[0];

            // 获取 ClassPool 对象
            ClassPool pool = ClassPool.getDefault();

            // 获取CtClass的参数类型数组
            CtClass[] parameterTypes = new CtClass[types.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                parameterTypes[i] = pool.get(types[i].getName());
            }

            // 获取方法对象
            CtClass clazz = pool.get(method.getDeclaringClass().getName());
            CtMethod ctMethod = clazz.getDeclaredMethod(method.getName(), parameterTypes);
            if (ctMethod == null) return new String[0];

            // 获取方法信息
            MethodInfo methodInfo = ctMethod.getMethodInfo();
            return getParameterNames(parameterTypes, methodInfo, method.getModifiers());
        } catch (NotFoundException ignored) {}
        return new String[0];
    }

    @Nonnull
    @Override
    public String[] getParameterNames(Constructor<?> constructor) {
        try {
            // 该方法没有参数的时候返回空的字符串数组
            Class<?>[] types = constructor.getParameterTypes();
            if (types.length == 0) return new String[0];

            // 获取 ClassPool 对象
            ClassPool pool = ClassPool.getDefault();

            // 获取CtClass的参数类型数组
            CtClass[] parameterTypes = new CtClass[types.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                parameterTypes[i] = pool.get(types[i].getName());
            }

            // 获取方法对象
            CtClass clazz = pool.get(constructor.getDeclaringClass().getName());
            CtConstructor ctConstructor = clazz.getDeclaredConstructor(parameterTypes);
            if (ctConstructor == null) return new String[0];

            // 获取方法信息
            MethodInfo methodInfo = ctConstructor.getMethodInfo();
            return getParameterNames(parameterTypes, methodInfo, constructor.getModifiers());
        } catch (NotFoundException ignored) {}
        return new String[0];
    }

    @Nonnull
    private String[] getParameterNames(CtClass[] parameterTypes, MethodInfo methodInfo, int modifiers) {
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(tag);

        // 获取方法名称列表
        int position = Modifier.isStatic(modifiers) ? 0 : 1;
        String[] parameterNames = new String[parameterTypes.length];
        for (int i = 0, len = parameterNames.length; i < len; i++) {
            parameterNames[i] = attr.variableName(i + position);
        }
        return parameterNames;
    }


}
