import com.mini.plugin.config.ColumnInfo
import com.mini.plugin.config.TableInfo
import com.mini.plugin.util.StringUtil

import java.util.stream.Collectors

//noinspection GrUnresolvedAccess
TableInfo info = tableInfo

List<ColumnInfo> idColumnList = info.columnMap.values().stream().filter {
    it.isId()
}.collect(Collectors.toList())

// 生成["类型 名称"，"类型 名称"，"类型 名称"]这样的数组
String[] idParamsList = idColumnList.stream().map {
    return "${it.fieldName}: ${it.dataType.javaType} "
}.toArray { new String[it] }

List<String> idWhereList = new ArrayList<>()
idColumnList.forEach {
    idWhereList.add("""WHERE("\$${info.beanName()}.${it.columnName} = ?")""")
    idWhereList.add("""ARGS(${it.fieldName})""")
}

//noinspection GrUnresolvedAccess
out.println """
package ${info.daoBasePackage()}

import ${info.beanPackage()}.${info.beanName()}

import com.mini.core.jdbc.builder.*
//import com.mini.core.jdbc.support.*
import com.mini.core.jdbc.*

interface ${info.daoBaseName()} : JdbcInterface { 

    fun <T : Any> insertOnUpdate(instance: T): Int { 
        return this.execute(SQLBuilder {
            ON_DUPLICATE_KEY_UPDATE(instance)
        })
    }

    fun <T : Any> replace(instance: T): Int { 
        return this.execute(SQLBuilder {
            INSERT_INTO(instance)
        })
    }

    fun <T : Any> insert(instance: T): Int { 
        return this.execute(SQLBuilder {
            INSERT_INTO(instance)
        })
    }

    fun <T : Any> update(instance: T): Int { 
        return this.execute(SQLBuilder {
            UPDATE(instance)
        })
    }

    fun <T : Any> delete(instance: T): Int { 
        return this.execute(SQLBuilder {
            DELETE(instance)
        })
    }
    
    fun deleteById(${StringUtil.join(idParamsList, ', ')}): Int {
        return this.execute(SQLBuilder {
            DELETE().FROM(${info.beanName()}.${info.tableName})
            ${StringUtil.join(idWhereList, '\n            ')}
        })
    }

    fun queryById(${StringUtil.join(idParamsList, ', ')}): Int {
        return this.queryObject(SQLBuilder {
            SELECT_FROM_JOIN(${info.beanName()}::class)
            ${StringUtil.join(idWhereList, '\\n            ')}
        }, ${info.beanName()}::class)
    }
    
}
"""







