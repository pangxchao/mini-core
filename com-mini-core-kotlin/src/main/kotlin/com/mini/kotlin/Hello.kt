package com.mini.kotlin

import kotlin.reflect.full.*
import kotlin.reflect.jvm.jvmName

interface Hello {
    fun print()
}

abstract class AbstractHello {
    fun print(text: String) {
        println(text);
    }
}

class HelloImpl(var str: String = "123") : AbstractHello(), Hello {

    override fun print() {
        println("HelloImpl")
    }
}

val HelloImpl.text: String
    get() = "123"

fun HelloImpl.size() = 123

@Suppress("UnusedMainParameter")
fun main(args: Array<String>) {
    println(HelloImpl().size())
    println(HelloImpl().text)

    val hello = HelloImpl::class

    println("functions:")
    println(hello.functions)

    println("constructors:")
    println(hello.constructors)

    println("nestedClasses:")
    println(hello.nestedClasses)

    println("objectInstance:")
    println(hello.objectInstance)

    println("sealedSubclasses:")
    println(hello.sealedSubclasses)

    println("simpleName:")
    println(hello.simpleName)

    println("qualifiedName:")
    println(hello.qualifiedName)

    println("jvmName:")
    println(hello.jvmName)

    println("visibility:")
    println(hello.visibility)

    println("annotations:")
    println(hello.annotations)

    println("java:")
    println(hello.java)

    println("javaObjectType:")
    println(hello.javaObjectType)

    println("javaPrimitiveType:")
    println(hello.javaPrimitiveType)

    println("declaredMemberExtensionProperties:")
    println(hello.declaredMemberExtensionProperties)

    println("memberExtensionProperties:")
    println(hello.memberExtensionProperties)

    println("declaredMemberProperties:")
    println(hello.declaredMemberProperties)

    println("memberProperties:")
    println(hello.memberProperties)

    println("superclasses:")
    println(hello.superclasses)

    println("memberFunctions:")
    println(hello.memberFunctions)

    println("memberExtensionFunctions:")
    println(hello.memberExtensionFunctions)

    println("starProjectedType:")
    println(hello.starProjectedType)
}

