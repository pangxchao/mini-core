import com.alibaba.fastjson.annotation.JSONField
import com.mini.core.jdbc.suppor.*
import com.mini.plugin.builder.javapoet.FieldSpecBuilder
import com.mini.plugin.builder.javapoet.MethodSpecBuilder
import com.mini.plugin.builder.javapoet.TypeSpecBuilder
import com.mini.plugin.config.TableInfo
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.JavaFile
import lombok.Data
import lombok.experimental.SuperBuilder
import lombok.experimental.Tolerate

import static com.mini.plugin.util.StringUtil.firstUpperCase
import static javax.lang.model.element.Modifier.*

AnnotationSpec.Builder annotation
//noinspection GrUnresolvedAccess
TableInfo info = tableInfo
MethodSpecBuilder method
TypeSpecBuilder builder
FieldSpecBuilder field

// 定义类的声明
builder = TypeSpecBuilder.classBuilder(info.beanName())
builder.addModifiers(PUBLIC)
builder.addSuperinterface(Serializable.class)
builder.addAnnotation(Data.class)
builder.addJavadoc('$L \n', info.getComment())
builder.addJavadoc('@author xchao \n')

// 添加 @SuperBuilder 注解
annotation = AnnotationSpec.builder(SuperBuilder.class)
annotation.addMember('toBuilder', '$L', 'true')
builder.addAnnotation(annotation.build())

// 添加 @Table 注解
annotation = AnnotationSpec.builder(Table.class)
annotation.addMember('value', '$S', info.getTableName())
builder.addAnnotation(annotation.build())

// 生成常量表名称字段
field = FieldSpecBuilder.builder(String.class, info.getTableName().toUpperCase())
field.addModifiers(PUBLIC, STATIC, FINAL)
field.initializer('$S', info.getTableName())
annotation = AnnotationSpec.builder(Comment.class)
annotation.addMember('value', '$S', info.getComment())
field.addAnnotation(annotation.build())
builder.addField(field.build())

// 生成字段常量
info.getColumnMap().forEach({ key, it ->
    field = FieldSpecBuilder.builder(String.class, it.getColumnName().toUpperCase())
    field.addModifiers(PUBLIC, STATIC, FINAL)
    field.initializer('$S ', it.getColumnName())
    annotation = AnnotationSpec.builder(Comment.class)
    annotation.addMember('value', '$S', it.getComment())
    field.addAnnotation(annotation.build())
    builder.addField(field.build())
})

// 生成属性信息
info.getColumnMap().forEach({ key, it ->
    field = FieldSpecBuilder.builder(it.getType(), it.getFieldName())
    field.addModifiers(PRIVATE)
    // @Id 注解
    if (it.isId()) {
        field.addAnnotation(Id.class)
    }
    // @Del 注解
    if (it.isDel()) {
        annotation = AnnotationSpec.builder(Del.class)
        annotation.addMember('value', '$L', it.delValue)
        field.addAnnotation(annotation.build())
    }
    // @Lock 注解
    if (it.isLock()) {
        field.addAnnotation(Lock.class)
    }
    // @Auto 注解
    if (it.isAuto()) {
        field.addAnnotation(Auto.class)
    }
    // @CreateAt 注解
    if (it.isCreateAt()) {
        field.addAnnotation(CreateAt.class)
    }
    // @UpdateAt 注解
    if (it.isUpdateAt()) {
        field.addAnnotation(UpdateAt.class)
    }
    // @Column 注解
    annotation = AnnotationSpec.builder(Column.class)
    annotation.addMember('value', '$L', it.getColumnName().toUpperCase())
    field.addAnnotation(annotation.build())
    // 添加字段信息
    builder.addField(field.build())
})

// 添加默认无参构造方法
method = MethodSpecBuilder.constructorBuilder()
method.addAnnotation(Tolerate.class)
method.addModifiers(PUBLIC)
builder.addMethod(method.build())

// 生成日期格式化扩展方法
info.getColumnMap().forEach({ key, it ->
    if (Date.class.isAssignableFrom(it.getType())) {
        String n = firstUpperCase(it.getFieldName())

        // 日期时间格式化
        method = MethodSpecBuilder.methodBuilder('get' + n + '_DT')
        method.addAnnotation(AnnotationSpec.builder(JSONField)
                .addMember("format", "yyyy-MM-dd HH:mm:ss")
                .build())
        method.addModifiers(PUBLIC, FINAL)
        method.returns(Date.class)
        method.addStatement('return this.$L', it.getFieldName())
        builder.addMethod(method.build())
        // 日期格式化
        method = MethodSpecBuilder.methodBuilder('get' + n + '_D')
        method.addAnnotation(AnnotationSpec.builder(JSONField)
                .addMember("format", "yyyy-MM-dd")
                .build())
        method.addModifiers(PUBLIC, FINAL)
        method.returns(Date.class)
        method.addStatement('return this.$L', it.getFieldName())
        builder.addMethod(method.build())
        // 时间格式化
        method = MethodSpecBuilder.methodBuilder('get' + n + '_T')
        method.addAnnotation(AnnotationSpec.builder(JSONField)
                .addMember("format", "HH:mm:ss")
                .build())
        method.addModifiers(PUBLIC, FINAL)
        method.returns(Date.class)
        method.addStatement('return this.$L', it.getFieldName())
        builder.addMethod(method.build())
    }
})

// 返回JavaFile信息
return JavaFile.builder(info.beanPackage(),
        builder.build()).build()
