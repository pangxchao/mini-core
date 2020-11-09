package com.mini.plugin.handler;

import com.intellij.codeInsight.generation.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import com.mini.plugin.builder.PsiClassBuilder;
import com.mini.plugin.builder.PsiFieldBuilder;
import com.mini.plugin.builder.PsiMethodBuilder;
import com.mini.plugin.builder.PsiTypeBuilder;
import com.mini.plugin.extension.StringKt;
import com.mini.plugin.util.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.intellij.codeInsight.hint.HintManager.getInstance;
import static com.intellij.psi.JavaPsiFacade.getInstance;
import static com.intellij.psi.PsiModifier.*;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.StringUtils.isNotBlank;

public final class BuilderHandler extends GenerateMembersHandlerBase /* implements LanguageCodeInsightActionHandler*/ {

    private static class BuilderInfo {
        final String builderImplClassName;
        final PsiClass superBuilderClass;
        final PsiElementFactory factory;
        final String builderClassName;
        final String entityClassName;
        final PsiClass superClass;
        final PsiClass psiClass;
        final String entityName;
        final PsiType entityType;

        public BuilderInfo(@NotNull PsiElementFactory factory, @NotNull PsiClass psiClass) {
            this.entityClassName = StringUtil.defaultIfEmpty(psiClass.getName(), "");
            this.entityName = StringKt.firstLowerCase(this.entityClassName);
            this.builderImplClassName = entityClassName + "BuilderImpl";
            this.superBuilderClass = getSuperBuilderClass(psiClass);
            this.builderClassName = entityClassName + "Builder";
            this.entityType = factory.createType(psiClass);
            this.superClass = psiClass.getSuperClass();
            this.psiClass = psiClass;
            this.factory = factory;

        }
    }

    public BuilderHandler() {
        super(Constants.BUILDER_TITLE);
    }


    @Nullable
    private static PsiClass getThisBuilderClass(@NotNull PsiClass psiClass) {
        final String builderClassName = psiClass.getName() + "Builder";
        return psiClass.findInnerClassByName(builderClassName, false);
    }

    @Nullable
    private static PsiClass getSuperBuilderClass(@NotNull PsiClass psiClass) {
        return Optional.ofNullable(psiClass.getSuperClass())
                .map(BuilderHandler::getThisBuilderClass)
                .orElse(null);
    }


    @Nullable
    private static PsiMethod getDefaultConstructor(@NotNull PsiClass psiClass) {
        return stream(psiClass.getConstructors())
                .filter(it -> it.getParameterList().isEmpty())
                .findAny().orElse(null);
    }

    @Nullable
    private static PsiMethod getFirstMethod(@NotNull PsiClass psiClass) {
        return stream(psiClass.getMethods())
                .filter(it -> !it.isConstructor())
                .findFirst().orElse(null);
    }


    private static PsiType getBuilderReturnType(BuilderInfo info) {
        return PsiTypeBuilder.builder(info.factory, "%s<? extends %s, ? extends %s<?, ?>>",
                info.builderClassName, info.entityClassName, info.builderClassName)
                .build();
    }

    @Override
    protected final ClassMember[] getAllOriginalMembers(@Nullable PsiClass psiClass) {
        return psiClass == null ? new PsiFieldMember[0] : stream(psiClass.getFields())
                .filter(it -> !(it.hasModifierProperty(FINAL) && it.hasInitializer()))
                .filter(it -> !it.hasModifierProperty(STATIC))
                .map(PsiFieldMember::new)
                .toArray(ClassMember[]::new);
    }

    @Override
    protected ClassMember[] chooseOriginalMembers(PsiClass psiClass, Project project, Editor editor) {
        final ClassMember[] allMembers = getAllOriginalMembers(psiClass);
        if (Objects.isNull(allMembers) || allMembers.length <= 0) {
            String msg = "No fields without builder were found";
            getInstance().showErrorHint(editor, msg);
            return null;
        }
        return super.chooseOriginalMembers(psiClass, project, editor);
    }

    @NotNull
    protected List<? extends GenerationInfo> generateMemberPrototypes(PsiClass psiClass, ClassMember[] members) {
        return Arrays.asList(
                // 默认构造方法
                new DefaultConstructorGenerationInfo(members),
                // ToBuilder 方法
                new ToBuilderMethodGenerationInfo(members),
                // Builder构造方法
                new BuilderConstructorGenerationInfo(members),
                // Builder 方法
                new DefaultBuilderMethodGenerationInfo(members),
                // Builder copy 方法
                new DefaultBuilderCopyMethodGenerationInfo(members),
                // Builder 类
                new BuilderClassGenerationInfo(members),
                // Builder 实现 类
                new BuilderImplClassGenerationInfo(members)
        );
    }

    @Override
    protected GenerationInfo[] generateMemberPrototypes(PsiClass aClass, ClassMember originalMember) {
        return new GenerationInfo[0];
    }

    private static abstract class AbstractGenerationInfo extends GenerationInfoBase {
        protected final List<PsiFieldMember> list;
        private PsiMember psiMember;

        public AbstractGenerationInfo(ClassMember[] members) {
            list = stream(members).filter(it -> it instanceof PsiFieldMember)
                    .map(PsiFieldMember.class::cast).collect(toList());
        }

        @Override
        @Nullable
        public final PsiMember getPsiMember() {
            return psiMember;
        }

        @Nullable
        protected abstract PsiMember getPsiMember(@NotNull PsiClass aClass, PsiElementFactory factory, BuilderInfo info);

        @Override
        public final void insert(@NotNull PsiClass aClass, PsiElement anchor, boolean before) throws IncorrectOperationException {
            final PsiElementFactory factory = getInstance(aClass.getProject()).getElementFactory();
            final BuilderInfo info = new BuilderInfo(factory, aClass);
            if ((psiMember = getPsiMember(aClass, factory, info)) == null) {
                return;
            }
            addElement(info.psiClass, this.psiMember);
        }

        protected void addElement(@NotNull PsiClass aClass, @NotNull PsiMember psiMember) {
            PsiClassBuilder.builder(aClass).addBefore(psiMember, PsiClass::getRBrace);
        }
    }

    // 默认构造方法
    private static final class DefaultConstructorGenerationInfo extends AbstractGenerationInfo {
        public DefaultConstructorGenerationInfo(ClassMember[] members) {
            super(members);
        }

        @Nullable
        @Override
        protected PsiMember getPsiMember(@NotNull PsiClass aClass, PsiElementFactory factory, BuilderInfo info) {
            if (getDefaultConstructor(aClass) == null && isNotBlank(info.entityClassName)) {
                return PsiMethodBuilder.createConstructor(info.factory, info.entityClassName)
                        .addModifiers(PUBLIC)
                        .build();
            }
            return null;
        }

        protected void addElement(@NotNull PsiClass aClass, @NotNull PsiMember psiMember) {
            final PsiMethod method = BuilderHandler.getFirstMethod(aClass);
            PsiClassBuilder.builder(aClass).addBefore(psiMember, it -> {
                return method == null ? it.getRBrace() : method; //
            });
        }
    }

    // toBuilder 方法生成
    private static final class ToBuilderMethodGenerationInfo extends AbstractGenerationInfo {
        public ToBuilderMethodGenerationInfo(ClassMember[] members) {
            super(members);
        }

        @Nullable
        @Override
        protected PsiMember getPsiMember(@NotNull PsiClass aClass, PsiElementFactory factory, BuilderInfo info) {
            return PsiMethodBuilder.builder(info.factory, "toBuilder", getBuilderReturnType(info))
                    .addStatementFromText("return new %s(this);", info.builderImplClassName)
                    .addModifiers(PUBLIC)
                    .build();

        }

        protected void addElement(@NotNull PsiClass aClass, @NotNull PsiMember psiMember) {
            PsiClassBuilder.builder(aClass).add(psiMember);
        }

    }

    // Builder的构造方法
    private static final class BuilderConstructorGenerationInfo extends AbstractGenerationInfo {

        public BuilderConstructorGenerationInfo(ClassMember[] members) {
            super(members);
        }

        @Override
        protected @Nullable PsiMember getPsiMember(@NotNull PsiClass aClass, PsiElementFactory factory, BuilderInfo info) {
            return PsiMethodBuilder.createConstructor(info.factory, info.entityClassName)
                    .addTypeParameterFromText("E extends %s", info.entityClassName)
                    .addTypeParameterFromText("B extends %s<E,B>", info.builderClassName)
                    .addParameterFromText("%s<E,B> builder", info.builderClassName)
                    .ifAdd(info.superClass != null && info.superBuilderClass != null, it -> {
                        it.addStatementFromText("super(builder);"); //
                    })
                    .forAdd(this.list, (it, member) -> {
                        String fieldName = member.getElement().getName();
                        it.addStatementFromText("this.%s = builder.%s;", fieldName, fieldName);
                    })
                    .addModifiers(PROTECTED).build();
        }
    }

    // Builder 静态方法
    private static final class DefaultBuilderMethodGenerationInfo extends AbstractGenerationInfo {
        public DefaultBuilderMethodGenerationInfo(ClassMember[] members) {
            super(members);
        }

        @Override
        protected @Nullable PsiMember getPsiMember(@NotNull PsiClass aClass, PsiElementFactory factory, BuilderInfo info) {
            final PsiType returnType = BuilderHandler.getBuilderReturnType(info);
            return PsiMethodBuilder.builder(info.factory, "builder", returnType)
                    .addStatementFromText("return new %s();", info.builderImplClassName)
                    .addModifiers(PUBLIC, STATIC).build();
        }
    }


    // Builder Copy 静态方法
    private static final class DefaultBuilderCopyMethodGenerationInfo extends AbstractGenerationInfo {
        public DefaultBuilderCopyMethodGenerationInfo(ClassMember[] members) {
            super(members);
        }

        @Override
        protected @Nullable PsiMember getPsiMember(@NotNull PsiClass aClass, PsiElementFactory factory, BuilderInfo info) {
            final PsiType returnType = BuilderHandler.getBuilderReturnType(info);
            return PsiMethodBuilder.builder(info.factory, "builder", returnType)
                    .addParameter(info.factory.createParameter(info.entityName, info.entityType))
                    .addStatementFromText("return new %s(%s);", info.builderImplClassName, info.entityName)
                    .addModifiers(PUBLIC, STATIC).build();
        }
    }

    // Builder 静态类
    private static final class BuilderClassGenerationInfo extends AbstractGenerationInfo {
        public BuilderClassGenerationInfo(ClassMember[] members) {
            super(members);
        }

        @Override
        protected @Nullable PsiMember getPsiMember(@NotNull PsiClass aClass, PsiElementFactory factory, BuilderInfo info) {
            return PsiClassBuilder.builder(info.factory, info.builderClassName)
                    .addTypeParameterFromText("E extends %s", info.entityClassName)
                    .addTypeParameterFromText("B extends %s<E,B>", info.builderClassName)
                    .addModifiers(PUBLIC, STATIC, ABSTRACT)
                    // 是否继承上级 Builder
                    .ifAdd(info.superClass != null && info.superBuilderClass != null, it -> {
                        Objects.requireNonNull(info.superBuilderClass);
                        it.superClass(info.factory.createTypeElementFromText(format("%s<E,B>",
                                info.superBuilderClass.getName()), null));
                    })
                    // 生成所有属性信息
                    .forAdd(this.list, (it, member) -> {
                        PsiField field = member.getElement();
                        it.addField(PsiFieldBuilder.builder(info.factory, field.getName(), field.getType())
                                .addModifiers(PRIVATE).build());
                    })
                    // 生成默认无参构造方法
                    .addMethod(PsiMethodBuilder.createConstructor(info.factory, info.builderClassName)
                            .addModifiers(PROTECTED).build())
                    // 生成实体Copy构造方法
                    .addMethod(PsiMethodBuilder.createConstructor(info.factory, info.builderClassName)
                            .addModifiers(PROTECTED)
                            .addParameterFromText("%s %s", info.entityClassName, info.entityName)
                            .ifAdd(info.superClass != null && info.superBuilderClass != null, it -> {
                                Objects.requireNonNull(info.entityName);
                                it.addStatementFromText("super(%s);", info.entityName);
                            })
                            .addStatementFromText("if(%s == null) { \n\treturn;\n} ", info.entityName)
                            .forAdd(this.list, (it, member) -> {
                                String fieldName = member.getElement().getName();
                                it.addStatementFromText("this.%s = %s.%s;", fieldName, info.entityName, fieldName);
                            }).build())
                    // 生成 Get This 方法
                    .ifAdd(info.superClass == null || info.superBuilderClass == null, it -> {
                        final String method = "protected abstract B self();";
                        it.addMethod(PsiMethodBuilder.builderFromText(info.factory, method).build());
                    })
                    // 生成所有属性的设置方法
                    .forAdd(this.list, (it, member) -> {
                        PsiField field = member.getElement();
                        String fieldName = StringUtil.defaultIfEmpty(field.getName(), "value");
                        PsiType returnType = PsiTypeBuilder.builder(info.factory, "B").build();
                        PsiParameter psiParameter = info.factory.createParameter(fieldName, field.getType());
                        it.addMethod(PsiMethodBuilder.builder(info.factory, field.getName(), returnType)
                                .addModifiers(PUBLIC, FINAL).addParameter(psiParameter)
                                .addStatementFromText("this.%s = %s;", field.getName(), fieldName)
                                .addStatementFromText("return self();")
                                .build());
                    })
                    // 生成 build 方法
                    .ifAdd(info.superClass == null || info.superBuilderClass == null, it -> {
                        final String method = "public abstract E build();";
                        it.addMethod(PsiMethodBuilder.builderFromText(info.factory, method).build());
                    })
                    // 生成类对象
                    .build();
        }
    }

    // BuilderImpl 静态类
    private static final class BuilderImplClassGenerationInfo extends AbstractGenerationInfo {
        public BuilderImplClassGenerationInfo(ClassMember[] members) {
            super(members);
        }

        @Override
        protected @Nullable PsiMember getPsiMember(@NotNull PsiClass aClass, PsiElementFactory factory, BuilderInfo info) {
            return PsiClassBuilder.builder(info.factory, info.builderImplClassName)
                    .addModifiers(PRIVATE, STATIC, FINAL)
                    // 父类继承
                    .superClass(info.factory.createTypeElementFromText(format("%s<%s,%s>",
                            info.builderClassName, info.entityClassName, info.builderImplClassName), null))
                    // 生成默认无参构造方法
                    .addMethod(PsiMethodBuilder.createConstructor(info.factory, info.builderImplClassName)
                            .addModifiers(PROTECTED)
                            .build())
                    // 生成实体Copy构造方法
                    .addMethod(PsiMethodBuilder.createConstructor(info.factory, info.builderImplClassName)
                            .addParameterFromText("%s %s", info.entityClassName, info.entityName)
                            .addStatementFromText("super(%s);", info.entityName)
                            .addModifiers(PROTECTED)
                            .build())
                    // 生成 Get This 方法
                    .addMethod(PsiMethodBuilder.builder(info.factory, "self", PsiTypeBuilder.builder(info.factory, info.builderImplClassName).build())
                            .addModifiers(PROTECTED, FINAL).addAnnotation("Override")
                            .addStatementFromText("return this;")
                            .build())
                    // 生成 build 方法
                    .addMethod(PsiMethodBuilder.builder(info.factory, "build", PsiTypeBuilder.builder(info.factory, info.entityClassName).build())
                            .addModifiers(PUBLIC, FINAL).addAnnotation("Override")
                            .addStatementFromText("return new %s(this);", info.entityClassName)
                            .build())
                    // 生成类对象
                    .build();
        }
    }
}
