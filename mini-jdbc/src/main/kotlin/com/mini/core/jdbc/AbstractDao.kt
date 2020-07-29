@file:Suppress("unused")

package com.mini.core.jdbc

import com.mini.core.jdbc.builder.SQLBuilder
import com.mini.core.jdbc.model.Paging
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import kotlin.reflect.KClass

abstract class AbstractDao : JdbcInterface {
    internal abstract fun writeTemplate(): JdbcTemplate
    internal abstract fun readTemplate(): JdbcTemplate

    final override fun execute(sql: String, vararg params: Any?): Int {
        return writeTemplate().execute(sql, *params)
    }

    final override fun execute(builder: SQLBuilder): Int {
        return writeTemplate().execute(builder)
    }

    final override fun execute(sql: String, holder: GeneratedKeyHolder, vararg params: Any?): Int {
        return writeTemplate().execute(sql, holder, *params)
    }

    final override fun execute(builder: SQLBuilder, holder: GeneratedKeyHolder): Int {
        return writeTemplate().execute(builder, holder)
    }

    final override fun executeBatch(vararg sql: String): IntArray {
        return writeTemplate().executeBatch(*sql)
    }

    final override fun executeBatch(sql: String, batchArgs: List<Array<Any>>): IntArray {
        return writeTemplate().executeBatch(sql, batchArgs)
    }

    final override fun executeBatch(sql: String, batchArgs: List<Array<Any>>, argTypes: IntArray): IntArray {
        return writeTemplate().executeBatch(sql, batchArgs, argTypes)
    }

    final override fun <T : Any> queryList(sql: String, m: RowMapper<out T?>, vararg params: Any?): List<T?> {
        return readTemplate().queryList(sql, m, *params)
    }

    final override fun <T : Any> queryList(builder: SQLBuilder, m: RowMapper<out T?>): List<T?> {
        return readTemplate().queryList(builder, m)
    }

    final override fun <T : Any> queryList(sql: String, type: KClass<out T>, vararg params: Any?): List<T?> {
        return readTemplate().queryList(sql, type, *params)
    }

    final override fun <T : Any> queryList(builder: SQLBuilder, type: KClass<out T>): List<T?> {
        return readTemplate().queryList(builder, type)
    }


    final override fun <T : Any> queryListSingle(sql: String, type: KClass<out T>, vararg params: Any?): List<T?> {
        return readTemplate().queryListSingle(sql, type, *params)
    }


    final override fun <T : Any> queryListSingle(builder: SQLBuilder, type: KClass<out T>): List<T?> {
        return queryListSingle(builder.SQL, type, builder.ARGS)
    }

    final override fun <T : Any> queryObject(sql: String, m: RowMapper<out T?>, vararg params: Any?): T? {
        return readTemplate().queryObject(sql, m, *params)
    }

    final override fun <T : Any> queryObject(builder: SQLBuilder, m: RowMapper<out T?>): T? {
        return readTemplate().queryObject(builder, m)
    }

    final override fun <T : Any> queryObject(sql: String, type: KClass<out T>, vararg params: Any?): T? {
        return readTemplate().queryObject(sql, type, *params)
    }

    final override fun <T : Any> queryObject(builder: SQLBuilder, type: KClass<out T>): T? {
        return readTemplate().queryObject(builder, type)
    }

    final override fun <T : Any> queryObjectSingle(sql: String, type: KClass<out T>, vararg params: Any?): T? {
        return readTemplate().queryObjectSingle(sql, type, *params)
    }

    final override fun <T : Any> queryObjectSingle(builder: SQLBuilder, type: KClass<out T>): T? {
        return readTemplate().queryObjectSingle(builder, type)
    }

    final override fun <T : Any> queryList(start: Int, limit: Int, sql: String, m: RowMapper<out T?>, vararg params: Any?): List<T?> {
        return readTemplate().queryList(start, limit, sql, m, *params)
    }

    final override fun <T : Any> queryList(start: Int, limit: Int, builder: SQLBuilder, m: RowMapper<out T?>): List<T?> {
        return readTemplate().queryList(start, limit, builder, m)
    }

    final override fun <T : Any> queryList(start: Int, limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): List<T?> {
        return readTemplate().queryList(start, limit, sql, type, *params)
    }

    final override fun <T : Any> queryList(start: Int, limit: Int, builder: SQLBuilder, type: KClass<out T>): List<T?> {
        return readTemplate().queryList(start, limit, builder, type)
    }


    final override fun <T : Any> queryListSingle(start: Int, limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): List<T?> {
        return readTemplate().queryListSingle(start, limit, sql, type, *params)
    }


    final override fun <T : Any> queryListSingle(start: Int, limit: Int, builder: SQLBuilder, type: KClass<out T>): List<T?> {
        return readTemplate().queryListSingle(start, limit, builder, type)
    }

    final override fun <T : Any> queryList(limit: Int, sql: String, m: RowMapper<out T?>, vararg params: Any?): List<T?> {
        return readTemplate().queryList(limit, sql, m, *params)
    }

    final override fun <T : Any> queryList(limit: Int, builder: SQLBuilder, m: RowMapper<out T?>): List<T?> {
        return readTemplate().queryList(limit, builder, m)
    }

    final override fun <T : Any> queryList(limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): List<T?> {
        return readTemplate().queryList(limit, sql, type, *params)
    }

    final override fun <T : Any> queryList(limit: Int, builder: SQLBuilder, type: KClass<out T>): List<T?> {
        return readTemplate().queryList(limit, builder, type)
    }

    final override fun <T : Any> queryListSingle(limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): List<T?> {
        return readTemplate().queryListSingle(limit, sql, type, *params)
    }

    final override fun <T : Any> queryListSingle(limit: Int, builder: SQLBuilder, type: KClass<out T>): List<T?> {
        return readTemplate().queryListSingle(limit, builder, type)
    }

    final override fun queryString(sql: String, vararg params: Any?): String? {
        return readTemplate().queryString(sql, *params)
    }

    final override fun queryString(builder: SQLBuilder): String? {
        return readTemplate().queryString(builder)
    }

    final override fun queryLong(sql: String, vararg params: Any?): Long? {
        return readTemplate().queryLong(sql, *params)
    }

    final override fun queryLong(builder: SQLBuilder): Long? {
        return readTemplate().queryLong(builder)
    }

    final override fun queryInt(sql: String, vararg params: Any?): Int? {
        return readTemplate().queryInt(sql, *params)
    }

    final override fun queryInt(builder: SQLBuilder): Int? {
        return readTemplate().queryInt(builder)
    }

    final override fun queryShort(sql: String, vararg params: Any?): Short? {
        return readTemplate().queryShort(sql, *params)
    }

    final override fun queryShort(builder: SQLBuilder): Short? {
        return readTemplate().queryShort(builder)
    }

    final override fun queryByte(sql: String, vararg params: Any?): Byte? {
        return readTemplate().queryByte(sql, *params)
    }

    final override fun queryByte(builder: SQLBuilder): Byte? {
        return readTemplate().queryByte(builder)
    }

    final override fun queryDouble(sql: String, vararg params: Any?): Double? {
        return readTemplate().queryDouble(sql, *params)
    }

    final override fun queryDouble(builder: SQLBuilder): Double? {
        return readTemplate().queryDouble(builder)
    }

    final override fun queryFloat(sql: String, vararg params: Any?): Float? {
        return readTemplate().queryFloat(sql, *params)
    }

    final override fun queryFloat(builder: SQLBuilder): Float? {
        return readTemplate().queryFloat(builder)
    }

    final override fun queryBoolean(sql: String, vararg params: Any?): Boolean? {
        return readTemplate().queryBoolean(sql, *params)
    }

    final override fun queryBoolean(builder: SQLBuilder): Boolean? {
        return readTemplate().queryBoolean(builder)
    }

    final override fun queryTimestamp(sql: String, vararg params: Any?): Timestamp? {
        return readTemplate().queryTimestamp(sql, *params)
    }

    final override fun queryTimestamp(builder: SQLBuilder): Timestamp? {
        return readTemplate().queryTimestamp(builder)
    }

    final override fun queryDate(sql: String, vararg params: Any?): Date? {
        return readTemplate().queryDate(sql, *params)
    }

    final override fun queryDate(builder: SQLBuilder): Date? {
        return readTemplate().queryDate(builder)
    }

    final override fun queryTime(sql: String, vararg params: Any?): Time? {
        return readTemplate().queryTime(sql, *params)
    }

    final override fun queryTime(builder: SQLBuilder): Time? {
        return readTemplate().queryTime(builder)
    }

    final override fun <T : Any> queryPaging(page: Int, limit: Int, sql: String, m: RowMapper<out T?>, vararg params: Any?): Paging<T?> {
        return readTemplate().queryPaging(page, limit, sql, m, *params)
    }

    final override fun <T : Any> queryPaging(page: Int, limit: Int, builder: SQLBuilder, m: RowMapper<out T?>): Paging<T?> {
        return readTemplate().queryPaging(page, limit, builder, m)
    }

    final override fun <T : Any> queryPaging(page: Int, limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): Paging<T?> {
        return readTemplate().queryPaging(page, limit, sql, type, *params)
    }

    final override fun <T : Any> queryPaging(page: Int, limit: Int, builder: SQLBuilder, type: KClass<out T>): Paging<T?> {
        return readTemplate().queryPaging(page, limit, builder, type)
    }

    final override fun <T : Any> queryPagingSingle(page: Int, limit: Int, sql: String, type: KClass<out T>, vararg params: Any?): Paging<T?> {
        return readTemplate().queryPagingSingle(page, limit, sql, type, *params)
    }

    final override fun <T : Any> queryPagingSingle(page: Int, limit: Int, builder: SQLBuilder, type: KClass<out T>): Paging<T?> {
        return readTemplate().queryPagingSingle(page, limit, builder, type)
    }
}