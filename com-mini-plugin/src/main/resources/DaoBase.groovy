import com.mini.plugin.builder.MethodSpecBuilder
import com.mini.plugin.config.TableInfo
import com.mini.plugin.util.ColumnUtil
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

import static com.mini.plugin.util.GroovyUtil.*
import static javax.lang.model.element.Modifier.DEFAULT
import static javax.lang.model.element.Modifier.PUBLIC

//noinspection GrUnresolvedAccess
TableInfo info = tableInfo

return JavaFile.builder(daoBasePackage(info),
	TypeSpec.interfaceBuilder(daoBaseName(info))
		.addModifiers(PUBLIC)
		.addSuperinterface(jdbcInterfaceClass())
		.addJavadoc('$L Dao Base \n', info.getComment())
		.addJavadoc('@author xchao \n')

	// 生成 deleteById 方法
		.addMethod(MethodSpecBuilder
			.methodBuilder('deleteById')
			.addModifiers(DEFAULT, PUBLIC)
			.returns(int.class)
			.addJavadoc('删除$L \n', info.getComment())
			.forAdd(info.getColumnList(), {method, column ->
				method.ifAdd(column.isId(), {
					method.addParameter(ColumnUtil.getColumnType(column), column.getFieldName())
					method.addJavadoc('@param $N $N \n', column.getFieldName(), column.getComment())
				})
			})
			.addJavadoc('@return 执行结果 \n')
			.addCode('return execute(new $T() {{ \n ', sqlBuilderClass())
			.ifElseAdd(info.getColumnList().stream().anyMatch({
				return it.isDel()
			}), {
				it.addStatement('update($T.$L)', beanClass(info), info.getTableName().toUpperCase())
				it.forAdd(info.getColumnList(), {method, column ->
					method.ifAdd(column.isDel(), {
						method.addStatement('set("%s = ?", $T.$L)', beanClass(info),
							column.getColumnName().toUpperCase())
						method.addStatement('params($S)', column.getDelValue())
					})
				})
			}, {
				it.addStatement('delete().from($T.$L)', builderClass(info), //
					info.getTableName().toUpperCase())
			})
			.forAdd(info.getColumnList(), {method, column ->
				method.ifAdd(column.isId(), {
					method.addStatement('\twhere($S, $T.$L)', '%s = ?', beanClass(info),
						column.getColumnName().toUpperCase())
					method.addStatement('\tparams($N)', column.getFieldName())
				})
			})
			.addStatement('}})')
			.build())

	// 生成 queryById 方法
		.addMethod(MethodSpecBuilder
			.methodBuilder('queryById')
			.addModifiers(DEFAULT, PUBLIC)
			.returns(beanClass(info))
			.addJavadoc('根据ID查询实体信息 \n')
			.forAdd(info.getColumnList(), {method, column ->
				method.ifAdd(column.isId(), {
					method.addParameter(ColumnUtil.getColumnType(column), column.getFieldName())
					method.addJavadoc('@param $N $N \n', column.getFieldName(), column.getComment())
				})
			})
			.addJavadoc('@return 实体信息 \n')
			.addCode('return queryObject(new $T($T.class) {{ \n', sqlBuilderClass(), beanClass(info))
			.forAdd(info.getColumnList(), {method, column ->
				method.ifAdd(column.isId(), {
					method.addStatement('\twhere($S, $T.$L)', '%s = ?', beanClass(info),
						column.getColumnName().toUpperCase())
					method.addStatement('\tparams($N)', column.getFieldName())
				})
				method.ifAdd(column.isDel(), {
					method.addStatement('\twhere($S, $T.$L)', '%s <> ?', beanClass(info),
						column.getColumnName().toUpperCase())
					method.addStatement('\tparams($S)', column.getDelValue())
				})
			})
			.addStatement('}}, $T.create($T.class))', beanMapperClass(), beanClass(info))
			.build())

	// 生成 queryAll 方法
		.addMethod(MethodSpecBuilder
			.methodBuilder('queryAll')
			.addModifiers(DEFAULT, PUBLIC)
			.returns(getParameterizedTypeName(List.class, beanClass(info))) //
			.addJavadoc('查询所有实体信息 \n') //
			.addJavadoc('@return 实体信息列表 \n') //
			.addCode('return queryList(new $T($T.class) {{ \n', sqlBuilderClass(), beanClass(info))
			.forAdd(info.getColumnList(), {method, column ->
				method.ifAdd(column.isDel(), {
					method.addStatement('\twhere($S, $T.$L)', '%s <> ?', beanClass(info),
						column.getColumnName().toUpperCase())
					method.addStatement('\tparams($S)', column.getDelValue())
				})
			})
			.addStatement('}},  $T.create($T.class))', beanMapperClass(), beanClass(info))
			.build())

	// 生成 queryAll(Paging) 方法
		.addMethod(MethodSpecBuilder
			.methodBuilder('queryAll')
			.addModifiers(DEFAULT, PUBLIC)
			.returns(getParameterizedTypeName(pagingClass(), beanClass(info)))
			.addParameter(int.class, 'page')
			.addParameter(int.class, 'limit')
			.addJavadoc('查询所有实体信息 \n')
			.addJavadoc('@param $N 分页-页码数\n', 'page')
			.addJavadoc('@param $N 分页- 每页条数\n', 'limit')
			.addJavadoc('@return 实体信息列表 \n')
			.addCode('return queryPaging(page, limit, new $T($T.class)  {{ \n',
				sqlBuilderClass(), beanClass(info))
			.forAdd(info.getColumnList(), {method, column ->
				method.ifAdd(column.isDel(), {
					method.addStatement('\twhere($S, $T.$L)', '%s <> ?', beanClass(info),
						column.getColumnName().toUpperCase())
					method.addStatement('\tparams($S)', column.getDelValue())
				})
			})
			.addStatement('}},  $T.create($T.class))', beanMapperClass(), beanClass(info))
			.build())
		.build())
	.build()