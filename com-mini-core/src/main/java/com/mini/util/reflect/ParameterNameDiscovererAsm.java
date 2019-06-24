package com.mini.util.reflect;


//import org.objectweb.asm.*;

import javax.annotation.Nonnull;
//import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//import java.util.Arrays;

public class ParameterNameDiscovererAsm implements ParameterNameDiscoverer {

    @Nonnull
    @Override
    public String[] getParameterNames(Method method) {
        return getParameterNames((Executable)method);
    }

    @Nonnull
    @Override
    public String[] getParameterNames(Constructor<?> constructor) {
        return getParameterNames((Executable)constructor);
    }

    @Nonnull
    private String[] getParameterNames(final Executable executable) {
        //try {
        //    // 获取方法类型数组
        //    Class<?>[] parameterTypes = executable.getParameterTypes();
        //    if (parameterTypes == null || parameterTypes.length == 0) {
        //        return new String[0];
        //    }
        //    // 创建类型
        //    Type[] types = new Type[parameterTypes.length];
        //    for (int i = 0; i < parameterTypes.length; i++) {
        //        types[i] = Type.getType(parameterTypes[i]);
        //    }
        //    String[] parameterNames = new String[parameterTypes.length];
        //    new ClassReader(executable.getDeclaringClass().getName()).accept(new ClassVisitor(Opcodes.ASM7) {
        //        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        //            if (!executable.getName().equals(name) || !Arrays.equals(Type.getArgumentTypes(desc), types)) {
        //                return super.visitMethod(access, name, desc, signature, exceptions);
        //            }
        //            return new MethodVisitor(Opcodes.ASM7) {
        //                public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        //                    // 非静态成员方法的第一个参数是this
        //                    if (Modifier.isStatic(executable.getModifiers())) {
        //                        parameterNames[index] = name;
        //                    } else if (index > 0) {
        //                        parameterNames[index - 1] = name;
        //                    }
        //                }
        //            };
        //        }
        //    }, 0);
        //    return parameterNames;
        //} catch (IOException ignored) {}
        return new String[0];
    }
}
