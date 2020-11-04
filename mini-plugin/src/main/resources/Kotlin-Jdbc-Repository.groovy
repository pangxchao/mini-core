import com.mini.plugin.state.DbColumn
import com.mini.plugin.state.DbTable
import com.mini.plugin.util.BuilderWriter

import java.util.stream.Collectors

//noinspection GrUnresolvedAccess
DbTable info = tableInfo


// 获取所有ID字段
@SuppressWarnings('DuplicatedCode')
List<DbColumn> idColumnList = info.columnList.stream().filter {
    it -> it.isId()
}.collect(Collectors.toList())

// 获取ID字段的类型
@SuppressWarnings('DuplicatedCode')
String idType = idColumnList.size() > 1 ? "${info.entityName}Id" :
        idColumnList.stream().findAny().map {
            it.javaType
        }.orElse("String")

// 设置包名与文件名，确定生成代码的路径
//noinspection GrUnresolvedAccess
final BuilderWriter out = builderWriter
out.setPackageName("${info.packageName}.repository")
out.setFileName("${info.entityName}Repository.kt")

out.println """@file:Suppress("unused") """
out.println """ """
out.println """package ${info.packageName}.repository """

// 导入包
out.println """ """
out.println """import ${info.packageName}.entity.*; """
out.println """import org.springframework.data.repository.PagingAndSortingRepository; """
out.println """import org.springframework.stereotype.Repository; """
out.println """ """


out.println """interface ${info.entityName}Repository : PagingAndSortingRepository<${info.entityName}, ${idType}> {  """
out.println """ """
out.println """} """



