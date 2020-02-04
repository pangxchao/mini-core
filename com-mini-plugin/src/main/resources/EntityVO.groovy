import com.mini.plugin.builder.FieldSpecBuilder
import com.mini.plugin.builder.MethodSpecBuilder
import com.mini.plugin.builder.TypeSpecBuilder
import com.mini.plugin.config.TableInfo
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec

import static com.mini.plugin.util.ColumnUtil.getColumnType
import static com.mini.plugin.util.GroovyUtil.*
import static com.mini.plugin.util.StringUtil.firstLowerCase
import static com.mini.plugin.util.StringUtil.firstUpperCase
import static javax.lang.model.element.Modifier.*

//noinspection GrUnresolvedAccess
TableInfo info = tableInfo

//noinspection GrUnresolvedAccess
return JavaFile.builder(voBeanPackage(info),
	TypeSpecBuilder.classBuilder(voBeanName(info))
		.addModifiers(PUBLIC)
		.superclass(beanClass(info))
		.addSuperinterface(Serializable.class)
		.addAnnotation(lombokDataClass())
		.addAnnotation(paramClass())
		.addAnnotation(AnnotationSpec.builder(tableClass())
			.addMember('value', '$S', info.getTableName())
			.build())

	// 添加表联合条件
		.forAdd(info.getColumnList(), {builder, column ->
			builder.ifAdd(column.isRef(), {
				it.addAnnotation(AnnotationSpec.builder(joinClass())
					.addMember('column', '$S', column.getColumnName())
					.build())
			})
		})
		.addJavadoc('$L \n', info.getComment())
		.addJavadoc('@author xchao \n')

	// 生成属性信息
		.forAdd(info.getColumnList(), {builder, column ->
			builder.ifAdd(column.isExt(), {
				builder.addField(FieldSpecBuilder.builder(getColumnType(column), column.getFieldName())
					.addModifiers(PRIVATE)
				// @Column 注解
					.addAnnotation(AnnotationSpec.builder(columnClass())
						.addMember('value', '$S', column.getColumnName())
						.build())
				// @Ref 注解
					.ifAdd(column.isRef(), {v ->
						v.addAnnotation(AnnotationSpec.builder(refClass())
							.addMember('table', '$S', column.getRefTable())
							.addMember('column', '$S', column.getRefColumn())
							.build())
					})
					.build())
			})
		})

	// 生成日期格式化扩展方法
		.forAdd(info.getColumnList(), {builder, column ->
			builder.ifAdd(Date.class.isAssignableFrom(getColumnType(column)) && column.isExt(), {
				builder.addMethod(MethodSpecBuilder.methodBuilder('get' + firstUpperCase(column.getFieldName()) + '_DT')
					.addModifiers(PUBLIC, FINAL)
					.returns(String.class)
					.addStatement('return $T.formatDateTime($N)', dateFormatUtilClass(), column.getFieldName())
					.build())

				builder.addMethod(MethodSpecBuilder.methodBuilder('get' + firstUpperCase(column.getFieldName()) + '_D')
					.addModifiers(PUBLIC, FINAL)
					.returns(String.class)
					.addStatement('return $T.formatDate($N)', dateFormatUtilClass(), column.getFieldName())
					.build())

				builder.addMethod(MethodSpecBuilder.methodBuilder('get' + firstUpperCase(column.getFieldName()) + '_T')
					.addModifiers(PUBLIC, FINAL)
					.returns(String.class)
					.addStatement('return $T.formatTime($N)', dateFormatUtilClass(), column.getFieldName())
					.build())
			})
		})

	// 生成静态无参数 builder 方法
		.addMethod(MethodSpecBuilder.methodBuilder('builder')
			.addModifiers(PUBLIC, STATIC)
			.returns(voBeanClass(info))
			.addStatement('return new $T(new $T())', voBuilderClass(info), voBeanClass(info))
			.build())

	// 生成静态 copy builder 方法
		.addMethod(MethodSpecBuilder.methodBuilder('builder')
			.addModifiers(PUBLIC, STATIC)
			.returns(voBeanClass(info))
			.addParameter(voBeanClass(info), 'copy')
			.addCode('return $T.builder()', voBeanClass(info))
			.forAdd(info.getColumnList(), {method, column ->
				method.addCode('\n\t.$L(copy.get$L())', column.getFieldName(), firstUpperCase(column.getFieldName()))
			})
			.addStatement('')
			.build())

	// 生成静态 Builder 类对象
		.addType(TypeSpecBuilder.classBuilder(voBuilderClass(info))
			.superclass(builderClass(info))
			.addModifiers(PUBLIC, STATIC)
		// 私有属性
			.addField(FieldSpecBuilder.builder(voBeanClass(info), firstLowerCase(beanName(info)))
				.addModifiers(PRIVATE, FINAL)
				.build())
		// 构造方法
			.addMethod(MethodSpec.constructorBuilder()
				.addModifiers(PROTECTED)
				.addParameter(voBeanClass(info), firstLowerCase(beanName(info)))
				.addStatement('super($L)', firstLowerCase(beanName(info)))
				.addStatement('this.$L = $L', firstLowerCase(beanName(info)),
					firstLowerCase(beanName(info)))
				.build())
		// 为每个属性生成一个 Setter 方法
			.forAdd(info.getColumnList(), {builder, column ->
				builder.addMethod(MethodSpecBuilder.methodBuilder(column.getFieldName())
					.addModifiers(PUBLIC)
					.returns(voBuilderClass(info))
					.addParameter(getColumnType(column), column.getFieldName())
					.addStatement('$L.set$L($L)', firstLowerCase(beanName(info)),
						column.getFieldName(), column.getFieldName())
					.addStatement('return this')
					.build())
			})

		// 生成 builder 方法
			.addMethod(MethodSpecBuilder.methodBuilder('build')
				.addModifiers(PUBLIC)
				.returns(voBeanClass(info))
				.addAnnotation(nonnullClass())
				.addStatement('return $L', firstLowerCase(beanName(info)))
				.build())
			.build())
		.build())
	.build()
