import com.mini.core.jdbc.DaoInterface
import com.mini.core.jdbc.builder.SQLBuilder
import com.mini.plugin.builder.javapoet.MethodSpecBuilder
import com.mini.plugin.builder.javapoet.TypeSpecBuilder
import com.mini.plugin.config.TableInfo
import com.squareup.javapoet.JavaFile

import static com.mini.plugin.util.GroovyUtil.typeName
import static javax.lang.model.element.Modifier.DEFAULT
import static javax.lang.model.element.Modifier.PUBLIC

//noinspection GrUnresolvedAccess
TableInfo info = tableInfo
MethodSpecBuilder method
TypeSpecBuilder builder

// 定义类的声明
builder = TypeSpecBuilder.interfaceBuilder(info.daoBaseName())
builder.addModifiers(PUBLIC)
builder.addSuperinterface(typeName(DaoInterface.class, info.beanClass()))
builder.addJavadoc('$L Base Dao \n', info.getComment())
builder.addJavadoc('@author xchao \n')

// 生成 deleteById 方法
method = MethodSpecBuilder.methodBuilder('deleteById')
method.addModifiers(DEFAULT, PUBLIC)
method.returns(int.class)
// 生成参数
info.getColumnMap().values().stream().filter { it.isId() }.forEach { it ->
    method.addParameter(it.getType(), it.getFieldName())
}
method.addCode('return execute(new $T({ \n ', SQLBuilder.class)
if (info.getColumnMap().values().stream().anyMatch({ it.isDel() })) {
    method.addStatement('\tUPDATE(%T.$L)', info.beanClass(), info.tableName)
    info.getColumnMap().values().stream().filter { it.isDel() }.forEach { it ->
        method.addStatement('\tSET($L.$L + " = ?")', info.beanClass(), it.columnName)
        method.addStatement('\tARGS($S)', it.delValue)
    }
    info.getColumnMap().values().stream().filter { it.isLock() }.forEach { it ->
        method.addStatement('\tSET($L.$L + " = ?")', info.beanClass(), it.columnName)
        method.addStatement('\tARGS(System.currentTimeMillis())')
    }
    info.getColumnMap().values().stream().filter { it.isUpdateAt() }.forEach { it ->
        method.addStatement('\tSET($L.$L + " = ?")', info.beanClass(), it.columnName)
        method.addStatement('\tARGS(new $T())', Date.class)
    }
} else {
    method.addStatement('\tDELETE().FROM(%T.$L)', info.beanClass(), info.tableName)
}
info.getColumnMap().values().stream().filter { it.isId() || it.isLock() }.forEach { it ->
    method.addStatement('\tWHERE($L.$L + " = ?")', info.beanClass(), it.columnName)
    method.addStatement('\tARGS($L)', it.fieldName)
}
method.addStatement('}))')


// 生成 queryById方法
method = MethodSpecBuilder.methodBuilder('queryById')
method.addModifiers(DEFAULT, PUBLIC)
method.returns(info.beanClass())
info.getColumnMap().values().stream().filter { it.isId() }.forEach { it ->
    method.addParameter(it.getType(), it.getFieldName())
}
method.addCode('return queryObject(new $T({ \n ', SQLBuilder.class)
method.addStatement('\tSELECT_FROM_JOIN(%T.class)', info.beanClass())
info.getColumnMap().values().stream().filter { it.isId() }.forEach { it ->
    method.addStatement('\tWHERE($L.$L + " = ?")', info.beanClass(), it.columnName)
    method.addStatement('\tARGS($L)', it.fieldName)
}
info.getColumnMap().values().stream().filter { it.isDel() }.forEach { it ->
    method.addStatement('\tSET($L.$L + " = ?")', info.beanClass(), it.columnName)
    method.addStatement('\tARGS($S)', it.delValue)
}
method.addStatement('}), $T)', info.beanClass())


// 返回JavaFile信息
return JavaFile.builder(info.daoBasePackage(),
        builder.build()).build()