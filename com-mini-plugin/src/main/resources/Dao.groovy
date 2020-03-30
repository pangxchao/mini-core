import com.mini.plugin.builder.javapoet.TypeSpecBuilder
import com.mini.plugin.config.TableInfo
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile

import static com.mini.plugin.util.GroovyUtil.*
import static javax.lang.model.element.Modifier.PUBLIC

ClassName implementedByClass = ClassName.get('com.google.inject', 'ImplementedBy')

AnnotationSpec.Builder annotation
//noinspection GrUnresolvedAccess
TableInfo info = tableInfo
TypeSpecBuilder builder

// 定义类的声明
builder = TypeSpecBuilder.interfaceBuilder(daoName(info))
builder.addModifiers(PUBLIC)
builder.addSuperinterface(daoBaseClass(info))
builder.addJavadoc('$L Dao \n', info.getComment())
builder.addJavadoc('@author xchao \n')

// 生成 ImplementedBy 注解
annotation = AnnotationSpec.builder(implementedByClass)
annotation.addMember('value', '$T.class', daoImplClass(info))
builder.addAnnotation(annotation.build())

// 返回JavaFile信息
return JavaFile.builder(daoPackage(info),
		builder.build()).build()
