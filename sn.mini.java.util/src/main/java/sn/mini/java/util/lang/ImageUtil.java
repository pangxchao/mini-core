package sn.mini.java.util.lang;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

public class ImageUtil {

	public static boolean isImage(BufferedImage image) {
		return image != null && image.getWidth() >= 0 && image.getHeight() >= 0;
	}

	public static boolean isImage(InputStream input) throws IOException {
		return input != null && isImage(ImageIO.read(input));
	}

	public static boolean isImage(File file) throws IOException {
		return file != null && isImage(ImageIO.read(file));
	}

	public static boolean isImage(String path) throws IOException {
		return StringUtil.isNotBlank(path) && isImage(new File(path));
	}

	/**
	 * 缩放图片
	 * @param buffer 读取图片的对象
	 * @param width 想要生成图片的宽度
	 * @param height 想要生成图片的高度
	 * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
	 * @return 新图片对像
	 */
	public static BufferedImage resize(BufferedImage buffer, int width, int height, boolean isBlank) {
		BufferedImage target = null;
		double sx = (double) width / buffer.getWidth();
		double sy = (double) height / buffer.getHeight();

		int w = 0, h = 0; // 定义实际图像的宽 高
		double s = (sx >= sy ? sy : sx); // 获得图片最终缩放的比例

		// 当目标宽度 或者 高度 小于或者等于 图片本身宽度 宽度的高度的时候 图像宽 度的 高度 等于 图片本身的宽高 乘以 图片最终缩放比例
		if(width <= buffer.getWidth() || height <= buffer.getHeight()) {
			w = (int) (buffer.getWidth() * s);
			h = (int) (buffer.getHeight() * s);
		} else { // 当目标宽度 和 高度都 大于 图片本身宽度 宽度的高度的时候 图像宽 度的 高度 图片本身的宽度的高度
			w = buffer.getWidth();
			h = buffer.getHeight();
		}

		if(!isBlank) {
			width = w;
			height = h;
		}

		int x = (width - w) / 2, y = (height - h) / 2, type = buffer.getType();
		if(type == BufferedImage.TYPE_CUSTOM) {
			ColorModel cm = buffer.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(width, height, type);
		}

		Graphics2D g = target.createGraphics(); // 获取画笔
		g.setColor(Color.WHITE); // 设置画笔颜色， 画背景明用白色
		g.fillRect(0, 0, width, height); // 画背景
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.drawImage(buffer.getScaledInstance(width, height, Image.SCALE_DEFAULT), x, y, w, h, null);
		g.dispose(); // 销毁画笔

		return target;
	}

	/**
	 * 缩放图片
	 * @param path
	 * @param width
	 * @param height
	 * @param isBlank 压缩后图片较窄方向是否保留空白使图片为固定传入宽高，参数为false时， 图片较窄方向为图片实际等比压缩后的宽高
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage resize(String path, int width, int height, boolean isBlank) throws IOException {
		return resize(ImageIO.read(new File(path)), width, height, isBlank);
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
	public static BufferedImage resize(File file, int width, int height, boolean isBlank) throws IOException {
		return resize(ImageIO.read(file), width, height, isBlank);
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
	public static BufferedImage resize(InputStream stream, int width, int height, boolean isBlank) throws IOException {
		return resize(ImageIO.read(stream), width, height, isBlank);
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
	public static BufferedImage resize(ImageInputStream stream, int width, int height, boolean isBlank)
		throws IOException {
		return resize(ImageIO.read(stream), width, height, isBlank);
	}

	/**
	 * 根据图片名称，获取图片 formatName
	 * @param name
	 * @return
	 */
	public static String getFormatName(String name) {
		return name.substring(Math.max(name.lastIndexOf(".") + 1, 0));
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
	public static void resize(BufferedImage buffer, int width, int height, File target, boolean isBlank)
		throws IOException {
		ImageIO.write(resize(buffer, width, height, isBlank), getFormatName(target.getAbsolutePath()), target);
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
	public static void resize(BufferedImage buffer, int width, int height, String target, boolean isBlank)
		throws IOException {
		ImageIO.write(resize(buffer, width, height, isBlank), getFormatName(target), new File(target));
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
	public static void resize(BufferedImage buffer, int width, int height, OutputStream target, String format,
		boolean isBlank) throws IOException {
		ImageIO.write(resize(buffer, width, height, isBlank), format, target);
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
	public static void resize(BufferedImage buffer, int width, int height, ImageOutputStream target, String format,
		boolean isBlank) throws IOException {
		ImageIO.write(resize(buffer, width, height, isBlank), format, target);
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
	public static void resize(String path, int width, int height, File target, boolean isBlank) throws IOException {
		ImageIO.write(resize(path, width, height, isBlank), getFormatName(target.getAbsolutePath()), target);
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
	public static void resize(String path, int width, int height, String target, boolean isBlank) throws IOException {
		ImageIO.write(resize(path, width, height, isBlank), getFormatName(target), new File(target));
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
	public static void resize(String path, int width, int height, OutputStream target, String format, boolean isBlank)
		throws IOException {
		ImageIO.write(resize(path, width, height, isBlank), format, target);
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
	public static void resize(String path, int width, int height, ImageOutputStream target, String format,
		boolean isBlank) throws IOException {
		ImageIO.write(resize(path, width, height, isBlank), format, target);
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
	public static void resize(File file, int width, int height, File target, boolean isBlank) throws IOException {
		ImageIO.write(resize(file, width, height, isBlank), getFormatName(target.getAbsolutePath()), target);
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
	public static void resize(File file, int width, int height, String target, boolean isBlank) throws IOException {
		ImageIO.write(resize(file, width, height, isBlank), getFormatName(target), new File(target));
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
	public static void resize(File file, int width, int height, OutputStream target, String format, boolean isBlank)
		throws IOException {
		ImageIO.write(resize(file, width, height, isBlank), format, target);
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
	public static void resize(File file, int width, int height, ImageOutputStream target, String format,
		boolean isBlank) throws IOException {
		ImageIO.write(resize(file, width, height, isBlank), format, target);
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
	public static void resize(InputStream stream, int width, int height, File target, boolean isBlank)
		throws IOException {
		ImageIO.write(resize(stream, width, height, isBlank), getFormatName(target.getAbsolutePath()), target);
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
	public static void resize(InputStream stream, int width, int height, String target, boolean isBlank)
		throws IOException {
		ImageIO.write(resize(stream, width, height, isBlank), getFormatName(target), new File(target));
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
	public static void resize(InputStream stream, int width, int height, OutputStream target, String format,
		boolean isBlank) throws IOException {
		ImageIO.write(resize(stream, width, height, isBlank), format, target);
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
	public static void resize(InputStream stream, int width, int height, ImageOutputStream target, String format,
		boolean isBlank) throws IOException {
		ImageIO.write(resize(stream, width, height, isBlank), format, target);
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
	public static void resize(ImageInputStream stream, int width, int height, File target, boolean isBlank)
		throws IOException {
		ImageIO.write(resize(stream, width, height, isBlank), getFormatName(target.getAbsolutePath()), target);
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
	public static void resize(ImageInputStream stream, int width, int height, String target, boolean isBlank)
		throws IOException {
		ImageIO.write(resize(stream, width, height, isBlank), getFormatName(target), new File(target));
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
	public static void resize(ImageInputStream stream, int width, int height, OutputStream target, String format,
		boolean isBlank) throws IOException {
		ImageIO.write(resize(stream, width, height, isBlank), format, target);
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
	public static void resize(ImageInputStream stream, int width, int height, ImageOutputStream target, String format,
		boolean isBlank) throws IOException {
		ImageIO.write(resize(stream, width, height, isBlank), format, target);
	}
}
