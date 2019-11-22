package com.mini.core.jdbc.extension

import java.sql.ResultSet
import java.sql.SQLException

@Suppress("unused")
@Throws(SQLException::class)
fun ResultSet.getOBoolean(columnIndex: Int): Boolean? {
    val value = this.getBoolean(columnIndex)
    return if (this.wasNull()) null else value
}

@Suppress("unused")
@Throws(SQLException::class)
fun ResultSet.getOBoolean(columnLabel: String?): Boolean? {
    val value = this.getBoolean(columnLabel)
    return if (this.wasNull()) null else value
}

@Suppress("unused")
@Throws(SQLException::class)
fun ResultSet.getOByte(columnIndex: Int): Byte? {
    val value = this.getByte(columnIndex)
    return if (this.wasNull()) null else value
}

@Suppress("unused")
@Throws(SQLException::class)
fun ResultSet.getOByte(columnLabel: String?): Byte? {
    val value = this.getByte(columnLabel)
    return if (this.wasNull()) null else value
}

@Suppress("unused")
@Throws(SQLException::class)
fun ResultSet.getOShort(columnIndex: Int): Short? {
    val value = this.getShort(columnIndex)
    return if (this.wasNull()) null else value
}

@Suppress("unused")
@Throws(SQLException::class)
fun ResultSet.getOShort(columnLabel: String?): Short? {
    val value = this.getShort(columnLabel)
    return if (this.wasNull()) null else value
}

@Suppress("unused")
@Throws(SQLException::class)
fun ResultSet.getInteger(columnIndex: Int): Int? {
    val value = this.getInt(columnIndex)
    return if (this.wasNull()) null else value
}

@Suppress("unused")
@Throws(SQLException::class)
fun ResultSet.getInteger(columnLabel: String?): Int? {
    val value = this.getInt(columnLabel)
    return if (this.wasNull()) null else value
}

@Suppress("unused")
@Throws(SQLException::class)
fun ResultSet.getOLong(columnIndex: Int): Long? {
    val value = this.getLong(columnIndex)
    return if (this.wasNull()) null else value
}

@Suppress("unused")
@Throws(SQLException::class)
fun ResultSet.getOLong(columnLabel: String?): Long? {
    val value = this.getLong(columnLabel)
    return if (this.wasNull()) null else value
}

@Suppress("unused")
@Throws(SQLException::class)
fun ResultSet.getOFloat(columnIndex: Int): Float? {
    val value = this.getFloat(columnIndex)
    return if (this.wasNull()) null else value
}

@Suppress("unused")
@Throws(SQLException::class)
fun ResultSet.getOFloat(columnLabel: String?): Float? {
    val value = this.getFloat(columnLabel)
    return if (this.wasNull()) null else value
}

@Throws(SQLException::class)
fun ResultSet.getODouble(columnIndex: Int): Double? {
    val value = this.getDouble(columnIndex)
    return if (this.wasNull()) null else value
}

@Suppress("unused")
@Throws(SQLException::class)
fun ResultSet.getODouble(columnLabel: String?): Double? {
    val value = this.getDouble(columnLabel)
    return if (this.wasNull()) null else value
}