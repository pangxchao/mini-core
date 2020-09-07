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

out.println """package ${info.packageName}.entity;"""
out.println """ """
out.println """import java.io.Serializable;"""
out.println """import com.mini.core.jdbc.support.*;"""
out.println """ """
out.println """import lombok.*;"""
out.println """import lombok.experimental.*;"""
out.println """ """

// 属性类型需要导入
info.columnList.stream().filter { it.typeImport.length() > 0 }
        .map { it -> it.typeImport }.distinct().forEach {
    out.println """import ${it}; """
}

// 如果属性中的日期类型
if (info.columnList.stream().anyMatch() { it.javaType == "Date" }) {
    out.println """ """
    out.println """import com.alibaba.fastjson.annotation.JSONField;"""
}

out.println """@Data """
out.println """@SuperBuilder(toBuilder = true) """
out.println """@Table(${info.entityName}.${info.tableName}) """
out.println """public class ${info.entityName} implements Serializable { """

out.println """     @Comment("${info.comment}") """
out.println """     public static final String ${info.tableName} = "${info.tableName}"; """

// 生成字段常量
info.columnList.forEach {
    out.println """     @Comment("${it.comment}") """
    out.println """     public static final String ${it.name} = "${it.name}"; """
}

// 生成字段属性
info.columnList.forEach {
    out.println """ """
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
    out.println """    private ${it.javaType}  ${it.fieldName}; """
}

out.println """ """
out.println """   @Tolerate"""
out.println """   public ${info.entityName}(){} """

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
out.println """ """
out.println """ """
out.println """}"""
out.println """ """
out.println """ """

