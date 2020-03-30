import com.mini.plugin.builder.javapoet.FieldSpecBuilder
import com.mini.plugin.builder.javapoet.MethodSpecBuilder
import com.mini.plugin.builder.javapoet.TypeSpecBuilder
import com.mini.plugin.config.TableInfo
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.JavaFile

import static com.mini.plugin.util.ColumnUtil.getColumnType
import static com.mini.plugin.util.GroovyUtil.*
import static com.mini.plugin.util.StringUtil.firstUpperCase
import static javax.lang.model.element.Modifier.*

AnnotationSpec.Builder annotation
//noinspection GrUnresolvedAccess
TableInfo info = tableInfo
MethodSpecBuilder method
TypeSpecBuilder builder
FieldSpecBuilder field

// 定义类的声明
builder = TypeSpecBuilder.classBuilder(voBeanName(info))
builder.addModifiers(PUBLIC)
builder.superclass(beanClass(info))
builder.addSuperinterface(Serializable.class)
builder.addJavadoc('$L \n', info.getComment())
builder.addJavadoc('@author xchao \n')
builder.addAnnotation(paramClass())
builder.addAnnotation(lombokGetterClass())
builder.addAnnotation(lombokSetterClass())

// 添加 @ToString 注解
annotation = AnnotationSpec.builder(lombokToStringClass())
annotation.addMember('callSuper', '$L', 'true')
builder.addAnnotation(annotation.build())

// 添加 @SuperBuilder 注解
annotation = AnnotationSpec.builder(lombokSuperBuilderClass())
annotation.addMember('toBuilder', '$L', 'true')
builder.addAnnotation(annotation.build())

// 添加 @EqualsAndHashCode 注解
annotation = AnnotationSpec.builder(lombokEqualsAndHashCodeClass())
annotation.addMember('callSuper', '$L', 'true')
builder.addAnnotation(annotation.build())

// 添加 @Table 注解
annotation = AnnotationSpec.builder(tableClass())
annotation.addMember('value', '$S', info.getTableName())
builder.addAnnotation(annotation.build())

// 添加 @Join注解
info.getColumnList().forEach({ column ->
	if (column.isRef()) {
		annotation = AnnotationSpec.builder(joinClass())
		annotation.addMember('column', '$S', column.getColumnName())
		annotation.addMember('type', '$T.JoinType.LEFT', joinClass())
		builder.addAnnotation(annotation.build())
	}
})

// 生成属性信息
info.getColumnList().forEach({ column ->
	if (column.isExt()) {
		field = FieldSpecBuilder.builder(getColumnType(column), column.getFieldName())
		field.addModifiers(PRIVATE)
		// @Column 注解
		annotation = AnnotationSpec.builder(columnClass())
		annotation.addMember('value', '$S', column.getColumnName())
		field.addAnnotation(annotation.build())
		// @Ref 注解
		if (column.isRef()) {
			annotation = AnnotationSpec.builder(refClass())
			annotation.addMember('table', '$S', column.getRefTable())
			annotation.addMember('column', '$S', column.getRefColumn())
			field.addAnnotation(annotation.build())
		}
		builder.addField(field.build())
	}
})

// 添加默认无参构造方法
method = MethodSpecBuilder.constructorBuilder()
method.addAnnotation(lombokTolerateClass())
method.addModifiers(PUBLIC)
builder.addMethod(method.build())

// 生成日期格式化扩展方法
info.getColumnList().forEach({ column ->
	if (column.isExt() && Date.class.isAssignableFrom(getColumnType(column))) {
		String n = firstUpperCase(column.getFieldName())
		// 日期时间格式化
		method = MethodSpecBuilder.methodBuilder('get' + n + '_DT')
		method.addModifiers(PUBLIC, FINAL)
		method.returns(String.class)
		method.addStatement('if($N == null) return null', column.getFieldName())
		method.addStatement('return $T.formatDateTime($N)', dateFormatUtilClass(), column.getFieldName())
		builder.addMethod(method.build())
		// 日期格式化
		method = MethodSpecBuilder.methodBuilder('get' + n + '_D')
		method.addModifiers(PUBLIC, FINAL)
		method.returns(String.class)
		method.addStatement('if($N == null) return null', column.getFieldName())
		method.addStatement('return $T.formatDate($N)', dateFormatUtilClass(), column.getFieldName())
		builder.addMethod(method.build())
		// 时间格式化
		method = MethodSpecBuilder.methodBuilder('get' + n + '_T')
		method.addModifiers(PUBLIC, FINAL)
		method.returns(String.class)
		method.addStatement('if($N == null) return null', column.getFieldName())
		method.addStatement('return $T.formatTime($N)', dateFormatUtilClass(), column.getFieldName())
		builder.addMethod(method.build())
	}
})

// 返回JavaFile信息
return JavaFile.builder(voBeanPackage(info),
		builder.build()).build()
