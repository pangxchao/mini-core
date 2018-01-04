/**
 * Created the sn.mini.web.http.rander.Stream.java
 * @created 2017年10月25日 下午4:54:19
 * @version 1.0.0
 */
package sn.mini.web.http.rander;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sn.mini.util.lang.StringUtil;
import sn.mini.util.lang.TypeUtil;
import sn.mini.web.http.view.IView;
import sn.mini.web.model.IModel;

/**
 * sn.mini.web.http.rander.Stream.java
 * @author XChao
 */
public final class Stream implements IRender {

	@Override
	public void render(IModel model, IView view, String viewPath, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		try (OutputStream outputStream = response.getOutputStream();
			InputStream inputStream = model.getInputStream();) {
			// 设置数据返回的ContentType
			response.setContentType("application/octet-stream");
			if(StringUtil.isNotBlank(model.getContentType())) {
				response.setContentType(model.getContentType());
			}
			// 设置文件下载的头信息
			if(StringUtil.isNotBlank(model.getFileName())) {
				String fileName = new String(model.getFileName().getBytes(), "ISO8859-1");// 解决中文
				response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			}
			// 如果输入流为空，则表示文件下载时，已经在控制器处理过了，不需要在这里处理
			if(inputStream == null) {
				return;
			}

			int length = 0; // length: 读取文件缓冲区实际长度，
			int bufferSize = 1024; // bufferSize：读取文件缓冲区最大长度
			byte[] buffer = new byte[bufferSize]; // buffer：缓冲区数据池
			long startLength = 0; // startLength:记录已下载文件大小
			long contentLength = 0; // contentLength: 客户端请求的字节总量
			long endLength = 0; // endLength: 记录客户端需要下载的字节段的最后一个字节偏移量（比如bytes=27000-39000，则这个值是为39000）
			// 设置文件下载大小，文件字节长度，如果设置该参数，表示可以 多线程连接下载
			if(model.getContentLength() > 0) {
				// 告诉客户端允许断点续传,响应的格式是:Accept-Ranges: bytes
				response.setHeader("Accept-Ranges", "bytes");
				// 客户端请求的下载的文件块的开始字节
				if(null != request.getHeader("Range")) {
					// 若客户端传来Range，说明之前下载了一部分，设置206状态(SC_PARTIAL_CONTENT)
					response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
					String rangeBytes = request.getHeader("Range").replaceAll("bytes=", "");
					// 从某字节开始 的下载(bytes=27000-)
					if(rangeBytes.endsWith("-")) {
						// 读取已经读取的字节数
						startLength = TypeUtil.castToLongValue(rangeBytes.replaceAll("-", "").trim());
						contentLength = model.getContentLength() - startLength;// 只有设置了文件下载大小时， 才支持断点续传
						// Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
						response.setHeader("Content-Range", StringUtil.join("bytes ", startLength, "-",
							model.getContentLength() - 1, "/", model.getContentLength()));
					} else {
						// 读取已经读取的字节和读取结束时的字节数
						String[] rangeBytesArr = rangeBytes.split("-");
						startLength = TypeUtil.castToLongValue(rangeBytesArr[0].trim());
						endLength = TypeUtil.castToLongValue(rangeBytesArr[1].trim());
						contentLength = endLength - startLength; // 只有设置了文件下载大小时， 才支持断点续传
						// Content-Range: bytes [文件块的开始字节]-[文件块结束字节]/[文件的总大小]
						response.setHeader("Content-Range",
							StringUtil.join("bytes ", startLength, "-", endLength, "/", model.getContentLength()));
					}
					inputStream.skip(startLength); // 读取时，跳过 startLength 字节
				} else {
					contentLength = model.getContentLength(); // 只有设置了文件下载大小时， 才支持断点续传
				}
				// 只有设置了文件下载大小时， 才支持断点续传。
				response.setHeader("Content-Length", String.valueOf(contentLength));
			}

			if(endLength > 0) {
				long readLength = 0;
				while (readLength <= contentLength - bufferSize) {
					inputStream.read(buffer, 0, bufferSize);
					outputStream.write(buffer, 0, bufferSize);
					readLength += bufferSize;
				}
				// 余下的不足 1024 个字节在这里读取
				if(readLength <= contentLength) {
					length = ((int) (contentLength - readLength));
					inputStream.read(buffer, 0, length);
					outputStream.write(buffer, 0, length);
				}
			} else {
				while ((length = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, length);
				}
			}
			outputStream.flush();
		}
	}
}
