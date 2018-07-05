package sn.mini.kotlin.jdbc

import sn.mini.kotlin.jdbc.sql.Sql
import sn.mini.kotlin.util.Paging
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.util.*

interface IDao : java.sql.Connection {
    /**
     * 填充预处理sql语句
     * @param statement 预处理sql 对象
     * @param params statement 对象中 sql对应的参数值
     */
    fun fullPreparedStatement(statement: PreparedStatement, vararg params: Any)

    /**
     * 批量SQL操作实现方法<br></br>
     * @param sql 预编译SQL语句
     * @param length 数据长度
     * @param values 回调接口
     */
    fun batch(sql: String, length: Int, values: ((index: Int) -> Array<Any>)): IntArray

    /**
     * 批量SQL操作实现方法
     * @param sql
     * @param params
     * @return
     */
    fun batch(sql: String, params: Array<Array<Any>>): IntArray

    /**
     * 批量SQL操作实现方法<br></br>
     * @param sql 预编译SQL语句
     * @param length 数据长度
     * @param values 回调接口
     */
    fun batch(sql: Sql, length: Int, values: (index: Int) -> Array<Any>): IntArray

    /**
     * 批量SQL操作实现方法
     * @param sql
     * @param params
     * @return
     */
    fun batch(sql: Sql, params: Array<Array<Any>>): IntArray

    /**
     * 执行insert,replace,update,delete语句,返回影响数据库记录的行数
     * @param sql
     * @param params
     * @return
     */
    fun execute(sql: String, vararg params: Any): Int

    /**
     * 执行insert,replace语句, 返回数据库自增ID
     * @param sql
     * @param params
     * @return 返回自增长ID值
     */
    fun executeGen(sql: String, vararg params: Any): Long

    /**
     * 执行insert,replace,update,delete语句,返回影响数据库记录的行数
     * @param sql
     * @return
     */
    fun execute(sql: Sql): Int

    /**
     * 执行insert,replace语句, 返回数据库自增ID
     * @param sql
     * @return 返回自增长ID值
     */
    fun executeGen(sql: Sql): Long

    /**
     * 生成SQL结果: insert into user_info(coumn1, column2, column3) values(?, ?, ?)
     * @param tableName 表名称 比如: user_info
     * @param keys 比如: coumn1, column2, column3
     * @param params 比如: va1, va2, va3 分别对应三个字段的值
     * @return
     */
    fun insert(tableName: String, keys: Array<String>, vararg params: Any): Int

    /**
     * 生成SQL结果: insert into user_info(coumn1, column2, column3) values(?, ?, ?)
     * @param tableName 表名称 比如: user_info
     * @param keys 比如: coumn1, column2, column3
     * @param params 比如: va1, va2, va3 分别对应三个字段的值
     * @return 返回自增长ID值
     */
    fun insertGen(tableName: String, keys: Array<String>, vararg params: Any): Long

    /**
     * 生成SQL结果: replace into user_info(coumn1, column2, column3) values(?, ?, ?)
     * @param tableName 表名称 比如: user_info
     * @param keys 比如: coumn1, column2, column3
     * @param params 比如: va1, va2, va3 分别对应三个字段的值
     * @return
     */
    fun replace(tableName: String, keys: Array<String>, vararg params: Any): Int

    /**
     * 生成SQL结果: replace into user_info(coumn1, column2, column3) values(?, ?, ?)
     * @param tableName 表名称 比如: user_info
     * @param keys 比如: coumn1, column2, column3
     * @param params 比如: va1, va2, va3 分别对应三个字段的值
     * @return 返回自增长ID值
     */
    fun replaceGen(tableName: String, keys: Array<String>, vararg params: Any): Long

    /**
     * 生成SQL结果: update coumn1 = ?, column2 = ?, column3 = ? where coumn4 = ? and column5 = ?
     * @param tableName 表名称 比如: user_info
     * @param keys 比如: coumn1, column2, column3
     * @param wheres 比如: coumn4, column5 默认用and方式连接
     * @param params 比如: va1, va2, va3, va4, va5, 其中前三个对应keys的值, 后两个对应wheres的值
     * @return
     */
    fun update(tableName: String, keys: Array<String>, wheres: Array<String>, vararg params: Any): Int

    /**
     * 生成SQL结果: delete user_info where coumn1 = ? and column2 = ? and column3 = ?
     * @param tableName 表名称 比如: user_info
     * @param wheres 比如: column1, column2, column3 默认用and方式连接
     * @param params 比如: va1, va2, va3 分别对应三个字段值
     * @return 影响数据库的条数
     */
    fun delete(tableName: String, wheres: Array<String>, vararg params: Any): Int

    /**
     * 生成SQL结果: delete user_info where coumn1 = ? and column2 = ? and column3 = ?
     * @param tableName 表名称 比如: user_info
     * @param wheres 条件字段，一般用于ID字段
     * @param params 比如: va1, va2, va3 分别对应三个字段值
     * @return 影响数据库的条数
     */
    fun delete(tableName: String, wheres: String, vararg params: Any): Int

    /**
     * 查询记录, 根据sql中所有带条查询数据库所有匹配的记录<br></br>
     * 调用该方法时, 一定要手动关闭ResultSet结果集,否则导致无法关闭连接
     * @param row 回调解析过程
     * @param sql sql语句
     * @param params sql语句是的参数
     * @return
     */
    fun <T> executeQuery(row: (rs: ResultSet) -> T?, sql: String, vararg params: Any): T

    /**
     * 查询记录, 根据sql所所带条件查询数据库所有匹配的记录<br></br>
     * 调用该方法时, 一定要手动关闭ResultSet结果集,否则导致无法关闭连接
     * @param row 回调解析过程
     * @param sql sql封装器
     * @return
     */
    fun <T> executeQuery(row: (rs: ResultSet) -> T?, sql: Sql): T?

    /**
     * 查询记录, 根据sql所所带条件查询数据库记录按分页工具分页<br></br>
     * 调用该方法时, 一定要手动关闭ResultSet结果集,否则导致无法关闭连接
     * @param row 回调解析过程
     * @param paging 分页工具
     * @param sql sql语句
     * @param params sql语句是的参数
     * @return
     */
    fun <T> executeQuery(row: (rs: ResultSet) -> T?, paging: Paging, sql: String, vararg params: Any): T?

    /**
     * 查询记录, 根据sql所所带条件查询数据库记录按分页工具分页<br></br>
     * 调用该方法时, 一定要手动关闭ResultSet结果集,否则导致无法关闭连接
     * @param row 回调解析过程
     * @param paging 分页工具
     * @param sql sql封装器
     * @return
     */
    fun <T> executeQuery(row: (rs: ResultSet) -> T?, paging: Paging, sql: Sql): T?

    /**
     * 查询记录， 根据sql中所带的条件查询数据库所有匹配的记录全部返回
     * @param sql sql封装工具
     * @return
     */
    fun query(sql: Sql): ArrayList<HashMap<String, Any>?>

    /**
     * 根据 查询所有记录
     * @param sql
     * @param params
     * @return
     */
    fun query(sql: String, vararg params: Any): ArrayList<HashMap<String, Any>?>

    /**
     * 查询记录， 根据sql中所带的条件查询数据库所有匹配的记录全部返回
     * @param clazz 实体对象class 为了节约性能 ，实体对象属性中不能有复合对象，只有能简单数据 类型，否则组装失败
     * @param sql 查询记录， 根据sql中所带的条件查询数据库所有匹配的记录全部返回
     * @return 查询结果
     */
    fun <T> query(clazz: Class<T>, sql: Sql): List<T?>

    /**
     * 查询所有记录
     * @param clazz 实体对象class. 为了节约性能 ，实体对象属性中不能有复合对象，只有能简单数据 类型，否则组装失败
     * @param sql 查询sql语句
     * @param params
     * @return
     */
    fun <T> query(clazz: Class<T>, sql: String, vararg params: Any): List<T?>

    /**
     * 查询记录， 根据sql中所带的条件查询数据库所有匹配的记录全部返回, 该方法中sql中的cellMapper无效
     * @param mapper
     * @param sql
     * @return
     */
    fun <T> query(mapper: (rs: ResultSet) -> T?, sql: Sql): List<T?>

    /**
     * 查询所有数据
     * @param mapper
     * @param sql
     * @param params
     * @return
     */
    fun <T> query(mapper: (rs: ResultSet) -> T?, sql: String, vararg params: Any): List<T?>

    /**
     * 查询记录， 根据sql中所带的条件与Paging工具分页查询数据库
     * @param paging 分页工具
     * @param sql sql封装工具
     * @return 查询结果 @see com.ms.mvc.util.JSONArrays
     */
    fun query(paging: Paging, sql: Sql): ArrayList<HashMap<String, Any>?>

    /**
     * 分页查询数据
     * @param paging
     * @param sql
     * @param params
     * @return
     */
    fun query(paging: Paging, sql: String, vararg params: Any): ArrayList<HashMap<String, Any>?>

    /**
     * 查询记录， 根据sql中所带的条件与Paging工具分页查询数据库
     * @param paging sql 分页工具
     * @param clazz 实体对象class. 为了节约性能 ，实体对象属性中不能有复合对象，只有能简单数据 类型，否则组装失败
     * @param sql
     * @return
     */
    fun <T> query(paging: Paging, clazz: Class<T>, sql: Sql): List<T?>

    /**
     * 查询所有记录
     * @param paging
     * @param clazz 实体对象class. 为了节约性能 ，实体对象属性中不能有复合对象，只有能简单数据 类型，否则组装失败
     * @param sql
     * @param params
     * @return
     */
    fun <T> query(paging: Paging, clazz: Class<T>, sql: String, vararg params: Any): List<T?>

    /**
     * 查询记录， 根据sql中所带的条件查询数据库所有匹配的记录全部返回, 该方法中sql中的cellMapper无效
     * @param paging
     * @param mapper
     * @param sql
     * @return
     */
    fun <T> query(paging: Paging, mapper: (rs: ResultSet) -> T?, sql: Sql): List<T?>

    /**
     * 分页查询数据
     * @param paging
     * @param mapper
     * @param sql
     * @param params
     * @return
     */
    fun <T> query(paging: Paging, mapper: (rs: ResultSet) -> T?, sql: String, vararg params: Any): List<T?>

    /**
     * 查询单条记录
     * @param sql sql封装工具
     * @return 查询结果 @see com.ms.mvc.util.JSONArrays
     */
    fun queryOne(sql: Sql): HashMap<String, Any>?

    /**
     * 查询单条记录
     * @param sql
     * @param params
     * @return
     */
    fun queryOne(sql: String, vararg params: Any): HashMap<String, Any>?

    /**
     * 查询单条记录
     * @param  clazz 为了节约性能 ，实体对象属性中不能有复合对象，只有能简单数据 类型，否则组装失败
     * @param sql  根据sql中所带的条件查询数据库所有匹配的记录全部返回
     * @return 查询结果
     */
    fun <T> queryOne(clazz: Class<T>, sql: Sql): T?

    /**
     * 查询单条记录
     * @param clazz 实体对象class. 为了节约性能 ，实体对象属性中不能有复合对象，只有能简单数据 类型，否则组装失败
     * @param sql
     * @param params
     * @return
     */
    fun <T> queryOne(clazz: Class<T>, sql: String, vararg params: Any): T?

    /**
     * 查询单条记录 该方法中sql中的cellMapper无效
     * @param mapper
     * @param sql
     * @return
     */
    fun <T> queryOne(mapper: (rs: ResultSet) -> T?, sql: Sql): T?

    /**
     * 查询单条记录
     * @param mapper
     * @param sql
     * @param params
     * @return
     */
    fun <T> queryOne(mapper: (rs: ResultSet) -> T?, sql: String, vararg params: Any): T?

    /**
     * 查询单一 String 数据值
     * @param sql
     */
    fun queryString(sql: Sql): String?

    /**
     * 查询单一 String 数据值
     * @param sql
     * @param params
     * @return
     */
    fun queryString(sql: String, vararg params: Any): String?

    /**
     * 查询单一 Long 数据值
     * @param sql
     */
    fun queryLong(sql: Sql): Long

    /**
     * 查询单一 Long 数据值
     * @param sql
     * @param params
     * @return
     */
    fun queryLong(sql: String, vararg params: Any): Long

    /**
     * 查询单一 int 数据值
     * @param sql
     */
    fun queryInt(sql: Sql): Int

    /**
     * 查询单一 int 数据值
     * @param sql
     * @param params
     * @return
     */
    fun queryInt(sql: String, vararg params: Any): Int

    /**
     * 查询单一 short 数据值
     * @param sql
     */
    fun queryShort(sql: Sql): Short

    /**
     * 查询单一 short 数据值
     * @param sql
     * @param params
     * @return
     */
    fun queryShort(sql: String, vararg params: Any): Short

    /**
     * 查询单一 byte 数据值
     * @param sql
     */
    fun queryByte(sql: Sql): Byte

    /**
     * 查询单一 short 数据值
     * @param sql
     * @param params
     * @return
     */
    fun queryByte(sql: String, vararg params: Any): Byte

    /**
     * 查询单一 double 数据值
     * @param sql
     */
    fun queryDouble(sql: Sql): Double

    /**
     * 查询单一 double 数据值
     * @param sql
     * @param params
     * @return
     */
    fun queryDouble(sql: String, vararg params: Any): Double

    /**
     * 查询单一 float 数据值
     * @param sql
     */
    fun queryFloat(sql: Sql): Float

    /**
     * 查询单一 float 数据值
     * @param sql
     * @param params
     * @return
     */
    fun queryFloat(sql: String, vararg params: Any): Float

    /**
     * 查询单一 date 数据值
     * @param sql
     */
    fun queryDate(sql: Sql): Date

    /**
     * 查询单一 date 数据值
     * @param sql
     * @param params
     * @return
     */
    fun queryDate(sql: String, vararg params: Any): Date?

    /**
     * 查询单一 boolean 数据值
     * @param sql
     */
    fun queryBoolean(sql: Sql): Boolean

    /**
     * 查询单一 boolean 数据值
     * @param sql
     * @param params
     * @return
     */
    fun queryBoolean(sql: String, vararg params: Any): Boolean

    /**
     * 事件执行方法
     * @param transaction
     * @return true: 事务执行成功(最后提交事务), false: 事务执行失败, 最后回滚
     */
    fun transaction(transaction: (() -> Boolean)): Boolean

    /**
     * 事件执行方法
     * @param transLevel 事务级别
     * @param transaction
     * @return true: 事务执行成功(最后提交事务), false: 事务执行失败, 最后回滚
     */
    fun transaction(transLevel: Int, transaction: (() -> Boolean)): Boolean

}