/**
 * Created the com.cfinal.util.http.CFHttpRequest.java
 * @created 2017年6月21日 上午11:15:18
 * @version 1.0.0
 */
package sn.mini.util.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import sn.mini.util.lang.StringUtil;

/**
 * com.cfinal.util.http.CFHttpRequest.java
 * @author XChao
 */
public class HttpRequest extends UrlRquest {

	public HttpRequest(String url) {
		super(url);
	}

	public HttpRequest(String url, Proxy proxy) {
		super(url, proxy);
	}

	public HttpRequest(HttpURLConnection connection) {
		super(connection);
	}

	protected HttpURLConnection getConnection() {
		return (HttpURLConnection) super.getConnection();
	}

	public void setFixedLengthStreamingMode(int contentLength) {
		this.getConnection().setFixedLengthStreamingMode(contentLength);
	}

	public void setFixedLengthStreamingMode(long contentLength) {
		this.getConnection().setFixedLengthStreamingMode(contentLength);
	}

	public void setChunkedStreamingMode(int chunklen) {
		this.getConnection().setChunkedStreamingMode(chunklen);
	}

	public void setInstanceFollowRedirects(boolean followRedirects) {
		this.getConnection().setInstanceFollowRedirects(followRedirects);
	}

	public boolean getInstanceFollowRedirects() {
		return this.getConnection().getInstanceFollowRedirects();
	}

	public void setRequestMethod(String method) throws ProtocolException {
		this.getConnection().setRequestMethod(method);
	}

	public String getRequestMethod() {
		return this.getConnection().getRequestMethod();
	}

	public int getResponseCode() throws IOException {
		return this.getConnection().getResponseCode();
	}

	public String getResponseMessage() throws IOException {
		return this.getConnection().getResponseMessage();
	}

	public void disconnect() {
		this.getConnection().disconnect();
	}

	public boolean usingProxy() {
		return this.getConnection().usingProxy();
	}

	public InputStream getErrorStream() {
		return this.getConnection().getErrorStream();
	}

	/**
	 * 将form数据以 application/x-www-form-urlencoded 的转码方式写入OutputStream<br/>
	 * 该种方式写数据时，不写file与contentBody数据
	 * @param formData
	 * @return
	 */
	public String requestForUrlEncodedToString(FormData formData) {
		InputStream inputStream = null;
		try {
			inputStream = this.requestForUrlEncoded(formData);
			return inputStreamToString(inputStream);
		} finally {
			close(inputStream);
		}
	}

	/**
	 * 将form数据以multipart/form-data 的转码方式写入OutputStream<br/>
	 * 该种方式写数据时，不写 contentBody 数据
	 * @return
	 * @throws IOException
	 */
	public String requestForMultipartToString(FormData formData) {
		InputStream inputStream = null;
		try {
			inputStream = this.requestForMultipart(formData);
			return inputStreamToString(inputStream);
		} finally {
			close(inputStream);
		}
	}

	/**
	 * 将form数据以 text/plain 方式写入OutputStream<br/>
	 * （multipart/form-data与application/x-www-form-urlencoded以外的方式）<br/>
	 * 该种方式写数据时，不写text与file数据
	 * @return
	 * @throws IOException
	 */
	public String requestForContentBodyToString(FormData formData) {
		InputStream inputStream = null;
		try {
			inputStream = this.requestForContentBody(formData);
			return inputStreamToString(inputStream);
		} finally {
			close(inputStream);
		}
	}

	/**
	 * 将form数据以 application/x-www-form-urlencoded 的转码方式写入OutputStream<br/>
	 * 该种方式写数据时，不写file与contentBody数据
	 * @param formData
	 * @return
	 */
	public InputStream requestForUrlEncoded(FormData formData) {
		try {
			this.requestForUrlEncoded(formData, request -> {
			});
			// 验证请求是否成功, 成功时，返回InputStream
			return this.validateResponseIsSuccess();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将form数据以multipart/form-data 的转码方式写入OutputStream<br/>
	 * 该种方式写数据时，不写 contentBody 数据
	 * @return
	 * @throws IOException
	 */
	public InputStream requestForMultipart(FormData formData) {
		try {
			this.requestForMultipart(formData, request -> {
			});
			// 验证请求是否成功, 成功时，返回InputStream
			return this.validateResponseIsSuccess();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将form数据以 text/plain 方式写入OutputStream<br/>
	 * （multipart/form-data与application/x-www-form-urlencoded以外的方式）<br/>
	 * 该种方式写数据时，不写text与file数据
	 * @return
	 * @throws IOException
	 */
	public InputStream requestForContentBody(FormData formData) {
		try {
			this.requestForContentBody(formData, request -> {
			});
			// 验证请求是否成功, 成功时，返回InputStream
			return this.validateResponseIsSuccess();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将form数据以 application/x-www-form-urlencoded 的转码方式写入OutputStream<br/>
	 * 该种方式写数据时，不写file与contentBody数据
	 * @param formData
	 * @param response
	 * @return
	 */
	public void requestForUrlEncoded(FormData formData, HttpResponse response) {
		OutputStreamWriter writer = null;
		try {
			this.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			writer = new OutputStreamWriter(this.getOutputStream());
			List<String> result = new ArrayList<>();
			for (String name : formData.textKeySet()) {
				for (String value : formData.getText(name)) {
					result.add(name + "=" + value);
				}
			}
			writer.write(StringUtil.join(result, "&"));
			// 刷新缓存
			writer.flush();
			// 结果处理
			response.handler(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(writer);
		}
	}

	/**
	 * 将form数据以multipart/form-data 的转码方式写入OutputStream<br/>
	 * 该种方式写数据时，不写 contentBody 数据
	 * @return
	 * @throws IOException
	 */
	public void requestForMultipart(FormData formData, HttpResponse response) {
		DataOutputStream steam = null;
		try {
			this.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
			steam = new DataOutputStream(this.getOutputStream());
			for (String name : formData.textKeySet()) {
				for (String value : formData.getText(name)) {
					steam.writeBytes(UrlRquest.HYPHENS);
					steam.writeBytes(UrlRquest.BOUNDARY);
					steam.writeBytes(UrlRquest.END);

					steam.writeBytes("Content-Disposition: form-data; name=\"");
					steam.writeBytes(name);
					steam.writeBytes("\"");
					steam.writeBytes(UrlRquest.END);

					steam.writeBytes(UrlRquest.END);
					steam.writeBytes(value);
					steam.writeBytes(UrlRquest.END);
				}
			}
			for (String name : formData.fileKeySet()) {
				for (int i = 0, len = formData.getFileCount(name); i < len; i++) {
					steam.writeBytes(UrlRquest.HYPHENS);
					steam.writeBytes(UrlRquest.BOUNDARY);
					steam.writeBytes(UrlRquest.END);

					steam.writeBytes("Content-Disposition: form-data; name=\"");
					steam.writeBytes(name);
					steam.writeBytes("\";filename=\"");
					steam.writeBytes(formData.getFileName(name, i));
//					steam.writeBytes("\"; filesize=\"");
//					steam.writeBytes(String.valueOf(formData.getFileLength(name, i)));
					steam.writeBytes("\"");
					steam.writeBytes(UrlRquest.END);

					steam.writeBytes("Content-Type:");
					steam.writeBytes(formData.getFileContentType(name, i));
					steam.writeBytes(UrlRquest.END);

					steam.writeBytes(UrlRquest.END);
					InputStream _inputStream = null;
					try {
						int bufferSize = 1024, length = -1;
						byte[] buffer = new byte[bufferSize];
						_inputStream = formData.getFileInputStream(name, i);
						while ((length = _inputStream.read(buffer)) != -1) {
							steam.write(buffer, 0, length);
						}
					} finally {
						if(_inputStream != null) {
							_inputStream.close();
						}
					}
					steam.writeBytes(UrlRquest.END);
				}
			}
			steam.writeBytes(UrlRquest.HYPHENS);
			steam.writeBytes(UrlRquest.BOUNDARY);
			steam.writeBytes(UrlRquest.HYPHENS);
			steam.writeBytes(UrlRquest.END);
			steam.flush();
			// 结果处理
			response.handler(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(steam);
		}
	}

	/**
	 * 将form数据以 text/plain 方式写入OutputStream<br/>
	 * （multipart/form-data与application/x-www-form-urlencoded以外的方式）<br/>
	 * 该种方式写数据时，不写text与file数据
	 * @return
	 * @throws IOException
	 */
	public void requestForContentBody(FormData formData, HttpResponse response) {
		OutputStreamWriter writer = null;
		try {
			this.setRequestProperty("Content-Type", "text/plain");
			writer = new OutputStreamWriter(this.getOutputStream());
			writer.write(formData.getContentBody());
			writer.flush();
			// 结果处理
			response.handler(this);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(writer);
		}
	}

	/**
	 * 验证请求是事成功，如果请求失败，则抛出异常， 如果成功，则返回 InputStream
	 * @throws IOException
	 */
	protected InputStream validateResponseIsSuccess() throws IOException {
		if(this.getConnection().getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new RuntimeException("HTTP Request is fail, Response code : " //
				+ this.getConnection().getResponseCode() + ", Response message : " //
				+ this.getConnection().getResponseMessage());
		}
		return this.getConnection().getInputStream();
	}
}
