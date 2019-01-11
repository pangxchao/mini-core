package com.mini.util.lang;

import static java.lang.Math.min;

public final class SizeF {
    public float width;
    public float height;

    public SizeF(float width, float height) {
        set(width, height);
    }

    public SizeF set(float width, float height) {
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
    public SizeF resizeByWidth(float width) {
        return set(width, width * height / this.width);
    }

    /**
     * 按原始比例放大或者缩小
     *
     * @param height
     * @return
     */
    public SizeF resizeByHeight(float height) {
        return set(height * width / this.height, height);
    }


    /**
     * 按原始比例放大或者缩小，宽度与高度不能超过最大宽度与高度
     *
     * @param max
     * @param zoom 原尺寸小于最大尺寸时，是否放大
     * @return
     */
    public SizeF resize(SizeF max, boolean zoom) {
        if (max.width < width) resizeByWidth(max.width);
        if (max.height < height) resizeByHeight(max.height);
        if (zoom && width < max.width && height < max.width) {
            float calc = min(max.width / width, max.height / height);
            set((int) (width * calc), (int) (height * calc));
        }
        return this;
    }

    public String toString() {
        return "SizeF(" + width + ", " + height + ")";
    }

    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof SizeF) {
            SizeF other = (SizeF) obj;
            return width == other.width && height == other.height;
        }
        return false;
    }
}
