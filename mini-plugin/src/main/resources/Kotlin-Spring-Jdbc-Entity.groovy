import com.mini.plugin.state.DbTable
import com.mini.plugin.util.BuilderWriter
import org.apache.commons.lang3.StringUtils

//noinspection GrUnresolvedAccess
DbTable info = tableInfo

// 设置包名与文件名，确定生成代码的路径
//noinspection GrUnresolvedAccess
final BuilderWriter out = builderWriter
out.setPackageName(info.entityPackage)
out.setFileName("${info.entityName}.kt")

out.println """@file:Suppress("unused") """
out.println """ """
out.println """package ${info.entityPackage} """
out.println """ """
out.println """import import org.springframework.data.relational.core.mapping.* """

// 属性类型需要导入
info.columnList.stream().filter { it.typeImport.length() > 0 }
        .map { it -> it.typeImport }.distinct().forEach {
    out.println """import ${it} """
}

// 类的声明
out.println """ """
out.println """@Table("${info.tableName}") """
out.println """data class ${info.entityName} constructor( """
// 生成所有字段属性
int i = 0
//noinspection DuplicatedCode
info.columnList.forEach {
    if (StringUtils.isNotBlank(it.comment)) {
        out.println """    // ${it.comment} """
    }
    out.println """    @Column("${it.columnName}") """
    //noinspection DuplicatedCode
    if (i < info.columnList.size() - 1) {
        out.println """    var ${it.fieldName}: ${it.kotlinType}?, """
    } else {
        out.println """    var ${it.fieldName}: ${it.kotlinType}? """
    }
    i++
}
out.println """)"""
out.println """ """
out.println """ """



