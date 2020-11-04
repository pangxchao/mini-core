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
        out.println """${placeholder}import ${it}"""
    }
}

// 定义属性生成方法
@SuppressWarnings('DuplicatedCode')
static def generateProperty(String placeholder, Collection<DbColumn> columnList, BuilderWriter out) {
    int index = 0
    columnList.forEach {
        final String end = index < columnList.size() - 1 ? ", " : " "

        out.println """${placeholder}// ${defaultIfBlank(it.comment, it.fieldName)} """
        out.println """${placeholder}var ${it.fieldName}: ${it.kotlinType}?${end} """
        index++
    }
}

// 设置包名与文件名，确定生成代码的路径
//noinspection GrUnresolvedAccess
final BuilderWriter out = builderWriter
out.setPackageName("${info.packageName}.entity")
out.setFileName("${info.entityName}.kt")

// 文件包声明
out.println """@file:Suppress("unused") """
out.println """ """
out.println """package ${info.packageName}.entity """
out.println """ """

// 属性类型需要导入
generateImports("", importColumnList, out)

// 类的声明
out.println """ """
out.println """data class ${info.entityName} constructor( """

// 生成所有字段属性
generateProperty(T, info.columnList, out)

// 类声明结尾
out.println """) """
out.println """ """
out.println """ """
out.println """ """