package com.mini.kotlin.util

import java.lang.Math.min

data class Size(
    private var width: Double = 0.toDouble(),
    private var height: Double = 0.toDouble()
) {

    fun width(): Double {
        return width
    }

    fun height(): Double {
        return height
    }

    fun widthToInt(): Int {
        return width.toInt()
    }

    fun heightToInt(): Int {
        return height.toInt()
    }

    fun widthToFloat(): Float {
        return width.toFloat()
    }

    fun heightToFloat(): Float {
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
     * 重新设置宽高
     * @param width  宽度
     * @param height 高度
     * @return 返回尺寸
     */
    operator fun set(width: Float, height: Float): Size {
        return set(width.toDouble(), height.toDouble())
    }

    /**
     * 重新设置宽高
     * @param width  宽度
     * @param height 高度
     * @return 返回尺寸
     */
    operator fun set(width: Int, height: Int): Size {
        return set(width.toDouble(), height.toDouble())
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
     * @param width 最大宽度
     * @return 返回尺寸
     */
    fun resizeByWidth(width: Int): Size {
        return resizeByWidth(width.toDouble())
    }

    /**
     * 按原始比例放大或者缩小
     * @param width 最大宽度
     * @return 返回尺寸
     */
    fun resizeByWidth(width: Float): Size {
        return resizeByWidth(width.toDouble())
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
     * 按原始比例放大或者缩小
     * @param width 最大宽度
     * @return 返回尺寸
     */
    fun resizeByHeight(width: Int): Size {
        return resizeByHeight(width.toDouble())
    }

    /**
     * 按原始比例放大或者缩小
     * @param width 最大宽度
     * @return 返回尺寸
     */
    fun resizeByHeight(width: Float): Size {
        return resizeByHeight(width.toDouble())
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

    override fun toString(): String = "Size($width, $height)"
}
