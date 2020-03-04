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

//noinspection GrUnresolvedAccess
TableInfo info = tableInfo

//noinspection GrUnresolvedAccess
return JavaFile.builder(voBeanPackage(info),
		TypeSpecBuilder.classBuilder(voBeanName(info))
				.addModifiers(PUBLIC)
				.superclass(beanClass(info))
				.addSuperinterface(Serializable.class)
				.addAnnotation(paramClass())
				.addAnnotation(lombokGetterClass())
				.addAnnotation(lombokSetterClass())
				.addAnnotation(AnnotationSpec.builder(lombokToStringClass())
						.addMember('callSuper', '$L', 'true')
						.build())
				.addAnnotation(AnnotationSpec.builder(lombokSuperBuilderClass())
						.addMember('toBuilder', '$L', 'true')
						.build())
				.addAnnotation(AnnotationSpec.builder(lombokEqualsAndHashCodeClass())
						.addMember('callSuper', '$L', 'true')
						.build())
				.addAnnotation(AnnotationSpec.builder(tableClass())
						.addMember('value', '$S', info.getTableName())
						.build())

				// 添加表联合条件
				.forAdd(info.getColumnList(), { builder, column ->
					builder.ifAdd(column.isRef(), {
						it.addAnnotation(AnnotationSpec.builder(joinClass())
								.addMember('column', '$S', column.getColumnName())
								.addMember('type', '$T.JoinType.LEFT', joinClass())
								.build())
					})
				})
				.addJavadoc('$L \n', info.getComment())
				.addJavadoc('@author xchao \n')

				// 生成属性信息
				.forAdd(info.getColumnList(), { builder, column ->
					builder.ifAdd(column.isExt(), {
						builder.addField(FieldSpecBuilder.builder(getColumnType(column), column.getFieldName())
								.addModifiers(PRIVATE)
								// @Column 注解
								.addAnnotation(AnnotationSpec.builder(columnClass())
										.addMember('value', '$S', column.getColumnName())
										.build())
								// @Ref 注解
								.ifAdd(column.isRef(), { v ->
									v.addAnnotation(AnnotationSpec.builder(refClass())
											.addMember('table', '$S', column.getRefTable())
											.addMember('column', '$S', column.getRefColumn())
											.build())
								})
								.build())
					})
				})

				// 添加默认无参构造方法
				.addMethod(MethodSpecBuilder.constructorBuilder()
						.addAnnotation(lombokTolerateClass())
						.addModifiers(PUBLIC)
						.build())

				// 生成日期格式化扩展方法
				.forAdd(info.getColumnList(), { builder, column ->
					builder.ifAdd(Date.class.isAssignableFrom(getColumnType(column)) && column.isExt(), {
						builder.addMethod(MethodSpecBuilder.methodBuilder('get' + firstUpperCase(
								column.getFieldName()) + '_DT')
								.addModifiers(PUBLIC, FINAL)
								.returns(String.class)
								.addStatement('if($N == null) return null;', column.getFieldName())
								.addStatement('return $T.formatDateTime($N)', dateFormatUtilClass(),
										column.getFieldName())
								.build())

						builder.addMethod(MethodSpecBuilder.methodBuilder('get' + firstUpperCase(
								column.getFieldName()) + '_D')
								.addModifiers(PUBLIC, FINAL)
								.returns(String.class)
								.addStatement('if($N == null) return null;', column.getFieldName())
								.addStatement('return $T.formatDate($N)', dateFormatUtilClass(),
										column.getFieldName())
								.build())

						builder.addMethod(MethodSpecBuilder.methodBuilder('get' + firstUpperCase(
								column.getFieldName()) + '_T')
								.addModifiers(PUBLIC, FINAL)
								.returns(String.class)
								.addStatement('if($N == null) return null;', column.getFieldName())
								.addStatement('return $T.formatTime($N)', dateFormatUtilClass(),
										column.getFieldName())
								.build())
					})
				})
				.build())
		.build()
