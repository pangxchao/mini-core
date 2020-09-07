//noinspection DuplicatedCode

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
out.setFileName("${info.entityName}.java")

// 包名和导入语句
out.println """package ${info.packageName}.entity;"""
out.println """ """
out.println """import java.io.Serializable;"""
out.println """ """
out.println """import lombok.*; """
out.println """import lombok.experimental.*; """

// 属性类型需要导入
info.columnList.stream().filter { it.typeImport.length() > 0 }
        .map { it -> it.typeImport }.distinct().forEach {
    out.println """import ${it}; """
}

// 类签名
out.println """@Data """
out.println """@SuperBuilder(toBuilder = true) """
out.println """public class ${info.entityName} implements Serializable { """

// 生成字段属性
info.columnList.forEach {
    out.println """ """
    if (StringUtils.isNotBlank(it.comment)) {
        out.println """     // ${it.comment} """
    }
    out.println """    private ${it.javaType}  ${it.fieldName}; """
}

// 默认构造方法
out.println """ """
out.println """    @Tolerate """
out.println """    public ${info.entityName}(){} """
out.println """ """

// 生成所有日期格式化字段
//noinspection DuplicatedCode
info.columnList.stream().filter { it.javaType == "Date" }.forEach {
    out.println """ """
    out.println """     @JSONField(format="yyyy-MM-dd HH:mm:ss") """
    out.println """     public ${it.javaType} get${StringKt.firstUpperCase(it.fieldName)}_DT() { """
    out.println """         return this.${it.fieldName}; """
    out.println """     } """
    out.println """ """
    out.println """     @JSONField(format="yyyy-MM-dd") """
    out.println """     public ${it.javaType} get${StringKt.firstUpperCase(it.fieldName)}_D() { """
    out.println """         return this.${it.fieldName}; """
    out.println """     } """
}

// 结尾
out.println """ """
out.println """ """
out.println """}"""
out.println """ """
out.println """ """