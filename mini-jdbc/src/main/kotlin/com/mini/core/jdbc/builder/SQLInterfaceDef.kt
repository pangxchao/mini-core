@file:Suppress("ObjectPropertyName", "unused")

package com.mini.core.jdbc.builder

import java.io.Serializable
import java.lang.RuntimeException
import java.util.*
import kotlin.reflect.KClass

class SQLInterfaceDef : SQLInterface, EventListener, Serializable {

    override fun <T : Any> createReplace(builder: SQLBuilder, instance: T) {
        TODO("Not yet implemented")
    }

    override fun <T : Any> createInsert(builder: SQLBuilder, instance: T) {
        TODO("Not yet implemented")
    }

    override fun <T : Any> createDelete(builder: SQLBuilder, instance: T) {
        TODO("Not yet implemented")
    }

    override fun <T : Any> createUpdate(builder: SQLBuilder, instance: T) {
        TODO("Not yet implemented")
    }

    override fun <T : Any> createInsertOnUpdate(builder: SQLBuilder, instance: T) {
        TODO("Not yet implemented")
    }

    override fun <T : Any> createSelect(builder: SQLBuilder?, type: KClass<T>) {
        TODO("Not yet implemented")
    }

    companion object {
        private val INTER_MAP: MutableMap<KClass<*>, SQLInterface> = mutableMapOf()

        // 获取SQL创建的实现类
        fun <T : Any> getSQLInterface(type: KClass<in T>): SQLInterface {
           throw RuntimeException()
//            return INTER_MAP.computeIfAbsent(type, Function<Class<*>, SQLInterface?> { key: Class<*>? ->
//                var mType: Class<*>
//                try {
//                    mType = Class.forName(type.getCanonicalName().toString() + `$SQL$`)
//                    Optional.of(mType).filter(Predicate<Class<Any?>> { cls: Class<Any?>? -> SQLInterface::class.java.isAssignableFrom(cls) })
//                            .orElseThrow { NoClassDefFoundError() }
//                } catch (e: ReflectiveOperationException) {
//                    mType = SQLInterfaceDef::class.java
//                } catch (e: NoClassDefFoundError) {
//                    mType = SQLInterfaceDef::class.java
//                }
//                try {
//                    val mClass = mType.asSubclass(SQLInterface::class.java)
//                    return@computeIfAbsent mClass.getDeclaredConstructor().newInstance()
//                } catch (e: ReflectiveOperationException) {
////                throw ThrowsUtil.hidden(e);
//                } catch (e: NoClassDefFoundError) {
//                }
//                null
//            })
        }
    }
}