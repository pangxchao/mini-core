import com.mini.plugin.builder.javapoet.FieldSpecBuilder
import com.mini.plugin.builder.javapoet.MethodSpecBuilder
import com.mini.plugin.builder.javapoet.TypeSpecBuilder
import com.mini.plugin.config.TableInfo
import com.squareup.javapoet.JavaFile

import static com.mini.plugin.util.GroovyUtil.*
import static javax.lang.model.element.Modifier.PRIVATE
import static javax.lang.model.element.Modifier.PUBLIC

//noinspection GrUnresolvedAccess
TableInfo info = tableInfo
MethodSpecBuilder method
TypeSpecBuilder builder
FieldSpecBuilder field

// 定义类的声明
builder = TypeSpecBuilder.classBuilder(daoImplName(info))
builder.addModifiers(PUBLIC)
builder.superclass(abstractDaoClass())
builder.addSuperinterface(daoClass(info))
builder.addJavadoc('$L Dao Impl \n', info.getComment())
builder.addJavadoc('@author xchao \n')
builder.addAnnotation(singletonClass())

// 生成 JdbcTemplate 属性
field = FieldSpecBuilder.builder(jdbcTemplateClass(), 'jdbcTemplate')
field.addAnnotation(injectClass())
field.addModifiers(PRIVATE)
builder.addField(field.build())

// 生成 writeTemplate 方法
method = MethodSpecBuilder.methodBuilder('writeTemplate')
method.addModifiers(PUBLIC)
method.returns(jdbcTemplateClass())
method.addAnnotation(Override.class)
method.addStatement('return jdbcTemplate')
builder.addMethod(method.build())

// 生成 readTemplate 方法
method = MethodSpecBuilder.methodBuilder('readTemplate')
method.addModifiers(PUBLIC)
method.returns(jdbcTemplateClass())
method.addAnnotation(Override.class)
method.addStatement('return jdbcTemplate')
builder.addMethod(method.build())

// 返回JavaFile信息
return JavaFile.builder(daoImplPackage(info),
		builder.build()).build()