import com.mini.plugin.state.DbColumn
import com.mini.plugin.state.DbTable
import com.mini.plugin.util.BuilderWriter

import java.util.stream.Collectors

import static com.mini.plugin.util.Constants.T
import static org.apache.commons.lang3.StringUtils.defaultIfBlank

//noinspection GrUnresolvedAccess
DbTable info = tableInfo

// 获取所有ID字段
@SuppressWarnings('DuplicatedCode')
List<DbColumn> idColumnList = info.columnList.stream().filter {
    it -> it.isId()
}.collect(Collectors.toList())

// 获取所有不是主键的字段
@SuppressWarnings('DuplicatedCode')
List<DbColumn> otherColumnList = info.columnList.stream().filter {
    it -> !it.isId()
}.collect(Collectors.toList())

// 获取所有需要import语句的字段
@SuppressWarnings('DuplicatedCode')
List<DbColumn> importColumnList = info.columnList.stream().filter {
    it.typeImport.length() > 0
}.collect(Collectors.toList())

// 定义生成import语句的方法
@SuppressWarnings('DuplicatedCode')
static def generateImports(String placeholder, List<DbColumn> columnList, BuilderWriter out) {
    out.println """${placeholder}"""
    columnList.stream().map {
        it.typeImport
    }.distinct().forEach {
        out.println """${placeholder}import ${it};"""
    }
}

// 定义属性生成的方法
@SuppressWarnings('DuplicatedCode')
static def generateProperty(String placeholder, Collection<DbColumn> columnList, BuilderWriter out, boolean hid) {
    columnList.forEach {
        out.println """${placeholder}"""
        out.println """${placeholder}// ${defaultIfBlank(it.comment, it.fieldName)} """
        if (hid && it.isId()) {
            out.println """${placeholder}@Id """
        }
        out.println """${placeholder}@Column(name = "${it.name}") """
        out.println """${placeholder}private ${it.javaType} ${it.fieldName}; """
    }
}

// 定义Getter Setter生成的方法
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

out.println """package ${info.packageName}.entity;"""
out.println """ """
out.println """import lombok.*; """
out.println """import lombok.experimental.*; """
out.println """import javax.persistence.*;"""
out.println """import org.springframework.data.relational.core.mapping.*; """
out.println """ """
out.println """import java.io.Serializable; """

// 属性类型需要包导入
generateImports("", importColumnList, out)

// 类签名
out.println """"""
out.println """@Entity """
out.println """@Table(name = "${info.tableName}") """
out.println """public class ${info.entityName} implements Serializable { """

// 多主键生成主键类
//noinspection DuplicatedCode
if (idColumnList != null && idColumnList.size() > 1) {
    out.println """ """
    out.println """${T}public static class ID implements Serializable { """
    // 生成ID类的属性
    generateProperty("${T}${T}", idColumnList, out, false)
    // 主键字段 Getter Setter 方法
    generateGetterSetter("${T}${T}", idColumnList, out)
    out.println """${T}} """
}

// 多主键时，生成主所在字段的属性
//noinspection DuplicatedCode
if (idColumnList != null && idColumnList.size() > 1) {
    // 主键属性
    out.println """${T}"""
    out.println """${T}@Id """
    out.println """${T}private ID  id; """
    // 生成其它字段的属性
    generateProperty(T, otherColumnList, out, false)
}
// 单主键或者没有主键时，生成字段属性
else {
    generateProperty(T, info.columnList, out, true)
}

// 默认构造方法
out.println """"""
out.println """${T}public ${info.entityName}(){ """
out.println """${T}} """

// 多主键时，生成主所在字段的Getter Setter
//noinspection DuplicatedCode
if (idColumnList != null && idColumnList.size() > 1) {
    // 主键的Getter方法
    out.println """${T}"""
    out.println """${T}public ID getId() {"""
    out.println """${T}${T}return id; """
    out.println """${T}} """
    // 主键的 Setter 方法
    out.println """${T}"""
    out.println """${T}public void setId(ID id) {"""
    out.println """${T}${T}this.id = id; """
    out.println """${T}} """
    // 生成其它字段的Getter Setter 方法
    generateGetterSetter(T, otherColumnList, out)
}
// 单主键或者没有主键时，生成字段属性
else {
    generateGetterSetter(T, info.columnList, out)
}

// 结尾
out.println """ """
out.println """ """
out.println """}"""
out.println """ """
out.println """ """

