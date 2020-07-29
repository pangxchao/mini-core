@file:Suppress("unused")

package com.mini.core.jdbc

import com.mini.core.jdbc.builder.SQLBuilder
import com.mini.core.jdbc.model.Paging
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.util.*
import kotlin.reflect.KClass

/**
 * 数据库操作对象
 *
 * @author xchao
 */
interface JdbcInterface : EventListener {
    /**
     * 执行SQL
     *
     * @param sql    SQL
     * @param params 参数
     * @return 执行结果 - 影响条数
     */
    fun execute(sql: String, vararg params: Any?): Int

    /**
     * 执行SQL
     *
     * @param builder SQLBuilder 对象
     * @return 执行结果 - 影响条数
     */
    fun execute(builder: SQLBuilder): Int

    /**
     * 执行SQL
     *
     * @param sql    SQL
     * @param holder 执行返回结果
     * @param params 参数
     * @return 执行结果 - 影响条数
     */
    fun execute(sql: String, holder: GeneratedKeyHolder, vararg params: Any?): Int

    /**
     * 执行SQL
     *
     * @param builder SQLBuilder 对象
     * @param holder  执行返回结果
     * @return 执行结果 - 影响条数
     */
    fun execute(builder: SQLBuilder, holder: GeneratedKeyHolder): Int

    /**
     * 指执行SQL
     *
     * @param sql SQL
     * @return 执行结果 - 影响条数
     */
    fun executeBatch(vararg sql: String): IntArray

    /**
     * 指执行SQL
     *
     * @param sql       SQL
     * @param batchArgs 数据
     * @return 执行结果 - 影响条数
     */
    fun executeBatch(sql: String, batchArgs: List<Array<Any>>): IntArray

    /**
     * 指执行SQL
     *
     * @param sql       SQL
     * @param batchArgs 数据
     * @param argTypes  数据类型
     * @return 执行结果 - 影响条数
     */
    fun executeBatch(sql: String, batchArgs: List<Array<Any>>, argTypes: IntArray): IntArray

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryList(sql: String, m: RowMapper<out T?>, vararg params: Any?): List<T?>

    /**
     * 查询列表
     *
     * @param builder SQL和参数
     * @param m       映射器
     * @param <T>     解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryList(builder: SQLBuilder, m: RowMapper<out T?>): List<T?>

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryList(sql: String, type: KClass<out T>, vararg params: Any?): List<T?>

    /**
     * 查询列表
     *
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryList(builder: SQLBuilder, type: KClass<out T>): List<T?>

    /**
     * 查询列表
     *
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryListSingle(sql: String, type: KClass<out T>, vararg params: Any?): List<T?>

    /**
     * 查询列表
     *
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryListSingle(builder: SQLBuilder, type: KClass<out T>): List<T?>

    /**
     * 查询列表
     *
     * @param start  跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryList(start: Int, limit: Int, sql: String, m: RowMapper<out T?>, vararg params: Any?): List<T?>

    /**
     * 查询列表
     *
     * @param start   跳过的数据条数
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param m       映射器
     * @param <T>     解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryList(start: Int, limit: Int, builder: SQLBuilder, m: RowMapper<out T?>): List<T?>

    /**
     * 查询列表
     *
     * @param start  跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryList(start: Int, limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): List<T?>

    /**
     * 查询列表
     *
     * @param start   跳过的数据条数
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryList(start: Int, limit: Int, builder: SQLBuilder, type: KClass<out T>): List<T?>

    /**
     * 查询列表
     *
     * @param start  跳过的数据条数
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryListSingle(start: Int, limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): List<T?>

    /**
     * 查询列表
     *
     * @param start   跳过的数据条数
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryListSingle(start: Int, limit: Int, builder: SQLBuilder, type: KClass<out T>): List<T?>

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryObject(sql: String, m: RowMapper<out T?>, vararg params: Any?): T?

    /**
     * 查询对象
     *
     * @param builder SQL和参数
     * @param m       映射器
     * @param <T>     解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryObject(builder: SQLBuilder, m: RowMapper<out T?>): T?

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param type   值的类型
     * @param params 参数
     * @return 查询结果
     */
    fun <T : Any> queryObject(sql: String, type: KClass<out T>, vararg params: Any?): T?

    /**
     * 查询对象
     *
     * @param builder SQL和参数
     * @param type    值的类型
     * @return 查询结果
     */
    fun <T : Any> queryObject(builder: SQLBuilder, type: KClass<out T>): T?

    /**
     * 查询对象
     *
     * @param sql    SQL
     * @param type   值的类型
     * @param params 参数
     * @return 查询结果
     */
    fun <T : Any> queryObjectSingle(sql: String, type: KClass<out T>, vararg params: Any?): T?

    /**
     * 查询对象
     *
     * @param builder SQL和参数
     * @param type    值的类型
     * @return 查询结果
     */
    fun <T : Any> queryObjectSingle(builder: SQLBuilder, type: KClass<out T>): T?

    /**
     * 查询列表
     *
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryList(limit: Int, sql: String, m: RowMapper<out T?>, vararg params: Any?): List<T?>

    /**
     * 查询列表
     *
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param m       映射器
     * @param <T>     解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryList(limit: Int, builder: SQLBuilder, m: RowMapper<out T?>): List<T?>

    /**
     * 查询列表
     *
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryList(limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): List<T?>

    /**
     * 查询列表
     *
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryList(limit: Int, builder: SQLBuilder, type: KClass<out T>): List<T?>

    /**
     * 查询列表
     *
     * @param limit  获取指定的条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryListSingle(limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): List<T?>

    /**
     * 查询列表
     *
     * @param limit   获取指定的条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryListSingle(limit: Int, builder: SQLBuilder, type: KClass<out T>): List<T?>

    /**
     * 查询 String 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryString(sql: String, vararg params: Any?): String?

    /**
     * 查询 String 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryString(builder: SQLBuilder): String?

    /**
     * 查询 Long 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryLong(sql: String, vararg params: Any?): Long?

    /**
     * 查询 Long 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryLong(builder: SQLBuilder): Long?

    /**
     * 查询 Integer 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryInt(sql: String, vararg params: Any?): Int?

    /**
     * 查询 Integer 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryInt(builder: SQLBuilder): Int?

    /**
     * 查询 Short 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryShort(sql: String, vararg params: Any?): Short?

    /**
     * 查询 Short 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryShort(builder: SQLBuilder): Short?

    /**
     * 查询 Byte 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryByte(sql: String, vararg params: Any?): Byte?

    /**
     * 查询 Byte 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryByte(builder: SQLBuilder): Byte?

    /**
     * 查询 Double 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryDouble(sql: String, vararg params: Any?): Double?

    /**
     * 查询 Double 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryDouble(builder: SQLBuilder): Double?

    /**
     * 查询 Float 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryFloat(sql: String, vararg params: Any?): Float?

    /**
     * 查询 Float 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryFloat(builder: SQLBuilder): Float?

    /**
     * 查询 Boolean 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryBoolean(sql: String, vararg params: Any?): Boolean?

    /**
     * 查询 Boolean 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryBoolean(builder: SQLBuilder): Boolean?

    /**
     * 查询 Timestamp 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryTimestamp(sql: String, vararg params: Any?): Timestamp?

    /**
     * 查询 Timestamp 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryTimestamp(builder: SQLBuilder): Timestamp?

    /**
     * 查询 Date 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryDate(sql: String, vararg params: Any?): Date?

    /**
     * 查询 Date 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryDate(builder: SQLBuilder): Date?

    /**
     * 查询 Time 对象
     *
     * @param sql    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryTime(sql: String, vararg params: Any?): Time?

    /**
     * 查询 Time 对象
     *
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryTime(builder: SQLBuilder): Time?

    /**
     * 查询列表
     *
     * @param page   当前页数
     * @param limit  每页条数
     * @param sql    SQL
     * @param m      映射器
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryPaging(page: Int, limit: Int, sql: String, m: RowMapper<out T?>, vararg params: Any?): Paging<T?>

    /**
     * 查询列表
     *
     * @param page    当前页数
     * @param limit   每页条数
     * @param builder SQL和参数
     * @param m       映射器
     * @param <T>     解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryPaging(page: Int, limit: Int, builder: SQLBuilder, m: RowMapper<out T?>): Paging<T?>

    /**
     * 查询列表
     *
     * @param page   当前页数
     * @param limit  每页条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryPaging(page: Int, limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): Paging<T?>

    /**
     * 查询列表
     *
     * @param page    当前页数
     * @param limit   每页条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryPaging(page: Int, limit: Int, builder: SQLBuilder, type: KClass<out T>): Paging<T?>

    /**
     * 查询列表
     *
     * @param page   当前页数
     * @param limit  每页条数
     * @param sql    SQL
     * @param type   类型类对象
     * @param params 参数
     * @param <T>    解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryPagingSingle(page: Int, limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): Paging<T?>

    /**
     * 查询列表
     *
     * @param page    当前页数
     * @param limit   每页条数
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     **/
    fun <T : Any> queryPagingSingle(page: Int, limit: Int, builder: SQLBuilder, type: KClass<out T>): Paging<T?>
}