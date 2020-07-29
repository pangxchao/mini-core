package com.mini.core.jdbc.model

import com.mini.core.holder.ClassHolder
import com.mini.core.holder.FieldHolder
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.JdbcUtils.lookupColumnName
import java.io.Serializable
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*
import kotlin.reflect.KClass


/**
 * BeanMapper.java
 *
 * @author xchao
 */
class BeanMapper<T : Any> private constructor(type: KClass<out T>) : RowMapper<T?>, EventListener, Serializable {
    private val columns: MutableMap<String, FieldHolder<out T>> = mutableMapOf()
    private val holder: ClassHolder<out T> = ClassHolder.create(type)

    init {
        holder.fields.filter {
            true
        }.forEach {
            val name = "" ?: "0"
            columns[name] = it.value
        }
    }

    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, number: Int): T? {
        val result = holder.instance
        val metaData = rs.metaData
        for (i in 1..metaData.columnCount) {
            val name = lookupColumnName(metaData, i)
            columns[name]?.let {
//                val o =getResultSetValue(rs, number, it.type)
//                it
            }
        }
        return result
    }


    companion object {
        private val MAP: MutableMap<KClass<*>, RowMapper<*>> = mutableMapOf()

        @Suppress("UNCHECKED_CAST")
        fun <T : Any> create(type: KClass<out T>): RowMapper<T?> {
            return MAP.computeIfAbsent(type) {
                type.constructors.firstOrNull { i ->
                    i.parameters.size == 1 && i.parameters[0].type.let {
                        KClass::class.isInstance(it)
                    }
                }?.call(type) as RowMapper<T?>
            } as RowMapper<T?>
        }
    }
}