import com.mini.plugin.builder.javapoet.FieldSpecBuilder
import com.mini.plugin.builder.javapoet.MethodSpecBuilder
import com.mini.plugin.builder.javapoet.TypeSpecBuilder
import com.mini.plugin.config.TableInfo
import com.mini.plugin.util.ColumnUtil
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec

import static com.mini.plugin.util.GroovyUtil.*
import static com.mini.plugin.util.StringUtil.firstLowerCase
import static com.mini.plugin.util.StringUtil.firstUpperCase
import static javax.lang.model.element.Modifier.*

//noinspection GrUnresolvedAccess
TableInfo info = tableInfo

//noinspection GrUnresolvedAccess
return JavaFile.builder(beanPackage(info),
	TypeSpecBuilder.classBuilder(beanName(info))
		.addModifiers(PUBLIC)
		.addSuperinterface(Serializable.class)
		.addAnnotation(lombokDataClass())
		.addAnnotation(paramClass())
		.addAnnotation(AnnotationSpec.builder(tableClass())
			.addMember('value', '$S', info.getTableName())
			.build())
		.addJavadoc('$L \n', info.getComment())
		.addJavadoc('@author xchao \n')

	// 生成常量 TABLE 字段
		.addField(FieldSpec.builder(String.class, info.getTableName().toUpperCase())
			.addModifiers(PUBLIC, STATIC, FINAL)
			.initializer('$S', info.getTableName())
			.addAnnotation(AnnotationSpec.builder(commentClass())
				.addMember('value', '$S', info.getComment() + ':' + info.getTableName())
				.build())
			.build())

	// 生成字段常量
		.forAdd(info.getColumnList(), {builder, column ->
			builder.addField(FieldSpec.builder(String.class, column.getColumnName().toUpperCase())
				.addModifiers(PUBLIC, STATIC, FINAL)
				.initializer('$S ', column.getColumnName())
				.addAnnotation(AnnotationSpec.builder(commentClass())
					.addMember('value', '$S', column.getComment())
					.build())
				.build())
		})

	// 生成属性信息
		.forAdd(info.getColumnList(), {builder, column ->
			builder.ifAdd(!column.isExt(), {
				builder.addField(FieldSpecBuilder.builder(ColumnUtil.getColumnType(column), column.getFieldName())
					.addModifiers(PRIVATE)
				// @Id 注解
					.ifAdd(column.isId(), {v ->
						v.addAnnotation(idClass())
					})
				// @Del 注解
					.ifAdd(column.isDel(), {v ->
						v.addAnnotation(delClass())
					})
				// @Lock 注解
					.ifAdd(column.isLock(), {v ->
						v.addAnnotation(lockClass())
					})
				// @Auto 注解
					.ifAdd(column.isLock(), {v ->
						v.addAnnotation(autoClass())
					})
				// @CreateAt 注解
					.ifAdd(column.isCreateAt(), {v ->
						v.addAnnotation(createAtClass())
					})
				// @UpdateAt 注解
					.ifAdd(column.isUpdateAt(), {v ->
						v.addAnnotation(updateAtClass())
					})
				// @Column 注解
					.addAnnotation(AnnotationSpec.builder(columnClass())
						.addMember('value', '$L', column.getColumnName().toUpperCase())
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
			builder.ifAdd(Date.class.isAssignableFrom(ColumnUtil.getColumnType(column))
				&& !column.isExt(), {
				builder.addMethod(MethodSpecBuilder.methodBuilder('get' +
					firstUpperCase(column.getFieldName()) + '_DT')
					.addModifiers(PUBLIC, FINAL)
					.returns(String.class)
					.addStatement('return $T.formatDateTime($N)', dateFormatUtilClass(),
						column.getFieldName())
					.build())

				builder.addMethod(MethodSpecBuilder.methodBuilder('get' +
					firstUpperCase(column.getFieldName()) + '_D')
					.addModifiers(PUBLIC, FINAL)
					.returns(String.class)
					.addStatement('return $T.formatDate($N)', dateFormatUtilClass(),
						column.getFieldName())
					.build())

				builder.addMethod(MethodSpecBuilder.methodBuilder('get' +
					firstUpperCase(column.getFieldName()) + '_T')
					.addModifiers(PUBLIC, FINAL)
					.returns(String.class)
					.addStatement('return $T.formatTime($N)', dateFormatUtilClass(),
						column.getFieldName())
					.build())
			})
		})

	// 生成静态无参数 builder 方法
		.addMethod(MethodSpecBuilder.methodBuilder('builder')
			.addModifiers(PUBLIC, STATIC)
			.returns(builderClass(info))
			.addStatement('return new $T(new $T())', builderClass(info), beanClass(info))
			.build())

	// 生成静态 copy builder 方法
		.addMethod(MethodSpecBuilder.methodBuilder('builder')
			.addModifiers(PUBLIC, STATIC)
			.returns(builderClass(info))
			.addParameter(beanClass(info), 'copy')
			.addCode('return $T.builder()', beanClass(info))
			.forAdd(info.getColumnList(), {method, column ->
				method.ifAdd(!column.isExt(), {
					Class<?> type = ColumnUtil.getColumnType(column)
					String prefix = type == boolean.class || type == Boolean.class //
						? "is" : "get"
					method.addCode('\n\t.$L(copy.$L$L())', column.getFieldName(),
						prefix, firstUpperCase(column.getFieldName()))
				})
			})
			.addStatement('')
			.build())

	// 生成静态 Builder 类对象
		.addType(TypeSpecBuilder.classBuilder(builderClass(info))
			.addModifiers(PUBLIC, STATIC)
		// 私有属性
			.addField(FieldSpecBuilder.builder(beanClass(info), firstLowerCase(beanName(info)))
				.addModifiers(PRIVATE, FINAL)
				.build())
		// 构造方法
			.addMethod(MethodSpec.constructorBuilder()
				.addModifiers(PROTECTED)
				.addParameter(beanClass(info), firstLowerCase(beanName(info)))
				.addStatement('this.$L = $L', firstLowerCase(beanName(info)),
					firstLowerCase(beanName(info)))
				.build())
		// 为每个属性生成一个 Setter 方法
			.forAdd(info.getColumnList(), {builder, column ->
				builder.ifAdd(!column.isExt(), {
					builder.addMethod(MethodSpecBuilder.methodBuilder(column.getFieldName())
						.addModifiers(PUBLIC)
						.returns(builderClass(info))
						.addParameter(ColumnUtil.getColumnType(column), column.getFieldName())
						.addStatement('$L.set$L($L)', firstLowerCase(beanName(info)),
							firstUpperCase(column.getFieldName()),
							column.getFieldName())
						.addStatement('return this')
						.build())
				})
			})

		// 生成 builder 方法
			.addMethod(MethodSpecBuilder.methodBuilder('build')
				.addModifiers(PUBLIC)
				.returns(beanClass(info))
				.addAnnotation(nonnullClass())
				.addStatement('return $L', firstLowerCase(info.getEntityName()))
				.build())
			.build())
		.build())
	.build()
