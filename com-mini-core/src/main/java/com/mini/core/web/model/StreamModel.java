package com.mini.core.web.model;

import com.mini.core.http.RangeParse;
import com.mini.core.http.RangeParse.Range;
import com.mini.core.util.Assert;
import com.mini.core.util.StringUtil;
import com.mini.core.util.ThrowsUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

import static java.lang.Math.min;
import static java.lang.String.format;

/**
 * Stream Model类实现
 * @author xchao
 */
public class StreamModel extends IModel<StreamModel> implements Serializable {
	private static final Logger log = LoggerFactory.getLogger(StreamModel.class);
	private static final String MULTIPART_BOUNDARY = "MULTIPART_BOUNDARY";
	private static final long serialVersionUID = -1731063292578685253L;
	private static final String TYPE = "application/octet-stream";
	private static final String CD = "Content-Disposition";
	private static final int BUFFER_SIZE = 2048;
	private boolean acceptRangesSupport = true;
	private WriteCallback writeCallback;
	private boolean attachment = true;
	private InputStream inputStream;
	private long contentLength;
	private String fileName;

	public StreamModel() {
		super(TYPE);
	}

	@Override
	protected StreamModel model() {
		return this;
	}

	public void setAttachment(boolean attachment) {
		this.attachment = attachment;
	}

	public final StreamModel setContentLength(long contentLength) {
		this.contentLength = contentLength;
		return model();
	}

	public final StreamModel setFileName(@Nonnull String fileName) {
		try {
			this.fileName = new String(fileName.getBytes(), //
					"ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			throw ThrowsUtil.hidden(e);
		}
		return model();
	}

	public final StreamModel setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
		return model();
	}


	public final StreamModel setWriteCallback(WriteCallback writeCallback) {
		this.writeCallback = writeCallback;
		return model();
	}


	public final StreamModel setAcceptRangesSupport(boolean acceptRangesSupport) {
		this.acceptRangesSupport = acceptRangesSupport;
		return model();
	}


	public final StreamModel setFileName(@Nonnull String fileName, String charset) {
		try {
			this.fileName = new String(fileName.getBytes(charset), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			throw ThrowsUtil.hidden(e);
		}
		return model();
	}

	public final StreamModel setFileName(@Nonnull String fileName, Charset charset) {
		try {
			this.fileName = new String(fileName.getBytes(charset), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			throw ThrowsUtil.hidden(e);
		}
		return model();
	}

	@Override
	protected void onSubmit(HttpServletRequest request, HttpServletResponse response, String viewPath) {
		try (ServletOutputStream output = response.getOutputStream()) {
			StringBuilder builder = new StringBuilder();
			// 设置文件名
			if (StringUtil.isNotBlank(this.fileName)) {
				builder.append(";filename=");
				builder.append(fileName);
			}
			// 设置文件内容不附件
			if (StreamModel.this.attachment) {
				builder.append(";attachment");
			}
			// 设置文件附加信息
			response.addHeader(CD, builder.toString());

			// 不支持断点续传
			if (!StreamModel.this.acceptRangesSupport) {
				copy(output, 0, contentLength - 1);
				return;
			}

			// 读取客户端上传的请求数据范围数据,并告诉客户端允许断点续传
			String rangeText = request.getHeader("Range");
			response.setHeader("Accept-Ranges", "bytes");

			// 如果请求数据范围不存在，则客户端不需要断点续传，直接返回
			if (rangeText == null || StringUtils.isBlank(rangeText)) {
				this.copy(output, 0, contentLength - 1);
				return;
			}

			// 解析客户端提交的请求数据范围数据
			List<Range> rangeList = RangeParse.parseRange(rangeText);
			if (rangeList == null || rangeList.size() <= 0) {
				response.addHeader("Content-Range", "bytes */" + contentLength);
				response.sendError(REQUESTED_RANGE_NOT_SATISFIABLE);
				return;
			}

			// 验证客户端提交的请求头是否合法
			for (RangeParse.Range range : rangeList) {
				if (range.validate(contentLength)) {
					continue;
				}

				// 如果Range对象不合法，返回错误信息
				response.addHeader("Content-Range", "bytes */" + contentLength);
				response.sendError(REQUESTED_RANGE_NOT_SATISFIABLE);
				return;
			}

			// 客户端只传入一个数据范围
			if (rangeList.size() == 1) {
				// 设置返回的数据范围
				RangeParse.Range range = rangeList.get(0);
				response.addHeader("Content-Range", format("bytes %d-%d/%d", range.getStart(), range.getEnd(),
						range.getLength()));

				// 设置传回内容在大小和Buffer大小
				long length = range.getEnd() - range.getStart() + 1;
				response.setContentLengthLong(length);
				response.setBufferSize(BUFFER_SIZE);

				// 写数据
				copy(output, range.getStart(), range.getEnd());
				return;
			}

			// 客户端传入了多个数据范围
			response.setContentType("multipart/byteranges; boundary=" + MULTIPART_BOUNDARY);
			for (RangeParse.Range range : rangeList) {
				// 输出每片数据的分隔符
				output.println();
				output.println("--" + MULTIPART_BOUNDARY);

				// 文件类型和设置返回的数据范围
				output.println("Content-Type: " + getContentType());
				output.println(format("Content-Range: bytes %d-%d/%d", range.getStart(), range.getEnd(),
						range.getLength()));

				// 输出一个空行,再定入数据
				output.println();
				copy(output, range.getStart(), range.getEnd());
			}

			// 输出结尾符
			output.println();
			output.print("--" + MULTIPART_BOUNDARY + "--");
		} catch (Exception | Error exception) {
			log.error(exception.getMessage());
			response.setStatus(500);
		}
	}

	// 写入数据
	private void copy(OutputStream out, long start, long end) throws Exception {
		getWriteCallback().copy(out, start, end);
	}

	@Nonnull
	private synchronized WriteCallback getWriteCallback() {
		return ObjectUtils.defaultIfNull(writeCallback, new WriteCallback() {
			public void copy(OutputStream out, long start, long end) throws IOException {
				if (StreamModel.this.inputStream == null) return;
				try (InputStream source = StreamModel.this.inputStream) {
					long sendLength = end - start + 1, skip = source.skip(start);
					Assert.isTrue(skip >= start, "Skip fail. [%d, %d]", skip, start);
					if (end >= 0) transferTo(out, source, sendLength);
					else source.transferTo(out);
				}
			}

			private void transferTo(OutputStream out, InputStream source, long sendLength) throws IOException {
				int length, size = BUFFER_SIZE;
				byte[] buffer = new byte[size];
				for (; sendLength > 0; sendLength -= length) {
					size = min(size, (int) sendLength);
					length = source.read(buffer, 0, size);

					if (length <= 0) break;
					out.write(buffer, 0, length);
				}
			}
		});
	}

	@FunctionalInterface
	public interface WriteCallback {
		void copy(OutputStream out, long start, long end) throws Exception;
	}
}
