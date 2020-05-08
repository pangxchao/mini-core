import com.mini.plugin.builder.javapoet.MethodSpecBuilder
import com.mini.plugin.builder.javapoet.TypeSpecBuilder
import com.mini.plugin.config.TableInfo
import com.mini.plugin.util.ColumnUtil
import com.squareup.javapoet.JavaFile

import static com.mini.plugin.util.GroovyUtil.*
import static javax.lang.model.element.Modifier.DEFAULT
import static javax.lang.model.element.Modifier.PUBLIC

//noinspection GrUnresolvedAccess
TableInfo info = tableInfo
MethodSpecBuilder method
TypeSpecBuilder builder

// 定义类的声明
builder = TypeSpecBuilder.interfaceBuilder(daoBaseName(info))
builder.addModifiers(PUBLIC)
builder.addSuperinterface(jdbcInterfaceClass())
builder.addJavadoc('$L Base Dao \n', info.getComment())
builder.addJavadoc('@author xchao \n')

// 生成 deleteById 方法
method = MethodSpecBuilder.methodBuilder('deleteById')
method.methodBuilder('deleteById')
method.addModifiers(DEFAULT, PUBLIC)
method.returns(int.class)
method.addJavadoc('删除$L \n', info.getComment())
info.getColumnMap().forEach({ key, column ->
	if (column.isId()) {
		method.addParameter(ColumnUtil.getColumnType(column), column.getFieldName())
		method.addJavadoc('@param $N $N \n', column.getFieldName(), column.getComment())
	}
})
method.addJavadoc('@return 执行结果 \n')
method.addCode('return execute(new $T() {{ \n ', sqlBuilderClass())
if (info.getColumnMap().values().stream().anyMatch({ return it.isDel() })) {
	method.addStatement('\tthis.update($T.$L)', beanClass(info), info.getTableName().toUpperCase())
	info.getColumnMap().forEach({ key, column ->
		if (column.isDel()) {
			method.addStatement('\tsetEquals($T.$L, $S)', beanClass(info), column.getColumnName().toUpperCase(), column.getDelValue())
		}
		if (column.isLock()) {
			method.addStatement('\tsetEquals($T.$L, $T.currentTimeMillis())', beanClass(info), column.getColumnName().toUpperCase(), System.class)
		}
	})
} else {
	method.addStatement('\tdelete().from($T.$L)', beanClass(info), info.getTableName().toUpperCase())
}
info.getColumnMap().forEach({ key, column ->
	if (column.isId()) {
		method.addStatement('\twhereEquals($T.$L, $N)', beanClass(info), column.getColumnName().toUpperCase(), column.getFieldName())
	}
})
method.addStatement('}})')
builder.addMethod(method.build())

// 生成 queryById 方法
method = MethodSpecBuilder.methodBuilder('queryById')
method.addModifiers(DEFAULT, PUBLIC)
method.returns(beanClass(info))
method.addJavadoc('根据ID查询实体信息 \n')
info.getColumnMap().forEach({ key, column ->
	if (column.isId()) {
		method.addParameter(ColumnUtil.getColumnType(column), column.getFieldName())
		method.addJavadoc('@param $N $N \n', column.getFieldName(), column.getComment())
	}
})
method.addJavadoc('@return 实体信息 \n')
method.addCode('return queryObject(new $T($T.class) {{ \n', sqlBuilderClass(), beanClass(info))
info.getColumnMap().forEach({ key, column ->
	if (column.isId()) {
		method.addStatement('\twhereEquals($T.$L, $N)', beanClass(info), column.getColumnName().toUpperCase(), column.getFieldName())
	}
	if (column.isDel()) {
		method.addStatement('\twhereNotEqual($T.$L, $S)', beanClass(info), column.getColumnName().toUpperCase(), column.getDelValue())
	}
})
method.addStatement('}}, $T.class)', beanClass(info))
builder.addMethod(method.build())

// 生成 queryAll 方法
method = MethodSpecBuilder.methodBuilder('queryAll')
method.methodBuilder('queryAll')
method.addModifiers(DEFAULT, PUBLIC)
method.returns(getParameterizedTypeName(List.class, beanClass(info))) //
method.addJavadoc('查询所有实体信息 \n') //
method.addJavadoc('@return 实体信息列表 \n') //
method.addCode('return queryList(new $T($T.class) {{ \n', sqlBuilderClass(), beanClass(info))
info.getColumnMap().forEach({ key, column ->
	if (column.isDel()) {
		method.addStatement('\twhereNotEqual($T.$L, $S)', beanClass(info), column.getColumnName().toUpperCase(), column.getDelValue())
	}
})
method.addStatement('}},  $T.class)', beanClass(info))
builder.addMethod(method.build())

// 生成 queryAll(Paging) 方法
method = MethodSpecBuilder.methodBuilder('queryAll')
method.addModifiers(DEFAULT, PUBLIC)
method.returns(getParameterizedTypeName(pagingClass(), beanClass(info)))
method.addParameter(int.class, 'page')
method.addParameter(int.class, 'limit')
method.addJavadoc('查询所有实体信息 \n')
method.addJavadoc('@param $N 分页-页码数\n', 'page')
method.addJavadoc('@param $N 分页- 每页条数\n', 'limit')
method.addJavadoc('@return 实体信息列表 \n')
method.addCode('return queryPaging(page, limit, new $T($T.class)  {{ \n', sqlBuilderClass(), beanClass(info))
info.getColumnMap().forEach({ key, column ->
	if (column.isDel()) {
		method.addStatement('\twhereNotEqual($T.$L, $S)', beanClass(info), column.getColumnName().toUpperCase(), column.getDelValue())
	}
})
method.addStatement('}},  $T.class)', beanClass(info))
builder.addMethod(method.build())

// 返回JavaFile信息
return JavaFile.builder(daoBasePackage(info),
		builder.build()).build()