package com.mini.core.jdbc

import com.mini.core.jdbc.builder.SQLBuilder
import com.mini.core.jdbc.model.BeanMapper
import com.mini.core.jdbc.model.Paging
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.*
import java.sql.Statement.RETURN_GENERATED_KEYS
import javax.sql.DataSource
import kotlin.reflect.KClass

/**
 * 数据库操作对象
 *
 * @author xchao
 */
abstract class JdbcTemplate @JvmOverloads constructor(
        dataSource: DataSource,
        lazyInit: Boolean = true
) : org.springframework.jdbc.core.JdbcTemplate(dataSource, lazyInit), JdbcInterface {

    final override fun execute(sql: String, vararg params: Any?): Int {
        return super.update(sql, *params)
    }

    final override fun execute(builder: SQLBuilder): Int {
        return execute(builder.SQL, builder.ARGS)
    }

    final override fun execute(sql: String, holder: GeneratedKeyHolder, vararg params: Any?): Int {
        return update({ con: Connection -> con.prepareStatement(sql, RETURN_GENERATED_KEYS) }, holder)
    }

    final override fun execute(builder: SQLBuilder, holder: GeneratedKeyHolder): Int {
        return execute(builder.SQL, holder, builder.ARGS)
    }

    final override fun executeBatch(vararg sql: String): IntArray {
        return super.batchUpdate(*sql)
    }

    final override fun executeBatch(sql: String, batchArgs: List<Array<Any>>): IntArray {
        return super.batchUpdate(sql, batchArgs)
    }

    final override fun executeBatch(sql: String, batchArgs: List<Array<Any>>, argTypes: IntArray): IntArray {
        return super.batchUpdate(sql, batchArgs, argTypes)
    }

    final override fun <T : Any> queryList(sql: String, m: RowMapper<out T?>, vararg params: Any?): List<T?> {
        return super.query(sql, params, m)
    }

    final override fun <T : Any> queryList(builder: SQLBuilder, m: RowMapper<out T?>): List<T?> {
        return queryList(builder.SQL, m, builder.ARGS)
    }

    final override fun <T : Any> queryList(sql: String, type: KClass<out T>, vararg params: Any?): List<T?> {
        return queryList(sql, BeanMapper.create(type), *params)
    }

    final override fun <T : Any> queryList(builder: SQLBuilder, type: KClass<out T>): List<T?> {
        return queryList(builder, BeanMapper.create(type))
    }

    final override fun <T : Any> queryListSingle(sql: String, type: KClass<out T>, vararg params: Any?): List<T?> {
        return super.queryForList(sql, type.java, *params) ?: listOf<T>()
    }

    final override fun <T : Any> queryListSingle(builder: SQLBuilder, type: KClass<out T>): List<T?> {
        return queryListSingle(builder.SQL, type, builder.ARGS)
    }

    /**
     * 根据分页参数，组装分页查询SQL
     *
     * @param start 查询起始位置
     * @param limit 查询条数
     * @param sql   基础查询SQL
     * @return 分页查询SQL
     */
    internal abstract fun paging(start: Int, limit: Int, sql: String): String

    final override fun <T : Any> queryObject(sql: String, m: RowMapper<out T?>, vararg params: Any?): T? {
        return super.queryForObject(paging(0, 1, sql), params, m)
    }

    final override fun <T : Any> queryObject(builder: SQLBuilder, m: RowMapper<out T?>): T? {
        return queryObject(builder.SQL, m, builder.ARGS)
    }

    final override fun <T : Any> queryObject(sql: String, type: KClass<out T>, vararg params: Any?): T? {
        return queryObject(sql, BeanMapper.create(type), *params)
    }

    final override fun <T : Any> queryObject(builder: SQLBuilder, type: KClass<out T>): T? {
        return queryObject(builder, BeanMapper.create(type))
    }

    final override fun <T : Any> queryObjectSingle(sql: String, type: KClass<out T>, vararg params: Any?): T? {
        return super.queryForObject(sql, params, type.java)
    }

    final override fun <T : Any> queryObjectSingle(builder: SQLBuilder, type: KClass<out T>): T? {
        return queryObject(builder.SQL, type, builder.ARGS)
    }

    final override fun <T : Any> queryList(start: Int, limit: Int, sql: String, m: RowMapper<out T?>, vararg params: Any?): List<T?> {
        return queryList(paging(start, limit, sql), m, *params)
    }

    final override fun <T : Any> queryList(start: Int, limit: Int, builder: SQLBuilder, m: RowMapper<out T?>): List<T?> {
        return queryList(start, limit, builder.SQL, m, builder.ARGS)
    }

    final override fun <T : Any> queryList(start: Int, limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): List<T?> {
        return queryList(paging(start, limit, sql), type, *params)
    }

    final override fun <T : Any> queryList(start: Int, limit: Int, builder: SQLBuilder, type: KClass<out T>): List<T?> {
        return queryList(start, limit, builder.SQL, type, builder.ARGS)
    }

    final override fun <T : Any> queryListSingle(start: Int, limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): List<T?> {
        return queryListSingle(paging(start, limit, sql), type, *params)
    }

    final override fun <T : Any> queryListSingle(start: Int, limit: Int, builder: SQLBuilder, type: KClass<out T>): List<T?> {
        return queryListSingle(start, limit, builder.SQL, type, builder.ARGS)
    }

    final override fun <T : Any> queryList(limit: Int, sql: String, m: RowMapper<out T?>, vararg params: Any?): List<T?> {
        return queryList(0, limit, sql, m, *params)
    }

    final override fun <T : Any> queryList(limit: Int, builder: SQLBuilder, m: RowMapper<out T?>): List<T?> {
        return queryList(0, limit, builder, m)
    }

    final override fun <T : Any> queryList(limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): List<T?> {
        return queryList(0, limit, sql, type, params)
    }

    final override fun <T : Any> queryList(limit: Int, builder: SQLBuilder, type: KClass<out T>): List<T?> {
        return queryList(0, limit, builder, type)
    }

    final override fun <T : Any> queryListSingle(limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): List<T?> {
        return queryListSingle(0, limit, sql, type, params)
    }

    final override fun <T : Any> queryListSingle(limit: Int, builder: SQLBuilder, type: KClass<out T>): List<T?> {
        return queryListSingle(0, limit, builder, type)
    }

    final override fun queryString(sql: String, vararg params: Any?): String? {
        return queryObjectSingle(sql, String::class, *params)
    }

    final override fun queryString(builder: SQLBuilder): String? {
        return queryObjectSingle(builder, String::class)
    }

    final override fun queryLong(sql: String, vararg params: Any?): Long? {
        return queryObjectSingle(sql, Long::class, *params)
    }

    final override fun queryLong(builder: SQLBuilder): Long? {
        return queryObjectSingle(builder, Long::class)
    }

    final override fun queryInt(sql: String, vararg params: Any?): Int? {
        return queryObjectSingle(sql, Int::class, *params)
    }

    final override fun queryInt(builder: SQLBuilder): Int? {
        return queryObjectSingle(builder, Int::class)
    }

    final override fun queryShort(sql: String, vararg params: Any?): Short? {
        return queryObjectSingle(sql, Short::class, *params)
    }

    final override fun queryShort(builder: SQLBuilder): Short? {
        return queryObjectSingle(builder, Short::class)
    }

    final override fun queryByte(sql: String, vararg params: Any?): Byte? {
        return queryObjectSingle(sql, Byte::class, *params)
    }

    final override fun queryByte(builder: SQLBuilder): Byte? {
        return queryObjectSingle(builder, Byte::class)
    }

    final override fun queryDouble(sql: String, vararg params: Any?): Double? {
        return queryObjectSingle(sql, Double::class, *params)
    }

    final override fun queryDouble(builder: SQLBuilder): Double? {
        return queryObjectSingle(builder, Double::class)
    }

    final override fun queryFloat(sql: String, vararg params: Any?): Float? {
        return queryObjectSingle(sql, Float::class, *params)
    }

    final override fun queryFloat(builder: SQLBuilder): Float? {
        return queryObjectSingle(builder, Float::class)
    }

    final override fun queryBoolean(sql: String, vararg params: Any?): Boolean? {
        return queryObjectSingle(sql, Boolean::class, *params)
    }

    final override fun queryBoolean(builder: SQLBuilder): Boolean? {
        return queryObjectSingle(builder, Boolean::class)
    }

    final override fun queryTimestamp(sql: String, vararg params: Any?): Timestamp? {
        return queryObjectSingle(sql, Timestamp::class, *params)
    }

    final override fun queryTimestamp(builder: SQLBuilder): Timestamp? {
        return queryObjectSingle(builder, Timestamp::class)
    }

    final override fun queryDate(sql: String, vararg params: Any?): Date? {
        return queryObjectSingle(sql, Date::class, *params)
    }

    final override fun queryDate(builder: SQLBuilder): Date? {
        return queryObjectSingle(builder, Date::class)
    }

    final override fun queryTime(sql: String, vararg params: Any?): Time? {
        return queryObjectSingle(sql, Time::class, *params)
    }

    final override fun queryTime(builder: SQLBuilder): Time? {
        return queryObjectSingle(builder, Time::class)
    }

    /**
     * 返回查询分页总条数的SQL
     *
     * @param sql SQL
     * @return 查询总条数的SQL
     */
    internal abstract fun totals(sql: String): String

    final override fun <T : Any> queryPaging(page: Int, limit: Int, sql: String, m: RowMapper<out T?>, vararg params: Any?): Paging<T?> {
        return Paging<T?>(page, limit).apply {
            val pSql = paging(start, this.limit, sql)
            total = queryInt(totals(sql), *params) ?: 0
            rows = queryList(pSql, m, *params)
        }
    }

    final override fun <T : Any> queryPaging(page: Int, limit: Int, builder: SQLBuilder, m: RowMapper<out T?>): Paging<T?> {
        return queryPaging(page, limit, builder.SQL, m, builder.ARGS)
    }

    final override fun <T : Any> queryPaging(page: Int, limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): Paging<T?> {
        return Paging<T?>(page, limit).apply {
            val pSql = paging(start, this.limit, sql)
            total = queryInt(totals(sql), *params) ?: 0
            rows = queryList(pSql, type, *params)
        }
    }

    final override fun <T : Any> queryPaging(page: Int, limit: Int, builder: SQLBuilder, type: KClass<out T>): Paging<T?> {
        return queryPaging(page, limit, builder.SQL, type, builder.ARGS)
    }

    final override fun <T : Any> queryPagingSingle(page: Int, limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): Paging<T?> {
        return Paging<T?>(page, limit).apply {
            val pSql = paging(start, this.limit, sql)
            total = queryInt(totals(pSql), *params) ?: 0
            rows = queryListSingle(sql, type, *params)
        }
    }

    final override fun <T : Any> queryPagingSingle(page: Int, limit: Int, builder: SQLBuilder, type: KClass<out T>): Paging<T?> {
        return queryPaging(page, limit, builder.SQL, type, builder.ARGS)
    }
}