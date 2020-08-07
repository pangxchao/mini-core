import com.mini.core.jdbc.AbstractDao
import com.mini.core.jdbc.JdbcTemplate
import com.mini.plugin.builder.javapoet.FieldSpecBuilder
import com.mini.plugin.builder.javapoet.MethodSpecBuilder
import com.mini.plugin.builder.javapoet.TypeSpecBuilder
import com.mini.plugin.config.TableInfo
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.JavaFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import static javax.lang.model.element.Modifier.*

//noinspection GrUnresolvedAccess
TableInfo info = tableInfo
MethodSpecBuilder method
TypeSpecBuilder builder
FieldSpecBuilder field

// 定义类的声明
builder = TypeSpecBuilder.classBuilder(info.daoImplName())
builder.addModifiers(PUBLIC)
builder.superclass(AbstractDao.class)
builder.addSuperinterface(info.daoClass())
builder.addJavadoc('$L Dao Impl \n', info.getComment())
builder.addJavadoc('@author xchao \n')
// org.springframework.stereotype.Repository
builder.addAnnotation(AnnotationSpec.builder(Repository.class)
        .addMember('value', '$S', info.daoName())
        .build())

// 生成 JdbcTemplate 属性
field = FieldSpecBuilder.builder(JdbcTemplate.class, 'jdbcTemplate')
field.addModifiers(PRIVATE, FINAL)
builder.addField(field.build())

// 生成自动注入的构造方法
method = MethodSpecBuilder.constructorBuilder()
method.addAnnotation(Autowired.class)
method.addModifiers(PUBLIC)
method.addParameter(JdbcTemplate.class, 'jdbcTemplate')
method.addStatement('this.jdbcTemplate = jdbcTemplate')

// 生成 writeTemplate 方法
method = MethodSpecBuilder.methodBuilder('writeTemplate')
method.addModifiers(PUBLIC)
method.returns(JdbcTemplate.class)
method.addAnnotation(Override.class)
method.addStatement('return jdbcTemplate')
builder.addMethod(method.build())

// 生成 readTemplate 方法
method = MethodSpecBuilder.methodBuilder('readTemplate')
method.addModifiers(PUBLIC)
method.returns(JdbcTemplate.class)
method.addAnnotation(Override.class)
method.addStatement('return jdbcTemplate')
builder.addMethod(method.build())

// 返回JavaFile信息
return JavaFile.builder(info.daoImplPackage(),
        builder.build()).build()