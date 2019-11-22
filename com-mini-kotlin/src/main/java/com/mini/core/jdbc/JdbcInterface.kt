package com.mini.core.jdbc

import com.mini.core.jdbc.builder.SQLBuilder
import com.mini.core.jdbc.model.Paging
import java.sql.*
import java.sql.Date
import java.util.*

/**
 * 数据库操作对象
 * @author xchao
 */
interface JdbcInterface : EventListener {
    /**
     * 指执行SQL
     * @param str    SQL
     * @param setter 预处理设置器
     * @return 执行结果
     */
    fun executeBatch(str: String, setter: PreparedStatementSetter): IntArray

    /**
     * 执行SQL
     * @param str    SQL
     * @param params 参数
     * @return 执行结果
     */
    fun execute(str: String, params: Array<Any?>?): Int

    /**
     * 执行SQL
     * @param builder SQLBuilder 对象
     * @return 执行结果
     */
    fun execute(builder: SQLBuilder): Int {
        return execute(builder.toString(), builder.toArray())
    }

    /**
     * 执行SQL
     * @param holder 执行返回数据
     * @param str    SQL
     * @param params 参数
     * @return 执行结果
     */
    fun execute(holder: HolderGenerated, str: String, params: Array<Any?>?): Int

    /**
     * 执行SQL
     * @param holder  执行返回数据
     * @param builder SQLBuilder 对象
     * @return 执行结果
     */
    fun execute(holder: HolderGenerated, builder: SQLBuilder): Int {
        return execute(holder, builder.toString(), builder.toArray())
    }

    /**
     * 查询结果
     * @param str      查询SQL
     * @param params   查询SQL中的参数
     * @param callback 查询回调方法
     * @param <T>      查询结果返回类型
     * @return 查询结果
     */
    fun <T> query(str: String, params: Array<Any?>, callback: (t: ResultSet) -> T?): T?

    /**
     * 查询结果
     * @param builder  查询SQLBuilder对象
     * @param callback 查询回调方法
     * @param <T>      查询结果返回类型
     * @return 查询结果
     */
    fun <T> query(builder: SQLBuilder, callback: (t: ResultSet) -> T?): T? {
        return query(builder.toString(), builder.toArray(), callback)
    }

    /**
     * 查询列表
     * @param str    SQL
     * @param params 参数
     * @param m      映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    fun <T> queryList(str: String, params: Array<Any?>, m: (rs: ResultSet, number: Int) -> T?): List<T?> {
        return this@JdbcInterface.query(str, params) { r: ResultSet ->
            val result: MutableList<T?> = arrayListOf()
            while (r.next()) result.add(m(r, r.row))
            return@query result
        } ?: arrayListOf()
    }

    /**
     * 查询列表
     * @param builder SQL和参数
     * @param m       映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    fun <T> queryList(builder: SQLBuilder, m: (rs: ResultSet, number: Int) -> T?): List<T?> {
        return queryList(builder.toString(), builder.toArray(), m)
    }

    /**
     * 查询列表
     * @param str    SQL
     * @param params 参数
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    fun <T> queryList(str: String, params: Array<Any?>, type: Class<T>): List<T?> {
        return queryList(str, params) { rs: ResultSet, _: Int ->
            rs.getObject(1)?.let { type.cast(it) }
        }
    }

    /**
     * 查询列表
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    fun <T> queryList(builder: SQLBuilder, type: Class<T>): List<T?> {
        return queryList(builder.toString(), builder.toArray(), type)
    }

    /**
     * 查询列表
     * @param start  查询起始位置
     * @param limit  查询条数
     * @param str    SQL
     * @param params 参数
     * @param m      解析器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    fun <T> queryList(start: Int, limit: Int, str: String, params: Array<Any?>, m: (rs: ResultSet, number: Int) -> T?): List<T?>

    /**
     * 查询列表
     * @param start   查询起始位置
     * @param limit   查询条数
     * @param builder SQL和参数
     * @param m       解析器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    fun <T> queryList(start: Int, limit: Int, builder: SQLBuilder, m: (rs: ResultSet, number: Int) -> T?): List<T?> {
        return queryList(start, limit, builder.toString(), builder.toArray(), m)
    }

    /**
     * 查询列表
     * @param limit  查询条数
     * @param str    SQL
     * @param params 参数
     * @param m      解析器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    fun <T> queryList(limit: Int, str: String, params: Array<Any?>, m: (rs: ResultSet, number: Int) -> T?): List<T?> {
        return queryList(0, limit, str, params, m)
    }

    /**
     * 查询列表
     * @param limit   查询条数
     * @param m       解析器
     * @param builder SQL和参数
     * @param <T>     解析器类型
     * @return 查询结果
     */
    fun <T> queryList(limit: Int, builder: SQLBuilder, m: (rs: ResultSet, number: Int) -> T?): List<T?> {
        return queryList(limit, builder.toString(), builder.toArray(), m)
    }

    /**
     * 查询列表
     * @param paging 分页器
     * @param str    SQL
     * @param params 参数
     * @param m      解析器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    fun <T> queryList(paging: Paging, str: String, params: Array<Any?>, m: (rs: ResultSet, number: Int) -> T?): List<T?>


    /**
     * 查询列表
     * @param paging  分页器
     * @param builder SQL和参数
     * @param m       解析器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    fun <T> queryList(paging: Paging, builder: SQLBuilder, m: (rs: ResultSet, number: Int) -> T?): List<T?> {
        return queryList(paging, builder.toString(), builder.toArray(), m)
    }

    /**
     * 查询列表
     * @param start  查询起始位置
     * @param limit  查询条数
     * @param str    SQL
     * @param params 参数
     * @param type    列表数据类型
     * @param <T>    解析器类型
     * @return 查询结果
     */
    fun <T> queryList(start: Int, limit: Int, str: String, params: Array<Any?>, type: Class<T>): List<T?> {
        return queryList(start, limit, str, params) { rs: ResultSet, _: Int ->
            rs.getObject(1)?.let { type.cast(it) }
        }
    }

    /**
     * 查询列表
     * @param start   查询起始位置
     * @param limit   查询条数
     * @param builder SQL和参数
     * @param type       列表数据类型
     * @param <T>     解析器类型
     * @return 查询结果
     */
    fun <T> queryList(start: Int, limit: Int, builder: SQLBuilder, type: Class<T>): List<T?> {
        return queryList(start, limit, builder.toString(), builder.toArray(), type)
    }

    /**
     * 查询列表
     * @param limit  查询条数
     * @param str    SQL
     * @param params 参数
     * @param type      列表数据类型
     * @param <T>    解析器类型
     * @return 查询结果
     */
    fun <T> queryList(limit: Int, str: String, params: Array<Any?>, type: Class<T>): List<T?> {
        return queryList(0, limit, str, params, type)
    }

    /**
     * 查询列表
     * @param limit   查询条数
     * @param builder SQL和参数
     * @param type       类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    fun <T> queryList(limit: Int, builder: SQLBuilder, type: Class<T>): List<T?> {
        return queryList(limit, builder.toString(), builder.toArray(), type)
    }

    /**
     * 查询列表
     * @param paging 分页器
     * @param str    SQL
     * @param params 参数
     * @param type   类型类对象
     * @param <T>    解析器类型
     * @return 查询结果
     */
    fun <T> queryList(paging: Paging, str: String, params: Array<Any?>, type: Class<T>): List<T?> {
        return queryList(paging.skip, paging.limit, str, params, type)
    }


    /**
     * 查询列表
     * @param paging  分页器
     * @param builder SQL和参数
     * @param type    类型类对象
     * @param <T>     解析器类型
     * @return 查询结果
     */
    fun <T> queryList(paging: Paging, builder: SQLBuilder, type: Class<T>): List<T?> {
        return queryList(paging.skip, paging.limit, builder, type)
    }


    /**
     * 查询对象
     * @param str    SQL
     * @param params 参数
     * @param m      映射器
     * @param <T>    解析器类型
     * @return 查询结果
     */
    fun <T> queryObject(str: String, params: Array<Any?>?, m: (rs: ResultSet, number: Int) -> T): T?

    /**
     * 查询对象
     * @param builder SQL和参数
     * @param m       映射器
     * @param <T>     解析器类型
     * @return 查询结果
     */
    fun <T> queryObject(builder: SQLBuilder, m: (rs: ResultSet, number: Int) -> T?): T? {
        return queryObject(builder.toString(), builder.toArray(), m)
    }

    /**
     * 查询对象
     * @param str    SQL
     * @param type   值的类型
     * @param params 参数
     * @return 查询结果
     */
    fun <T> queryObject(str: String, params: Array<Any?>, type: Class<T>): T? {
        return queryObject(str, params) { rs: ResultSet, _: Int ->
            rs.getObject(1)?.let { type.cast(it) }
        }
    }

    /**
     * 查询对象
     * @param builder SQL和参数
     * @param type    值的类型
     * @return 查询结果
     */
    fun <T> queryObject(builder: SQLBuilder, type: Class<T>): T? {
        return queryObject(builder.toString(), builder.toArray(), type)
    }

    /**
     * 查询 String 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryString(str: String, params: Array<Any?>): String? {
        return queryObject(str, params, String::class.java)
    }

    /**
     * 查询 String 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryString(builder: SQLBuilder): String? {
        return queryString(builder.toString(), builder.toArray())
    }

    /**
     * 查询 Long 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryLong(str: String, params: Array<Any?>): Long? {
        return queryObject(str, params, Long::class.java)
    }

    /**
     * 查询 Long 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryLong(builder: SQLBuilder): Long? {
        return queryLong(builder.toString(), builder.toArray())
    }

    /**
     * 查询 long 值
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryLongVal(str: String, params: Array<Any?>): Long {
        return queryObject(str, params, Long::class.java) ?: 0
    }

    /**
     * 查询 long 值
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryLongVal(builder: SQLBuilder): Long {
        return queryObject(builder, Long::class.java) ?: 0
    }

    /**
     * 查询 Integer 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryInt(str: String, params: Array<Any?>): Int? {
        return queryObject(str, params, Int::class.java)
    }

    /**
     * 查询 Integer 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryInt(builder: SQLBuilder): Int? {
        return queryInt(builder.toString(), builder.toArray())
    }

    /**
     * 查询 int 值
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryIntVal(str: String, params: Array<Any?>): Int {
        return queryObject(str, params, Int::class.java) ?: 0
    }

    /**
     * 查询 int 值
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryIntVal(builder: SQLBuilder): Int {
        return queryObject(builder, Int::class.java) ?: 0
    }

    /**
     * 查询 Short 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryShort(str: String, params: Array<Any?>): Short? {
        return queryObject(str, params, Short::class.java)
    }

    /**
     * 查询 Short 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryShort(builder: SQLBuilder): Short? {
        return queryShort(builder.toString(), builder.toArray())
    }

    /**
     * 查询 short 值
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryShortVal(str: String, params: Array<Any?>): Short {
        return queryObject(str, params, Short::class.java) ?: 0
    }

    /**
     * 查询 short 值
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryShortVal(builder: SQLBuilder): Short {
        return queryObject(builder, Short::class.java) ?: 0
    }

    /**
     * 查询 Byte 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryByte(str: String, params: Array<Any?>): Byte? {
        return queryObject(str, params, Byte::class.java)
    }

    /**
     * 查询 Byte 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryByte(builder: SQLBuilder): Byte? {
        return queryByte(builder.toString(), builder.toArray())
    }

    /**
     * 查询 byte 值
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryByteVal(str: String, params: Array<Any?>): Byte {
        return queryObject(str, params, Byte::class.java) ?: 0
    }

    /**
     * 查询 byte 值
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryByteVal(builder: SQLBuilder): Byte {
        return queryObject(builder, Byte::class.java) ?: 0
    }

    /**
     * 查询 Double 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryDouble(str: String, params: Array<Any?>): Double? {
        return queryObject(str, params, Double::class.java)
    }

    /**
     * 查询 Double 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryDouble(builder: SQLBuilder): Double? {
        return queryDouble(builder.toString(), builder.toArray())
    }

    /**
     * 查询 double 值
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryDoubleVal(str: String, params: Array<Any?>): Double {
        return queryDouble(str, params) ?: 0.toDouble()
    }

    /**
     * 查询 double 值
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryDoubleVal(builder: SQLBuilder): Double {
        return queryDouble(builder) ?: 0.toDouble()
    }

    /**
     * 查询 Float 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryFloat(str: String, params: Array<Any?>): Float? {
        return queryObject(str, params, Float::class.java)
    }

    /**
     * 查询 Float 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryFloat(builder: SQLBuilder): Float? {
        return queryFloat(builder.toString(), builder.toArray())
    }

    /**
     * 查询 float 值
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryFloatVal(str: String, params: Array<Any?>): Float {
        return queryFloat(str, params) ?: 0.toFloat()
    }

    /**
     * 查询 float 值
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryFloatVal(builder: SQLBuilder): Float {
        return queryFloat(builder) ?: 0.toFloat()
    }

    /**
     * 查询 Boolean 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryBoolean(str: String, params: Array<Any?>): Boolean? {
        return queryObject(str, params, Boolean::class.java)
    }

    /**
     * 查询 Boolean 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryBoolean(builder: SQLBuilder): Boolean? {
        return queryBoolean(builder.toString(), builder.toArray())
    }

    /**
     * 查询 boolean 值
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryBooleanVal(str: String, params: Array<Any?>): Boolean {
        return queryBoolean(str, params) ?: false
    }

    /**
     * 查询 boolean 值
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryBooleanVal(builder: SQLBuilder): Boolean {
        return queryBoolean(builder) ?: false
    }

    /**
     * 查询 Timestamp 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryTimestamp(str: String, params: Array<Any?>): Timestamp? {
        return queryObject(str, params, Timestamp::class.java)
    }

    /**
     * 查询 Timestamp 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryTimestamp(builder: SQLBuilder): Timestamp? {
        return queryTimestamp(builder.toString(), builder.toArray())
    }

    /**
     * 查询 Date 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryDate(str: String, params: Array<Any?>): Date? {
        return queryObject(str, params, Date::class.java)
    }

    /**
     * 查询 Date 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryDate(builder: SQLBuilder): Date? {
        return queryDate(builder.toString(), builder.toArray())
    }

    /**
     * 查询 Time 对象
     * @param str    SQL
     * @param params 参数
     * @return 查询结果
     */
    fun queryTime(str: String, params: Array<Any?>): Time? {
        return queryObject(str, params, Time::class.java)
    }


    /**
     * 查询 Time 对象
     * @param builder SQL和参数
     * @return 查询结果
     */
    fun queryTime(builder: SQLBuilder): Time? {
        return queryTime(builder.toString(), builder.toArray())
    }

    /**
     * 查询结果集回调处理
     * @param <T> 返回类型
     * @author xchao
     */
    @FunctionalInterface
    interface ResultSetCallback<T> {
        @Throws(SQLException::class)
        fun apply(t: ResultSet): T
    }

    /**
     * 指处理参数设置
     * @author xchao
     */
    interface PreparedStatementSetter {
        @Throws(SQLException::class)
        fun setValues(ps: PreparedStatement, i: Int)

        @Suppress("unused")
        val batchSize: Int
    }

    /**
     * 执行获取返回值处理
     * @param <T>
     * @author xchao
     */
    interface Holder<T> : EventListener {
        @Throws(SQLException::class)
        fun setValue(rs: ResultSet)

        fun getValue(): T
    }

    /**
     * 执行获取自增长ID返回
     * @author xchao
     */
    class HolderGenerated : Holder<Long> {
        private var value = 0L
        @Throws(SQLException::class)
        override fun setValue(rs: ResultSet) {
            value = if (rs.next()) {
                rs.getLong(1)
            } else 0L
        }

        fun setValue(value: Long) {
            this.value = value
        }

        override fun getValue(): Long {
            return value
        }
    }
}