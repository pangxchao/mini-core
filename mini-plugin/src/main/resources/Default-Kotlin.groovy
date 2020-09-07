import com.mini.plugin.extension.StringKt
import com.mini.plugin.state.DbTable
import com.mini.plugin.util.BuilderWriter
import org.apache.commons.lang3.StringUtils

//noinspection GrUnresolvedAccess
DbTable info = tableInfo

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
info.columnList.stream().filter { it.typeImport.length() > 0 }
        .map { it -> it.typeImport }.distinct().forEach {
    out.println """import ${it} """
}

// 类的声明
out.println """ """
out.println """data class ${info.entityName} constructor( """
// 生成所有字段属性
int i = 0
//noinspection DuplicatedCode
info.columnList.forEach {
    if (StringUtils.isNotBlank(it.comment)) {
        out.println """    // ${it.comment} """
    }
    //noinspection DuplicatedCode
    if (i < info.columnList.size() - 1) {
        out.println """    var ${it.fieldName}: ${it.kotlinType}?, """
    } else {
        out.println """    var ${it.fieldName}: ${it.kotlinType}? """
    }
    i++
}
out.println """) {"""

// 生成所有日期格式化字段
//noinspection DuplicatedCode
info.columnList.stream().filter { it.kotlinType == "Date" }.forEach {
    out.println """ """
    out.println """     @JSONField(format="yyyy-MM-dd HH:mm:ss") """
    out.println """     fun get${StringKt.firstUpperCase(it.fieldName)}_DT(): ${it.kotlinType} { """
    out.println """         return this.${it.fieldName}; """
    out.println """     } """
    out.println """ """
    out.println """     @JSONField(format="yyyy-MM-dd") """
    out.println """     fun get${StringKt.firstUpperCase(it.fieldName)}_D(): ${it.kotlinType} { """
    out.println """         return this.${it.fieldName}; """
    out.println """     } """
}
out.println """ """
out.println """ """
out.println """ }"""