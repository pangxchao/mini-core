@file:Suppress("unused")

package com.mini.core.jdbc.builder

import java.io.Serializable
import java.util.*
import kotlin.reflect.KClass

interface SQLInterface : Serializable, EventListener {
    /**
     * 根据实例创建“REPLACE INTO”语句
     * @param builder  [SQLBuilder]
     * @param instance 实例
     */
    fun <T : Any> createReplace(builder: SQLBuilder, instance: T)

    /**
     * 根据类型创建“INSERT INTO”语句
     * @param builder  [SQLBuilder]
     * @param instance 实例
     */
    fun <T : Any> createInsert(builder: SQLBuilder, instance: T)

    /**
     * 根据类型创建“DELETE”语句
     * @param builder  [SQLBuilder]
     * @param instance 实例
     */
    fun <T : Any> createDelete(builder: SQLBuilder, instance: T)

    /**
     * 根据类型创建“UPDATE”语句
     * @param builder  [SQLBuilder]
     * @param instance 实例
     */
    fun <T : Any> createUpdate(builder: SQLBuilder, instance: T)

    /**
     * 根据类型创建“INSERT ON DUPLICATE KEY UPDATE”语句
     * @param builder  [SQLBuilder]
     * @param instance 实例
     */
    fun <T : Any> createInsertOnUpdate(builder: SQLBuilder, instance: T)

    /**
     * 根据类型创建“SELECT”语句-不带ID条件
     * @param builder [SQLBuilder]
     * @param type    实例类型
     */
    fun <T : Any> createSelect(builder: SQLBuilder?, type: KClass<T>)
}