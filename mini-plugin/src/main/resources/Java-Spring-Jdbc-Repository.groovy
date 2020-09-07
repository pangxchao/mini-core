import com.mini.plugin.state.DbTable
import com.mini.plugin.util.BuilderWriter

// 接收表配置信息
//noinspection GrUnresolvedAccess
DbTable info = tableInfo

// 设置包名与文件名，确定生成代码的路径
//noinspection GrUnresolvedAccess
final BuilderWriter out = builderWriter
out.setPackageName("${info.packageName}.dao")
out.setFileName("${info.entityName}Dao.java")

out.println """package ${info.packageName}.dao; """
out.println """ """
out.println """import ${info.packageName}.dao.base.${info.entityName}BaseDao; """
out.println """import org.springframework.data.repository.PagingAndSortingRepository; """
out.println """import org.springframework.stereotype.Repository; """
out.println """ """

String idType
if (info.columnList.stream().filter { it -> it.isId() }.count() == 1) {
    idType = info.columnList.stream().filter { it -> it.isId() }
            .map { it -> it.javaType }.findAny()
            .orElse("String")
} else {
    idType = "${info.entityName}.ID"
}

out.println """public interface ${info.entityName}Repository extends PagingAndSortingRepository<${info.entityName}, ${idType}> {  """
out.println """ """
out.println """} """







