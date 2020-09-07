import com.mini.plugin.state.DbTable
import com.mini.plugin.extension.StringKt
import com.mini.plugin.util.BuilderWriter

// 接收表配置信息
//noinspection GrUnresolvedAccess
DbTable info = tableInfo

// 设置包名与文件名，确定生成代码的路径
//noinspection GrUnresolvedAccess
final BuilderWriter out = builderWriter
out.setPackageName(info.daoImplPackage)
out.setFileName("${info.daoImplName}.kt")

out.println """ @file:Suppress("unused") """
out.println """ """
out.println """ package ${info.daoImplPackage} """
out.println """ """
out.println """ import ${info.daoPackage}.${info.daoName} """
out.println """ """
out.println """ import org.springframework.beans.factory.annotation.Autowired """
out.println """ import org.springframework.stereotype.Repository """
out.println """ """
out.println """ import com.mini.core.jdbc.* """
out.println """ """
out.println """ @Repository("${StringKt.firstLowerCase(info.daoName)}") """
out.println """ class ${info.daoImplName}  @Autowired constructor( """
out.println """     private val jdbcTemplate:JdbcTemplate """
out.println """ ) : AbstractDao(), ${info.daoName} { """
out.println """     override fun writeTemplate(): JdbcTemplate { """
out.println """         return this.jdbcTemplate """
out.println """     } """
out.println """ """
out.println """     override fun readTemplate(): JdbcTemplate { """
out.println """         return this.jdbcTemplate """
out.println """     } """
out.println """ } """









