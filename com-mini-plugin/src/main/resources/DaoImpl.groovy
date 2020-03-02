import com.mini.plugin.builder.javapoet.MethodSpecBuilder
import com.mini.plugin.builder.javapoet.TypeSpecBuilder
import com.mini.plugin.config.TableInfo
import com.mini.plugin.util.GroovyUtil
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile

import static com.mini.plugin.util.StringUtil.firstLowerCase
import static javax.lang.model.element.Modifier.PRIVATE
import static javax.lang.model.element.Modifier.PUBLIC

//noinspection GrUnresolvedAccess
TableInfo info = tableInfo

return JavaFile.builder(GroovyUtil.daoImplPackage(info),
		TypeSpecBuilder.classBuilder(GroovyUtil.daoImplName(info))
				.addModifiers(PUBLIC)
				.superclass(GroovyUtil.abstractDaoClass())
				.addSuperinterface(GroovyUtil.daoClass(info))
				.addJavadoc('$L Dao Impl \n', info.getComment())
				.addJavadoc('@author xchao \n')
				.addAnnotation(GroovyUtil.singletonClass())
				.addAnnotation(AnnotationSpec.builder(GroovyUtil.namedClass())
						.addMember('value', '$S', firstLowerCase(GroovyUtil.daoName(info)))
						.build())
				.addField(FieldSpec.builder(GroovyUtil.jdbcTemplateClass(), 'jdbcTemplate', PRIVATE)
						.addAnnotation(GroovyUtil.injectClass())
						.build())
				.addMethod(MethodSpecBuilder
						.methodBuilder('writeTemplate')
						.addModifiers(PUBLIC)
						.returns(GroovyUtil.jdbcTemplateClass())
						.addAnnotation(Override.class)
						.addStatement('return jdbcTemplate').build())
				.addMethod(MethodSpecBuilder
						.methodBuilder('readTemplate')
						.addModifiers(PUBLIC)
						.returns(GroovyUtil.jdbcTemplateClass())
						.addAnnotation(Override.class)
						.addStatement('return jdbcTemplate')
						.build())
				.build())
		.build()