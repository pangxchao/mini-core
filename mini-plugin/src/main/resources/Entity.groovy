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
builder = TypeSpecBuilder.classBuilder(beanName(info))
builder.addModifiers(PUBLIC)
builder.addSuperinterface(Serializable.class)
builder.addAnnotation(lombokDataClass())
builder.addAnnotation(paramClass())
builder.addJavadoc('$L \n', info.getComment())
builder.addJavadoc('@author xchao \n')

// 添加 @SuperBuilder 注解
annotation = AnnotationSpec.builder(lombokSuperBuilderClass())
annotation.addMember('toBuilder', '$L', 'true')
builder.addAnnotation(annotation.build())

// 添加 @Table 注解
annotation = AnnotationSpec.builder(tableClass())
annotation.addMember('value', '$S', info.getTableName())
builder.addAnnotation(annotation.build())

// 生成常量表名称字段
field = FieldSpecBuilder.builder(String.class, info.getTableName().toUpperCase())
field.addModifiers(PUBLIC, STATIC, FINAL)
field.initializer('$S', info.getTableName())
annotation = AnnotationSpec.builder(commentClass())
annotation.addMember('value', '$S', info.getComment())
field.addAnnotation(annotation.build())
builder.addField(field.build())

// 生成字段常量
info.getColumnMap().forEach({ key, column ->
	field = FieldSpecBuilder.builder(String.class, column.getColumnName().toUpperCase())
	field.addModifiers(PUBLIC, STATIC, FINAL)
	field.initializer('$S ', column.getColumnName())
	annotation = AnnotationSpec.builder(commentClass())
	annotation.addMember('value', '$S', column.getComment())
	field.addAnnotation(annotation.build())
	builder.addField(field.build())
})

// 生成属性信息
info.getColumnMap().forEach({ key, column ->
	field = FieldSpecBuilder.builder(getColumnType(column), column.getFieldName())
	field.addModifiers(PRIVATE)
	// @Id 注解
	if (column.isId()) {
		field.addAnnotation(idClass())
	}
	// @Del 注解
	if (column.isDel()) {
		annotation = AnnotationSpec.builder(delClass())
		annotation.addMember('value', '$L', column.delValue)
		field.addAnnotation(annotation.build())
	}
	// @Lock 注解
	if (column.isLock()) {
		field.addAnnotation(lockClass())
	}
	// @Auto 注解
	if (column.isAuto()) {
		field.addAnnotation(autoClass())
	}
	// @CreateAt 注解
	if (column.isCreateAt()) {
		field.addAnnotation(createAtClass())
	}
	// @UpdateAt 注解
	if (column.isUpdateAt()) {
		field.addAnnotation(updateAtClass())
	}
	// @Column 注解
	annotation = AnnotationSpec.builder(columnClass())
	annotation.addMember('value', '$L', column.getColumnName().toUpperCase())
	field.addAnnotation(annotation.build())
	// 添加字段信息
	builder.addField(field.build())
})

// 添加默认无参构造方法
method = MethodSpecBuilder.constructorBuilder()
method.addAnnotation(lombokTolerateClass())
method.addModifiers(PUBLIC)
builder.addMethod(method.build())

// 生成日期格式化扩展方法
info.getColumnMap().forEach({ key, column ->
	if (Date.class.isAssignableFrom(getColumnType(column))) {
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
return JavaFile.builder(beanPackage(info),
		builder.build()).build()
