@file:JvmName("ArrayKt")
@file:Suppress("unused")

package com.mini.core.util

fun IntArray.toObject(): Array<Int> {
    this.joinToString()
    return asList().toTypedArray()
}

fun LongArray.toObject(): Array<Long> {
    return asList().toTypedArray()
}

fun ByteArray.toObject(): Array<Byte> {
    return asList().toTypedArray()
}

fun ShortArray.toObject(): Array<Short> {
    return asList().toTypedArray()
}

fun FloatArray.toObject(): Array<Float> {
    return asList().toTypedArray()
}

fun DoubleArray.toObject(): Array<Double> {
    return asList().toTypedArray()
}

fun BooleanArray.toObject(): Array<Boolean> {
    return asList().toTypedArray()
}

fun CharArray.toObject(): Array<Char> {
    return asList().toTypedArray()
}

fun Array<Int>.toPrimitive(): IntArray {
    return toIntArray()
}

fun Array<Long>.toPrimitive(): LongArray {
    return toLongArray()
}

fun Array<Byte>.toPrimitive(): ByteArray {
    return toByteArray()
}

fun Array<Short>.toPrimitive(): ShortArray {
    return toShortArray()
}

fun Array<Float>.toPrimitive(): FloatArray {
    return toFloatArray()
}

fun Array<Double>.toPrimitive(): DoubleArray {
    return toDoubleArray()
}

fun Array<Boolean>.toPrimitive(): BooleanArray {
    return toBooleanArray()
}

fun Array<Char>.toPrimitive(): CharArray {
    return toCharArray()
}
