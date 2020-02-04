import com.mini.plugin.config.TableInfo
import com.mini.plugin.util.GroovyUtil
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec

import static javax.lang.model.element.Modifier.PUBLIC

ClassName implementedByClass = ClassName.get('com.google.inject', 'ImplementedBy')

//noinspection GrUnresolvedAccess
TableInfo info = tableInfo

return JavaFile.builder(GroovyUtil.daoPackage(info),
	TypeSpec.interfaceBuilder(GroovyUtil.daoName(info))
		.addModifiers(PUBLIC)
		.addSuperinterface(GroovyUtil.daoBaseClass(info))
		.addJavadoc('$L Dao \n', info.getComment())
		.addJavadoc('@author xchao \n')
		.addAnnotation(AnnotationSpec.builder(implementedByClass)
			.addMember('value', '$T.class', GroovyUtil.daoImplClass(info))
			.build())
		.build())
	.build()
