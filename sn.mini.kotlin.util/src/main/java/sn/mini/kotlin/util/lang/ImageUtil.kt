package sn.mini.kotlin.util.lang

import java.awt.Color
import java.awt.Image
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import javax.imageio.ImageIO
import javax.imageio.stream.ImageInputStream
import javax.imageio.stream.ImageOutputStream

object ImageUtil {
    fun isImage(image: BufferedImage?): Boolean = image != null && image.width >= 0 && image.height >= 0

    @Throws(IOException::class)
    fun isImage(input: InputStream?): Boolean = input != null && isImage(ImageIO.read(input))

    @Throws(IOException::class)
    fun isImage(file: File?): Boolean = file != null && isImage(ImageIO.read(file))

    @Throws(IOException::class)
    fun isImage(path: String): Boolean = path.isNotBlank() && isImage(File(path))

    /**
     * 缩放图片
     * @param path
     * @param width
     * @param height
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(path: String, width: Int, height: Int, isBlank: Boolean): BufferedImage {
        return resize(ImageIO.read(File(path)), width, height, isBlank)
    }

    /**
     * 缩放图片
     * @param file
     * @param width
     * @param height
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(file: File, width: Int, height: Int, isBlank: Boolean): BufferedImage {
        return resize(ImageIO.read(file), width, height, isBlank)
    }


    /**
     * 缩放图片
     * @param buffer 读取图片的对象
     * @param width 想要生成图片的宽度
     * @param height 想要生成图片的高度
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @return 新图片对像
     */
    @Suppress("NAME_SHADOWING")
    fun resize(buffer: BufferedImage, width: Int, height: Int, isBlank: Boolean): BufferedImage {
        var width = width
        var height = height
        val sx = width.toDouble() / buffer.width
        val sy = height.toDouble() / buffer.height

        val w: Int
        val h: Int // 定义实际图像的宽 高
        val s = if (sx >= sy) sy else sx // 获得图片最终缩放的比例

        // 当目标宽度 或者 高度 小于或者等于 图片本身宽度 宽度的高度的时候 图像宽 度的 高度 等于 图片本身的宽高 乘以 图片最终缩放比例
        if (width <= buffer.width || height <= buffer.height) {
            w = (buffer.width * s).toInt()
            h = (buffer.height * s).toInt()
        } else { // 当目标宽度 和 高度都 大于 图片本身宽度 宽度的高度的时候 图像宽 度的 高度 图片本身的宽度的高度
            w = buffer.width
            h = buffer.height
        }

        if (!isBlank) {
            width = w
            height = h
        }

        val x = (width - w) / 2
        val y = (height - h) / 2
        val type = buffer.type
        val target: BufferedImage = if (type == BufferedImage.TYPE_CUSTOM) {
            val cm = buffer.colorModel
            val raster = cm.createCompatibleWritableRaster(width, height)
            val alphaPhotomultiplier = cm.isAlphaPremultiplied
            BufferedImage(cm, raster, alphaPhotomultiplier, null)
        } else {
            BufferedImage(width, height, type)
        }

        val g = target.createGraphics() // 获取画笔
        g.color = Color.WHITE // 设置画笔颜色， 画背景明用白色
        g.fillRect(0, 0, width, height) // 画背景
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        g.drawImage(buffer.getScaledInstance(width, height, Image.SCALE_DEFAULT), x, y, w, h, null)
        g.dispose() // 销毁画笔

        return target
    }

    /**
     * 缩放图片
     * @param stream
     * @param width
     * @param height
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(stream: InputStream, width: Int, height: Int, isBlank: Boolean): BufferedImage {
        return resize(ImageIO.read(stream), width, height, isBlank)
    }

    /**
     * 缩放图片
     * @param stream
     * @param width
     * @param height
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(stream: ImageInputStream, width: Int, height: Int, isBlank: Boolean): BufferedImage {
        return resize(ImageIO.read(stream), width, height, isBlank)
    }

    /**
     * 根据图片名称，获取图片 formatName
     * @param name
     * @return
     */
    fun getFormatName(name: String): String {
        return name.substring(Math.max(name.lastIndexOf(".") + 1, 0))
    }

    /**
     * 缩放图片
     * @param buffer
     * @param width
     * @param height
     * @param target 文件目标对象
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(buffer: BufferedImage, width: Int, height: Int, target: File, isBlank: Boolean) {
        ImageIO.write(resize(buffer, width, height, isBlank), getFormatName(target.absolutePath), target)
    }

    /**
     * 缩放图片
     * @param buffer
     * @param width
     * @param height
     * @param target 文件目标地址
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(buffer: BufferedImage, width: Int, height: Int, target: String, isBlank: Boolean) {
        ImageIO.write(resize(buffer, width, height, isBlank), getFormatName(target), File(target))
    }

    /**
     * 缩放图片
     * @param buffer
     * @param width
     * @param height
     * @param target 文件目标流
     * @param format 图片格式
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(buffer: BufferedImage, width: Int, height: Int, target: OutputStream, format: String,
               isBlank: Boolean) {
        ImageIO.write(resize(buffer, width, height, isBlank), format, target)
    }

    /**
     * 缩放图片
     * @param buffer
     * @param width
     * @param height
     * @param target 文件目标图片流
     * @param format 图片格式
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(buffer: BufferedImage, width: Int, height: Int, target: ImageOutputStream, format: String,
               isBlank: Boolean) {
        ImageIO.write(resize(buffer, width, height, isBlank), format, target)
    }

    /**
     * 缩放图片
     * @param path
     * @param width
     * @param height
     * @param target 文件目标对象
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(path: String, width: Int, height: Int, target: File, isBlank: Boolean) {
        ImageIO.write(resize(path, width, height, isBlank), getFormatName(target.absolutePath), target)
    }

    /**
     * 缩放图片
     * @param path
     * @param width
     * @param height
     * @param target 文件目标地址
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(path: String, width: Int, height: Int, target: String, isBlank: Boolean) {
        ImageIO.write(resize(path, width, height, isBlank), getFormatName(target), File(target))
    }

    /**
     * 缩放图片
     * @param path
     * @param width
     * @param height
     * @param target 文件目标流
     * @param format 图片格式
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(path: String, width: Int, height: Int, target: OutputStream, format: String, isBlank: Boolean) {
        ImageIO.write(resize(path, width, height, isBlank), format, target)
    }

    /**
     * 缩放图片
     * @param path
     * @param width
     * @param height
     * @param target 文件目标图片流
     * @param format 图片格式
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(path: String, width: Int, height: Int, target: ImageOutputStream, format: String,
               isBlank: Boolean) {
        ImageIO.write(resize(path, width, height, isBlank), format, target)
    }

    /**
     * 缩放图片
     * @param file
     * @param width
     * @param height
     * @param target 文件目标对象
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(file: File, width: Int, height: Int, target: File, isBlank: Boolean) {
        ImageIO.write(resize(file, width, height, isBlank), getFormatName(target.absolutePath), target)
    }

    /**
     * 缩放图片
     * @param file
     * @param width
     * @param height
     * @param target 文件目标地址
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(file: File, width: Int, height: Int, target: String, isBlank: Boolean) {
        ImageIO.write(resize(file, width, height, isBlank), getFormatName(target), File(target))
    }

    /**
     * 缩放图片
     * @param file
     * @param width
     * @param height
     * @param target 文件目标流
     * @param format 图片格式
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(file: File, width: Int, height: Int, target: OutputStream, format: String, isBlank: Boolean) {
        ImageIO.write(resize(file, width, height, isBlank), format, target)
    }

    /**
     * 缩放图片
     * @param file
     * @param width
     * @param height
     * @param target 文件目标图片流
     * @param format 图片格式
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(file: File, width: Int, height: Int, target: ImageOutputStream, format: String,
               isBlank: Boolean) {
        ImageIO.write(resize(file, width, height, isBlank), format, target)
    }

    /**
     * 缩放图片
     * @param stream
     * @param width
     * @param height
     * @param target 文件目标对象
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(stream: InputStream, width: Int, height: Int, target: File, isBlank: Boolean) {
        ImageIO.write(resize(stream, width, height, isBlank), getFormatName(target.absolutePath), target)
    }

    /**
     * 缩放图片
     * @param stream
     * @param width
     * @param height
     * @param target 文件目标地址
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(stream: InputStream, width: Int, height: Int, target: String, isBlank: Boolean) {
        ImageIO.write(resize(stream, width, height, isBlank), getFormatName(target), File(target))
    }

    /**
     * 缩放图片
     * @param stream
     * @param width
     * @param height
     * @param target 文件目标流
     * @param format 图片格式
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(stream: InputStream, width: Int, height: Int, target: OutputStream, format: String,
               isBlank: Boolean) {
        ImageIO.write(resize(stream, width, height, isBlank), format, target)
    }

    /**
     * 缩放图片
     * @param stream
     * @param width
     * @param height
     * @param target 文件目标图片流
     * @param format 图片格式
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(stream: InputStream, width: Int, height: Int, target: ImageOutputStream, format: String,
               isBlank: Boolean) {
        ImageIO.write(resize(stream, width, height, isBlank), format, target)
    }

    /**
     * 缩放图片
     * @param stream
     * @param width
     * @param height
     * @param target 文件目标对象
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(stream: ImageInputStream, width: Int, height: Int, target: File, isBlank: Boolean) {
        ImageIO.write(resize(stream, width, height, isBlank), getFormatName(target.absolutePath), target)
    }

    /**
     * 缩放图片
     * @param stream
     * @param width
     * @param height
     * @param target 文件目标地址
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(stream: ImageInputStream, width: Int, height: Int, target: String, isBlank: Boolean) {
        ImageIO.write(resize(stream, width, height, isBlank), getFormatName(target), File(target))
    }

    /**
     * 缩放图片
     * @param stream
     * @param width
     * @param height
     * @param target 文件目标流
     * @param format 图片格式
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(stream: ImageInputStream, width: Int, height: Int, target: OutputStream, format: String,
               isBlank: Boolean) {
        ImageIO.write(resize(stream, width, height, isBlank), format, target)
    }

    /**
     * 缩放图片
     * @param stream
     * @param width
     * @param height
     * @param target 文件目标图片流
     * @param format 图片格式
     * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
     * @throws IOException
     */
    @Throws(IOException::class)
    fun resize(stream: ImageInputStream, width: Int, height: Int, target: ImageOutputStream, format: String,
               isBlank: Boolean) {
        ImageIO.write(resize(stream, width, height, isBlank), format, target)
    }
}
