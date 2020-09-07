import com.mini.plugin.state.DbTable
import com.mini.plugin.util.BuilderWriter

// 接收表配置信息
//noinspection GrUnresolvedAccess
DbTable info = tableInfo

// 设置包名与文件名，确定生成代码的路径
//noinspection GrUnresolvedAccess
final BuilderWriter out = builderWriter
out.setPackageName(info.daoPackage)
out.setFileName("${info.daoName}.kt")

out.println """package ${info.daoPackage} """
out.println """ """
out.println """import ${info.baseDaoPackage}.${info.baseDaoName} """
out.println """ """
out.println """interface ${info.daoName} : ${info.baseDaoName} { """
out.println """ """
out.println """}  """
out.println """ """
out.println """ """








