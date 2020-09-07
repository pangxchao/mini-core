import com.mini.plugin.state.DbTable
import com.mini.plugin.extension.StringKt
import com.mini.plugin.util.BuilderWriter

// 接收表配置信息
//noinspection GrUnresolvedAccess
DbTable info = tableInfo

// 设置包名与文件名，确定生成代码的路径
//noinspection GrUnresolvedAccess
final BuilderWriter out = builderWriter
out.setPackageName("${info.packageName}.dao.impl")
out.setFileName("${info.entityName}DaoImpl.java")

out.println """package ${info.entityName}.dao.impl; """
out.println """ """
out.println """import ${info.packageName}.dao.${info.entityName}Dao; """
out.println """ """
out.println """import org.jetbrains.annotations.NotNull; """
out.println """import org.springframework.beans.factory.annotation.Autowired; """
out.println """import org.springframework.stereotype.Repository; """
out.println """ """
out.println """import com.mini.core.jdbc.*;"""
out.println """ """
out.println """@Repository("${StringKt.firstLowerCase(info.entityName)}Dao") """
out.println """public class ${info.entityName}DaoImpl extends AbstractDao implements ${info.entityName}Dao { """
out.println """ """
out.println """    @NotNull """
out.println """    private final JdbcTemplate jdbcTemplate; """
out.println """ """
out.println """    @Autowired"""
out.println """    public ${info.entityName}DaoImpl(@NotNull JdbcTemplate jdbcTemplate) { """
out.println """        this.jdbcTemplate = jdbcTemplate; """
out.println """    } """
out.println """ """
out.println """    @NotNull """
out.println """    @Override """
out.println """    public JdbcTemplate writeTemplate() { """
out.println """        return this.jdbcTemplate; """
out.println """    } """
out.println """ """
out.println """    @NotNull """
out.println """    @Override """
out.println """    public JdbcTemplate readTemplate() { """
out.println """        return this.jdbcTemplate; """
out.println """    } """
out.println """} """
out.println """ """







