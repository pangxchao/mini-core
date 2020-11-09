//noinspection DuplicatedCode


import com.mini.plugin.state.DbColumn
import com.mini.plugin.state.DbTable
import com.mini.plugin.util.BuilderWriter

import java.util.stream.Collectors

import static com.mini.plugin.util.Constants.T
import static org.apache.commons.lang3.StringUtils.defaultIfBlank

//noinspection GrUnresolvedAccess
DbTable info = tableInfo

// 获取所有需要import语句的字段
@SuppressWarnings('DuplicatedCode')
List<DbColumn> importColumnList = info.columnList.stream().filter {
    it.typeImport.length() > 0
}.collect(Collectors.toList())

// 定义生成import语句的方法
@SuppressWarnings('DuplicatedCode')
static def generateImports(String placeholder, List<DbColumn> columnList, BuilderWriter out) {
    out.println """${placeholder}"""
    columnList.stream().map { it.typeImport }.distinct().forEach {
        out.println """${placeholder}import ${it};"""
    }
}

// 定义属性生成方法
@SuppressWarnings('DuplicatedCode')
static def generateProperty(String placeholder, Collection<DbColumn> columnList, BuilderWriter out) {
    columnList.forEach {
        out.println """${placeholder}"""
        out.println """${placeholder}// ${defaultIfBlank(it.comment, it.fieldName)} """
        out.println """${placeholder}private ${it.javaType} ${it.fieldName}; """
    }
}

@SuppressWarnings('DuplicatedCode')
static def generateGetterSetter(String placeholder, Collection<DbColumn> columnList, BuilderWriter out) {
    columnList.forEach {
        // Getter 方法
        out.println """${placeholder}"""
        out.println """${placeholder}public ${it.javaType}  ${it.getJavaGetterName()}(){ """
        out.println """${placeholder}${T}return  ${it.fieldName}; """
        out.println """${placeholder}} """

        // Setter 方法
        out.println """${placeholder}"""
        out.println """${placeholder}public void ${it.getJavaSetterName()}(${it.javaType} ${it.fieldName}){ """
        out.println """${placeholder}${T}this.${it.fieldName} = ${it.fieldName}; """
        out.println """${placeholder}} """
    }
}

// 设置包名与文件名，确定生成代码的路径
//noinspection GrUnresolvedAccess
final BuilderWriter out = builderWriter
out.setPackageName("${info.packageName}.entity")
out.setFileName("${info.entityName}.java")

// 包名和导入语句
out.println """package ${info.packageName}.entity;"""
out.println """"""
out.println """import lombok.*;"""
out.println """import lombok.experimental.*;"""
out.println """"""
out.println """import java.io.Serializable;"""
// 属性类型需要导入
generateImports("", importColumnList, out)

// 类签名
out.println """@Data"""
out.println """@SuperBuilder(toBuilder = true)"""
out.println """public class ${info.entityName} implements Serializable { """

// 生成字段属性
generateProperty(T, info.columnList, out)

// 默认构造方法
out.println """"""
out.println """@Tolerate"""
out.println """${T}public ${info.entityName}(){ """
out.println """${T}} """

// 生成Getter Setter
//generateGetterSetter(T, info.columnList, out)

// 结尾
out.println """ """
out.println """ """
out.println """}"""
out.println """ """
out.println """ """