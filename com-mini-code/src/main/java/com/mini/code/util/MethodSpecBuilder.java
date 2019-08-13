package com.mini.code.util;

import com.mini.code.Configure.ClassInfo;
import com.mini.jdbc.SQLBuilder;
import com.mini.util.StringUtil;
import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static com.mini.util.StringUtil.*;
import static com.squareup.javapoet.MethodSpec.Builder;
import static com.squareup.javapoet.MethodSpec.methodBuilder;

public final class MethodSpecBuilder {
    private final Builder builder;

    public MethodSpecBuilder(String name) {
        builder = methodBuilder(name);
    }

    /**
     * 将所有主键字段添加到方法参数上
     * <P>添加时会将参数添加到JavaDoc</P>
     * @param fieldList 所有 字段信息
     * @param info      生成的类信息
     * @return 当前对象
     */
    public MethodSpecBuilder addMapperStatement(List<Util.FieldInfo> fieldList, ClassInfo info) {
        builder.addStatement("$T $L = new $T()", info.beanClass, firstLowerCase(info.beanName), info.beanClass);
        for (Util.FieldInfo fieldInfo : fieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = StringUtil.toJavaName(fieldInfo.getFieldName(), false);
            builder.addComment(fieldInfo.getRemarks());
            builder.addStatement("$L.set$L(rs.get$L($T.$L))", //
                    firstLowerCase(info.beanName), //
                    firstUpperCase(name), //
                    firstUpperCase(fieldInfo.getTypeClass().getSimpleName()), //
                    info.beanClass, //
                    db_name); //
        }
        builder.addStatement("return $L", firstLowerCase(info.beanName));
        return this;
    }

    /**
     * 将所有主键字段添加到方法参数上
     * <P>添加时会将参数添加到JavaDoc</P>
     * @param fieldList 所有 字段信息
     * @param info      生成的类信息
     * @return 当前对象
     */
    public MethodSpecBuilder addMapperSqlStatement(List<Util.FieldInfo> fieldList, ClassInfo info) {
        for (Util.FieldInfo fieldInfo : fieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            builder.addCode("// $L \n", fieldInfo.getRemarks());
            builder.addStatement("builder.select($T.$L)", info.beanClass, db_name);
        }
        builder.addCode("// $L \n", "表名称");
        builder.addStatement("builder.from($T.TABLE)", info.beanClass);
        return this;
    }

    /**
     * 将所有主键字段添加到方法参数上
     * <P>添加时会将参数添加到JavaDoc</P>
     * @param pkFieldList 所有主键字段信息
     * @param addJavaDoc  true-添加方法文档注释
     * @return 当前对象
     */
    public MethodSpecBuilder addParameter(List<Util.FieldInfo> pkFieldList, boolean addJavaDoc) {
        for (Util.FieldInfo fieldInfo : pkFieldList) {
            String name = toJavaName(fieldInfo.getFieldName(), false);
            builder.addParameter(fieldInfo.getTypeClass(), name);
            // 处理文档注释
            if (!addJavaDoc) continue;
            builder.addJavadoc("@param $N $N \n", name, fieldInfo.getRemarks());
        }
        return this;
    }

    /**
     * 将所有字段添加到insert方法的方法体中
     * @param fieldList 所有的字段信息
     * @param info      生成的类信息
     * @return 当前对象
     */
    public MethodSpecBuilder addInsertStatement(List<Util.FieldInfo> fieldList, ClassInfo info) {
        builder.addCode("return execute(new $T() {{ \n", SQLBuilder.class);
        builder.addStatement("\tinsert_into($T.TABLE)", info.beanClass);
        for (Util.FieldInfo fieldInfo : fieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), true);
            builder.addCode("\t// $L \n", fieldInfo.getRemarks());
            builder.addStatement("\tvalues($T.$L)", info.beanClass, db_name);
            builder.addStatement("\tparams($L.get$L())", firstLowerCase(info.beanName), name);
        }
        builder.addStatement("}})");
        return this;
    }

    /**
     * 将所有字段和所有主键字段添加到update方法的方法体中
     * @param fieldList   所有的字段信息
     * @param pkFieldList 所有主键字段信息
     * @param info        生成的类信息
     * @return 当前对象
     */
    public MethodSpecBuilder addUpdateStatement(List<Util.FieldInfo> fieldList, List<Util.FieldInfo> pkFieldList, ClassInfo info) {
        builder.addCode("return execute(new $T() {{ \n", SQLBuilder.class);
        builder.addStatement("\tupdate($T.TABLE)", info.beanClass);
        for (Util.FieldInfo fieldInfo : fieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), true);
            builder.addCode("\t// $L \n", fieldInfo.getRemarks());
            builder.addStatement("\tset($S, $T.$L)", "%s = ?", info.beanClass, db_name);
            builder.addStatement("\tparams($L.get$L())", firstLowerCase(info.beanName), name);
        }
        for (Util.FieldInfo fieldInfo : pkFieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), true);
            builder.addCode("\t// $L \n", fieldInfo.getRemarks());
            builder.addStatement("\twhere($S, $T.$L)", "%s = ?", info.beanClass, db_name);
            builder.addStatement("\tparams($L.get$L())", firstLowerCase(info.beanName), name);
        }
        builder.addStatement("}})");
        return this;
    }

    /**
     * 将所有主键字段添加到delete方法体中
     * @param pkFieldList 所有主键字段信息
     * @param info        生成的类信息
     * @return 当前对象
     */
    public MethodSpecBuilder addDeleteStatement(List<Util.FieldInfo> pkFieldList, ClassInfo info) {
        builder.addCode("return execute(new $T() {{ \n", SQLBuilder.class);
        builder.addStatement("\tdelete().from($T.TABLE)", info.beanClass);
        for (Util.FieldInfo fieldInfo : pkFieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), true);
            builder.addCode("\t// $L \n", fieldInfo.getRemarks());
            builder.addStatement("\twhere($S, $T.$L)", "%s = ?", info.beanClass, db_name);
            builder.addStatement("\tparams($L.get$L())", firstLowerCase(info.beanName), name);
        }
        builder.addStatement("}})");
        return this;
    }

    /**
     * 将所有主键字段添加到deleteById方法体中
     * @param pkFieldList 所有主键字段信息
     * @param info        生成的类信息
     * @return 当前对象
     */
    public MethodSpecBuilder addDeleteByIdStatement(List<Util.FieldInfo> pkFieldList, ClassInfo info) {
        builder.addCode("return execute(new $T() {{ \n", SQLBuilder.class);
        builder.addStatement("\tdelete().from($T.TABLE)", info.beanClass);
        for (Util.FieldInfo fieldInfo : pkFieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), true);
            builder.addCode("\t// $L \n", fieldInfo.getRemarks());
            builder.addStatement("\twhere($S, $T.$L)", "%s = ?", info.beanClass, db_name);
            builder.addStatement("\tparams($N)", firstLowerCase(name));
        }
        builder.addStatement("}})");
        return this;
    }

    /**
     * 将所有主键字段添加到queryById方法体中
     * @param pkFieldList 所有主键字段信息
     * @param info        生成的类信息
     * @return 当前对象
     */
    public MethodSpecBuilder addQueryByIdStatement(List<Util.FieldInfo> pkFieldList, ClassInfo info) {
        builder.addCode("return queryOne(new $T() {{ \n", SQLBuilder.class);
        builder.addStatement("\t$T.init(this)", info.mapperClass);
        for (Util.FieldInfo fieldInfo : pkFieldList) {
            String db_name = fieldInfo.getFieldName().toUpperCase();
            String name = toJavaName(fieldInfo.getFieldName(), true);
            builder.addCode("\t// $L \n", fieldInfo.getRemarks());
            builder.addStatement("\twhere($S, $T.$L)", "%s = ?", info.beanClass, db_name);
            builder.addStatement("\tparams($N)", firstLowerCase(name));
        }
        builder.addStatement("}}, get$L())", info.mapperName);
        return this;
    }

    public MethodSpecBuilder addJavadoc(String format, Object... args) {
        builder.addJavadoc(format, args);
        return this;
    }

    public MethodSpecBuilder addJavadoc(CodeBlock block) {
        builder.addJavadoc(block);
        return this;
    }

    public MethodSpecBuilder addAnnotations(Iterable<AnnotationSpec> annotationSpecs) {
        builder.addAnnotations(annotationSpecs);
        return this;
    }

    public MethodSpecBuilder addAnnotation(AnnotationSpec annotationSpec) {
        builder.addAnnotation(annotationSpec);
        return this;
    }

    public MethodSpecBuilder addAnnotation(ClassName annotation) {
        builder.addAnnotation(annotation);
        return this;
    }

    public MethodSpecBuilder addAnnotation(Class<?> annotation) {
        builder.addAnnotation(annotation);
        return this;
    }

    public MethodSpecBuilder addModifiers(Modifier... modifiers) {
        builder.addModifiers(modifiers);
        return this;
    }

    public MethodSpecBuilder addModifiers(Iterable<Modifier> modifiers) {
        builder.addModifiers(modifiers);
        return this;
    }

    public MethodSpecBuilder addTypeVariables(Iterable<TypeVariableName> typeVariables) {
        builder.addTypeVariables(typeVariables);
        return this;
    }

    public MethodSpecBuilder addTypeVariable(TypeVariableName typeVariable) {
        builder.addTypeVariable(typeVariable);
        return this;
    }

    public MethodSpecBuilder returns(TypeName returnType) {
        builder.returns(returnType);
        return this;
    }

    public MethodSpecBuilder returns(Type returnType) {
        builder.returns(returnType);
        return this;
    }

    public MethodSpecBuilder addParameters(Iterable<ParameterSpec> parameterSpecs) {
        builder.addParameters(parameterSpecs);
        return this;
    }

    public MethodSpecBuilder addParameter(ParameterSpec parameterSpec) {
        builder.addParameter(parameterSpec);
        return this;
    }

    public MethodSpecBuilder addParameter(TypeName type, String name, Modifier... modifiers) {
        builder.addParameter(type, name, modifiers);
        return this;
    }

    public MethodSpecBuilder addParameter(Type type, String name, Modifier... modifiers) {
        builder.addParameter(type, name, modifiers);
        return this;
    }

    public MethodSpecBuilder varargs() {
        builder.varargs();
        return this;
    }

    public MethodSpecBuilder varargs(boolean varargs) {
        builder.varargs(varargs);
        return this;
    }

    public MethodSpecBuilder addExceptions(Iterable<? extends TypeName> exceptions) {
        builder.addExceptions(exceptions);
        return this;
    }

    public MethodSpecBuilder addException(TypeName exception) {
        builder.addException(exception);
        return this;
    }

    public MethodSpecBuilder addException(Type exception) {
        builder.addException(exception);
        return this;
    }

    public MethodSpecBuilder addCode(String format, Object... args) {
        builder.addCode(format, args);
        return this;
    }

    public MethodSpecBuilder addNamedCode(String format, Map<String, ?> args) {
        builder.addNamedCode(format, args);
        return this;
    }

    public MethodSpecBuilder addCode(CodeBlock codeBlock) {
        builder.addCode(codeBlock);
        return this;
    }

    public MethodSpecBuilder addComment(String format, Object... args) {
        builder.addComment(format, args);
        return this;
    }

    public MethodSpecBuilder defaultValue(String format, Object... args) {
        builder.defaultValue(format, args);
        return this;
    }

    public MethodSpecBuilder defaultValue(CodeBlock codeBlock) {
        builder.defaultValue(codeBlock);
        return this;
    }

    public MethodSpecBuilder beginControlFlow(String controlFlow, Object... args) {
        builder.beginControlFlow(controlFlow, args);
        return this;
    }

    public MethodSpecBuilder nextControlFlow(String controlFlow, Object... args) {
        builder.nextControlFlow(controlFlow, args);
        return this;
    }

    public MethodSpecBuilder endControlFlow() {
        builder.endControlFlow();
        return this;
    }

    public MethodSpecBuilder endControlFlow(String controlFlow, Object... args) {
        builder.endControlFlow(controlFlow, args);
        return this;
    }

    public MethodSpecBuilder addStatement(String format, Object... args) {
        builder.addStatement(format, args);
        return this;
    }

    public MethodSpecBuilder addStatement(CodeBlock codeBlock) {
        builder.addStatement(codeBlock);
        return this;
    }

    public MethodSpec build() {
        return builder.build();
    }
}
