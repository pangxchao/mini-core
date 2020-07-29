@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.mini.core.util

import java.io.Serializable
import kotlin.math.min

class Size(width: Double, height: Double) : Serializable {
    var width = 0.0
    var height = 0.0

    init {
        set(width, height)
    }

    fun width(): Int {
        return width.toInt()
    }

    fun height(): Int {
        return height.toInt()
    }

    fun floatWidth(): Float {
        return width.toFloat()
    }

    fun floatHeight(): Float {
        return height.toFloat()
    }

    /**
     * 重新设置宽高
     * @param width  宽度
     * @param height 高度
     * @return 返回尺寸
     */
    operator fun set(width: Double, height: Double): Size {
        this.height = height
        this.width = width
        return this
    }

    /**
     * 按原始比例放大或者缩小
     * @param width 最大宽度
     * @return 返回尺寸
     */
    fun resizeByWidth(width: Double): Size {
        return set(width, width * height / this.width)
    }

    /**
     * 按原始比例放大或者缩小
     * @param height 最大高度
     * @return 返回尺寸
     */
    fun resizeByHeight(height: Double): Size {
        return set(height * width / this.height, height)
    }

    /**
     * 按原始比例放大或者缩小，宽度与高度不能超过最大宽度与高度
     * @param max  最大尺寸
     * @param zoom 原尺寸小于最大尺寸时，是否放大
     * @return 返回尺寸
     */
    fun resize(max: Size, zoom: Boolean): Size {
        if (max.width < width) resizeByWidth(max.width)
        if (max.height < height) resizeByHeight(max.height)
        if (zoom && width < max.width && height < max.width) {
            val calc = min(max.width / width, max.height / height)
            set(width * calc, height * calc)
        }
        return this
    }

    override fun toString(): String {
        return "Size(width=$width, height=$height)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) {
            return false
        }

        other as Size

        if (width != other.width) {
            return false
        }
        if (height != other.height) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = width.hashCode()
        result = 31 * result + height.hashCode()
        return result
    }

}