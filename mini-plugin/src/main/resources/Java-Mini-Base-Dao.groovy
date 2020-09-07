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
out.setPackageName("${info.packageName}.dao.base")
out.setFileName("${info.entityName}BaseDao.java")

out.println """package ${info.packageName}.dao.base; """
out.println """ """
out.println """import ${info.packageName}.entity.${info.entityName}; """
out.println """ """
out.println """import com.mini.core.jdbc.builder.*; """
out.println """import com.mini.core.jdbc.*; """
out.println """ """
out.println """public interface ${info.entityName}BaseDao extends JdbcInterface { """
out.println """ """
out.println """    default <T> int insertOnUpdate(T instance) { """
out.println """        return this.execute(new SQLBuilder(it -> { """
out.println """            it.ON_DUPLICATE_KEY_UPDATE(instance); """
out.println """        }));   """
out.println """    }    """
out.println """ """
out.println """    default <T> int replace(T instance) { """
out.println """        return this.execute(new SQLBuilder(it -> { """
out.println """            it.INSERT_INTO(instance); """
out.println """        })); """
out.println """    } """
out.println """ """
out.println """    default <T> int insert(T instance) { """
out.println """        return this.execute(new SQLBuilder(it -> { """
out.println """            it.INSERT_INTO(instance); """
out.println """        })); """
out.println """    } """
out.println """ """
out.println """    default <T> int delete(T instance) { """
out.println """        return this.execute(new SQLBuilder(it -> { """
out.println """            it.DELETE(instance); """
out.println """        })); """
out.println """    } """
out.println """ """
out.println """    default <T> int update(T instance) { """
out.println """        return this.execute(new SQLBuilder(it -> {  """
out.println """            it.UPDATE(instance); """
out.println """        })); """
out.println """    } """
out.println """ """

// 获取所有的主键列
List<DbColumn> idColumnList = info.columnList.stream().filter {
    it.isId()
}.collect(Collectors.toList())

// 生成["类型 名称"，"类型 名称"，"类型 名称"]这样的数组
String[] idParamsList = idColumnList.stream().map {
    return it.javaType + " " + it.fieldName
}.toArray { new String[it] }

// WHERE条件生成器
List<String> idWhereList = new ArrayList<>()
idColumnList.forEach {
    idWhereList.add("""it.WHERE(${info.entityName}.${it.name} + " = ?"); """)
    idWhereList.add("""it.ARGS(${it.fieldName}); """)
}

out.println """    default int deleteById(${idParamsList.join(", ")}) { """
out.println """        return this.execute(new SQLBuilder(it -> { """
out.println """            it.DELETE().FROM(${info.entityName}.${info.tableName}); """
out.println """            ${idWhereList.join('\n\t            ')} """
out.println """        })); """
out.println """    }"""
out.println """ """
out.println """    default ${info.entityName} queryById(${idParamsList.join(", ")}) { """
out.println """        return this.queryObject(new SQLBuilder(it ->  { """
out.println """            it.SELECT_FROM_JOIN(${info.entityName}.class); """
out.println """            ${idWhereList.join('\n\t           ')} """
out.println """        }), ${info.entityName}.class); """
out.println """    } """
out.println """ """
out.println """} """