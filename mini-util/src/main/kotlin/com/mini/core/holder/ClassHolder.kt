@file:Suppress("unused")

package com.mini.core.holder

import kotlin.reflect.KClass

class ClassHolder<T : Any> private constructor(val type: KClass<in T>) {
    val fields: MutableMap<String, FieldHolder<T>> = mutableMapOf()

    @Suppress("UNCHECKED_CAST")
    val instance: T? by lazy {
        type.constructors.firstOrNull { it.parameters.isEmpty() }?.call() as? T
    }

    inline fun <reified A : Annotation> getAnnotationsByType(clazz: KClass<in A>): Array<A> {
        return type.annotations.filter {
            clazz.isInstance(it)
        }.map { it as A }.toTypedArray()
    }

    inline fun <reified A : Annotation> getAnnotation(clazz: KClass<in A>): A? {
        return type.annotations.find {
            clazz.isInstance(it)
        } as? A
    }

    companion object {
        private val M: MutableMap<KClass<*>, ClassHolder<*>> = mutableMapOf()

        @Synchronized
        @Suppress("UNCHECKED_CAST")
        fun <T : Any> create(type: KClass<T>): ClassHolder<T> {
            return M.computeIfAbsent(type) {
                val holder = ClassHolder(type)
                // holder.fields.put()

                return@computeIfAbsent holder
            } as ClassHolder<T>
        }
    }
}