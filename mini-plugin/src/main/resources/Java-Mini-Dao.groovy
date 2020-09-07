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
out.println """ """
out.println """public interface ${info.entityName}Dao extends ${info.entityName}BaseDao {  """
out.println """ """
out.println """} """
