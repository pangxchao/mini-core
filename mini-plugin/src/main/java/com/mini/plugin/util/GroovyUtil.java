package com.mini.plugin.util;

import com.intellij.openapi.vfs.VirtualFile;
import com.mini.plugin.config.TableInfo;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;

import java.io.*;
import java.util.EventListener;

import static com.intellij.openapi.ui.Messages.showWarningDialog;
import static com.mini.plugin.util.Constants.GENERATED_ERROR;

public final class GroovyUtil implements Serializable, EventListener {
    public static void generate(String template, TableInfo tableInfo, VirtualFile file) {
        try {
            ClassLoader parentLoader = GroovyUtil.class.getClassLoader();
            GroovyClassLoader loader = new GroovyClassLoader(parentLoader);
            ((JavaFile) new GroovyShell(loader, new Binding() {{
                setVariable("tableInfo", tableInfo);
            }}).evaluate(template)).writeTo(new File(file.getPath()));
        } catch (GroovyRuntimeException | IOException e) {
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            showWarningDialog(writer.toString(), GENERATED_ERROR);
            throw ThrowsUtil.hidden(e);
        }
    }

    public static void generate1(String template, TableInfo tableInfo, VirtualFile file) {
        try {
            final ClassLoader parentLoader = GroovyUtil.class.getClassLoader();
            new GroovyShell(new GroovyClassLoader(parentLoader), new Binding() {{
                setVariable("out", new PrintWriter(new File(file.getPath())));
                setVariable("tableInfo", tableInfo);
            }}).evaluate(template);
        } catch (GroovyRuntimeException | IOException e) {
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            showWarningDialog(writer.toString(), GENERATED_ERROR);
            throw ThrowsUtil.hidden(e);
        }
    }

    public static <T> TypeName typeName(Class<T> clazz, TypeName... typeNames) {
        return ParameterizedTypeName.get(ClassName.get(clazz), typeNames);
    }

    public static TypeName typeName(ClassName className, TypeName... typeNames) {
        return ParameterizedTypeName.get(className, typeNames);
    }

}
