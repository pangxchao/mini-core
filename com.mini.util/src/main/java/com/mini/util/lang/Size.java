package com.mini.util.lang;

import static java.lang.Math.min;

public final class Size {
    public int width;
    public int height;

    public Size(int width, int height) {
        set(width, height);
    }

    public Size set(int width, int height) {
        this.height = height;
        this.width = width;
        return this;
    }

    /**
     * 按原始比例放大或者缩小
     *
     * @param width
     * @return
     */
    public Size resizeByWidth(int width) {
        return set(width, width * height / this.width);
    }

    /**
     * 按原始比例放大或者缩小
     *
     * @param height
     * @return
     */
    public Size resizeByHeight(int height) {
        return set(height * width / this.height, height);
    }


    /**
     * 按原始比例放大或者缩小，宽度与高度不能超过最大宽度与高度
     *
     * @param max
     * @param zoom 原尺寸小于最大尺寸时，是否放大
     * @return
     */
    public Size resize(Size max, boolean zoom) {
        if (max.width < width) resizeByWidth(max.width);
        if (max.height < height) resizeByHeight(max.height);
        if (zoom && width < max.width && height < max.width) {
            float calc = min(max.width / width, max.height / height);
            set((int) (width * calc), (int) (height * calc));
        }
        return this;
    }


    public String toString() {
        return "Size(" + width + ", " + height + ")";
    }

    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Size) {
            Size other = (Size) obj;
            return width == other.width && height == other.height;
        }
        return false;
    }
}
