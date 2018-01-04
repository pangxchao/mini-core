/**
 * Created the com.cfinal.web.render.CFStreamRender.java
 * @created 2016年10月6日 下午9:32:50
 * @version 1.0.0
 */
package com.cfinal.web.http.render;

import org.apache.commons.lang.StringUtils;

import com.cfinal.web.http.CFHttpServletRequest;
import com.cfinal.web.http.CFHttpServletResponse;
import com.cfinal.web.http.view.CFView;
import com.cfinal.web.model.CFModel;

/**
 * 文件下载处理器
 * @author XChao
 */
public class CFStreamRender extends CFRender {

	@Override
	public void render(CFModel model, CFView view, String viewPath, CFHttpServletRequest request,
		CFHttpServletResponse response) throws Exception {
		try {
			// 设置数据返回的ContentType
			response.setContentType("application/octet-stream");
			if(StringUtils.isNotBlank(model.getContentType())) {
				response.setContentType(model.getContentType());
			}
			// 设置文件下载的头信息
			if(StringUtils.isNotBlank(model.getFileName())) {
				String fileName = new String(model.getFileName().getBytes(), "ISO8859-1");// 解决中文
				response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			}
			// 如果输入流为空，则表示文件下载时，已经在控制器处理过了，不需要在这里处理
			if(model.getInputStream() == null) {
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
					response.setStatus(CFHttpServletResponse.SC_PARTIAL_CONTENT);
					String rangeBytes = request.getHeader("Range").replaceAll("bytes=", "");
					// 从某字节开始 的下载(bytes=27000-)
					if(rangeBytes.endsWith("-")) {
						// 读取已经读取的字节数
						startLength = Long.valueOf(rangeBytes.replaceAll("-", "").trim());
						// 只有设置了文件下载大小时， 才支持断点续传。
						contentLength = model.getContentLength() - startLength;
						// Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
						StringBuilder contentRange = new StringBuilder("bytes ");
						contentRange.append(startLength).append("-");
						contentRange.append(model.getContentLength() - 1);
						contentRange.append("/").append(model.getContentLength());
						response.setHeader("Content-Range", contentRange.toString());
					} else {
						// 读取已经读取的字节和读取结束时的字节数
						String[] rangeBytesArr = rangeBytes.split("-");
						startLength = Long.valueOf(rangeBytesArr[0].trim());
						endLength = Long.valueOf(rangeBytesArr[1].trim());
						// 只有设置了文件下载大小时， 才支持断点续传。
						contentLength = endLength - startLength;
						// Content-Range: bytes [文件块的开始字节]-[文件块结束字节]/[文件的总大小]
						StringBuilder contentRange = new StringBuilder("bytes ");
						contentRange.append(startLength).append("-");
						contentRange.append(endLength).append("/");
						contentRange.append(model.getContentLength());
						response.setHeader("Content-Range", contentRange.toString());
					}
					// 读取时，跳过 startLength 字节
					model.getInputStream().skip(startLength);
				} else {
					contentLength = model.getContentLength();
				}
				// 只有设置了文件下载大小时， 才支持断点续传。
				response.setHeader("Content-Length", String.valueOf(contentLength));
			}

			if(endLength > 0) {
				long readLength = 0;
				while (readLength <= contentLength - bufferSize) {
					model.getInputStream().read(buffer, 0, bufferSize);
					response.getOutputStream().write(buffer, 0, bufferSize);
					readLength += bufferSize;
				}
				// 余下的不足 1024 个字节在这里读取
				if(readLength <= contentLength) {
					length = ((int) (contentLength - readLength));
					model.getInputStream().read(buffer, 0, length);
					response.getOutputStream().write(buffer, 0, length);
				}
			} else {
				while ((length = model.getInputStream().read(buffer)) != -1) {
					response.getOutputStream().write(buffer, 0, length);
				}
			}
			response.getOutputStream().flush();
		} finally {
			try {
				if(model.getInputStream() != null) {
					model.getInputStream().close();
				}
			} finally {
				if(response.getOutputStream() != null) {
					response.getOutputStream().close();
				}
			}
		}
	}
}
