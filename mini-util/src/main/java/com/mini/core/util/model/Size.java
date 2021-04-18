package com.mini.core.util.model;

import java.io.Serializable;
import java.util.Objects;

import static java.lang.Math.min;

public class Size implements Serializable {
    public double width, height;

    public Size(double width, double height) {
        set(width, height);
    }

    public int width() {
        return (int) width;
    }

    public int height() {
        return (int) height;
    }

    public float floatWidth() {
        return (float) width;
    }

    public float floatHeight() {
        return (float) height;
    }

    /**
     * 重新设置宽高
     *
     * @param width  宽度
     * @param height 高度
     * @return 返回尺寸
     */
    public Size set(double width, double height) {
        this.height = height;
        this.width = width;
        return this;
    }

    /**
     * 按原始比例放大或者缩小
     *
     * @param width 最大宽度
     * @return 返回尺寸
     */
    @SuppressWarnings("UnusedReturnValue")
    public Size resizeByWidth(double width) {
        return set(width, width * height / this.width);
    }

    /**
     * 按原始比例放大或者缩小
     *
     * @param height 最大高度
     * @return 返回尺寸
     */
    @SuppressWarnings("UnusedReturnValue")
    public Size resizeByHeight(double height) {
        return set(height * width / this.height, height);
    }

    /**
     * 按原始比例放大或者缩小，宽度与高度不能超过最大宽度与高度
     *
     * @param max  最大尺寸
     * @param zoom 原尺寸小于最大尺寸时，是否放大
     * @return 返回尺寸
     */
    public Size resize(Size max, boolean zoom) {
        if (max.width < width) resizeByWidth(max.width);
        if (max.height < height) resizeByHeight(max.height);
        if (zoom && width < max.width && height < max.width) {
            double calc = min(max.width / width, max.height / height);
            set((width * calc), height * calc);
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Size size = (Size) o;
        return Double.compare(size.width, width) == 0 &&
                Double.compare(size.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return "Size{ width=" + width + ", height=" + height + "}";
    }
}