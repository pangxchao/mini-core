import com.mini.plugin.extension.StringKt
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

out.println """@file:JvmName("${info.entityName}Kt") """
out.println """@file:Suppress("unused") """
out.println """ """
out.println """package ${info.entityPackage} """
out.println """ """
out.println """import com.mini.core.jdbc.support.* """

// 如果属性中的日期类型
if (info.columnList.stream().anyMatch() { it.javaType == "Date" }) {
    out.println """ """
    out.println """import com.alibaba.fastjson.annotation.JSONField """
}

// 属性类型需要导入
info.columnList.stream().filter { it.typeImport.length() > 0 }
        .map { it -> it.typeImport }.distinct().forEach {
    out.println """import ${it} """
}

// 生成表名字段常量
out.println """ """
out.println """const val ${info.tableName}: String = "${info.tableName}" """

// 生成所有字段常量
info.columnList.forEach {
    out.println """const val ${it.name}: String = "${it.name}" """
}

// 类的声明
out.println """ """
out.println """@Table(${info.tableName}) """
out.println """data class ${info.entityName} constructor( """

// 生成所有字段属性
int i = 0
info.columnList.forEach {
    if (StringUtils.isNotBlank(it.comment)) {
        out.println """    // ${it.comment} """
    }
    if (it.id) {
        out.println """    @Id """
    }
    if (it.lock) {
        out.println """    @Lock """
    }
    if (it.auto) {
        out.println """    @Auto """
    }
    if (it.del) {
        out.println """    @Del(${it.delValue}) """
    }
    if (it.updateAt) {
        out.println """    @UpdateAt """
    }
    if (it.createAt) {
        out.println """    @CreateAt """
    }
    out.println """    @Column(${it.name}) """

    //noinspection DuplicatedCode
    if (i < info.columnList.size() - 1) {
        out.println """    var ${it.fieldName}: ${it.kotlinType}?, """
    } else {
        out.println """    var ${it.fieldName}: ${it.kotlinType}? """
    }
    i++
}
out.println """) { """
out.println """ """

// 生成所有日期格式化字段
//noinspection DuplicatedCode
info.columnList.stream().filter { it.kotlinType == "Date" || it.kotlinType == "Date?" }.forEach {
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
out.println """} """
out.println """ """
out.println """ """



