import com.mini.plugin.builder.javapoet.TypeSpecBuilder
import com.mini.plugin.config.TableInfo
import com.squareup.javapoet.JavaFile

import static javax.lang.model.element.Modifier.PUBLIC

//noinspection GrUnresolvedAccess
TableInfo info = tableInfo
TypeSpecBuilder builder

// 定义类的声明
builder = TypeSpecBuilder.interfaceBuilder(info.daoName())
builder.addModifiers(PUBLIC)
builder.addSuperinterface(info.daoBaseClass())
builder.addJavadoc('$L Dao \n', info.getComment())
builder.addJavadoc('@author xchao \n')

// 返回JavaFile信息
return JavaFile.builder(info.daoPackage(),
		builder.build()).build()
