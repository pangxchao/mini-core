import com.mini.plugin.state.DbColumn
import com.mini.plugin.state.DbTable
import com.mini.plugin.util.BuilderWriter

import java.util.stream.Collectors

// 接收表配置信息
//noinspection GrUnresolvedAccess
DbTable info = tableInfo

// 设置包名与文件名，确定生成代码的路径
//noinspection GrUnresolvedAccess
final BuilderWriter out = builderWriter
out.setPackageName(info.baseDaoPackage)
out.setFileName("${info.baseDaoName}.kt")

out.println """@file:Suppress("unused") """
out.println """ """
out.println """package ${info.baseDaoPackage} """
out.println """ """
out.println """import ${info.entityPackage}.${info.entityName} """
out.println """import ${info.entityPackage}.* """
out.println """ """
out.println """import com.mini.core.jdbc.builder.* """
out.println """import com.mini.core.jdbc.* """
out.println """ """
out.println """interface ${info.baseDaoName} : JdbcInterface { """
out.println """ """
out.println """    fun <T : Any> insertOnUpdate(instance: T): Int { """
out.println """        return this.execute(SQLBuilder { """
out.println """            ON_DUPLICATE_KEY_UPDATE(instance) """
out.println """        })   """
out.println """    }    """
out.println """ """
out.println """    fun <T : Any> replace(instance: T): Int { """
out.println """        return this.execute(SQLBuilder { """
out.println """            INSERT_INTO(instance) """
out.println """        }) """
out.println """    } """
out.println """ """
out.println """    fun <T : Any> insert(instance: T): Int { """
out.println """        return this.execute(SQLBuilder { """
out.println """            INSERT_INTO(instance) """
out.println """        }) """
out.println """    } """
out.println """ """
out.println """    fun <T : Any> delete(instance: T): Int { """
out.println """        return this.execute(SQLBuilder { """
out.println """            DELETE(instance) """
out.println """        }) """
out.println """    } """
out.println """ """
out.println """    fun <T : Any> update(instance: T): Int { """
out.println """        return this.execute(SQLBuilder { """
out.println """            UPDATE(instance) """
out.println """        }) """
out.println """    } """
out.println """ """

// 获取所有的主键列
List<DbColumn> idColumnList = info.columnList.stream().filter {
    it.isId()
}.collect(Collectors.toList())

// 生成["类型 名称"，"类型 名称"，"类型 名称"]这样的数组
String[] idParamsList = idColumnList.stream().map {
    return it.fieldName + ": " + it.kotlinType
}.toArray { new String[it] }

// WHERE条件生成器
List<String> idWhereList = new ArrayList<>()
idColumnList.forEach {
    idWhereList.add("""WHERE("\$${it.name} = ?") """)
    idWhereList.add("""ARGS(${it.fieldName})""")
}

out.println """    fun deleteById(${idParamsList.join(", ")}): Int { """
out.println """        return this.execute(SQLBuilder {"""
out.println """            DELETE().FROM(${info.tableName}) """
out.println """            ${idWhereList.join('\n\t            ')}"""
out.println """        })"""
out.println """    }"""
out.println """ """
out.println """    fun queryById(${idParamsList.join(", ")}): ${info.entityName}? { """
out.println """        return this.queryObject(SQLBuilder { """
out.println """            SELECT_FROM_JOIN(${info.entityName}::class.java) """
out.println """            ${idWhereList.join('\n\t           ')} """
out.println """        }, ${info.entityName}::class.java) """
out.println """    } """
out.println """ """
out.println """} """
out.println """ """
out.println """ """
out.println """ """
out.println """ """
out.println """ """