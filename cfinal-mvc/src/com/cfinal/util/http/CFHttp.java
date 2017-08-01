/**
 * Created the com.xsy.utils.http.CFHttp.java
 * @created 2016年10月19日 下午4:43:12
 * @version 1.0.0
 */
package com.cfinal.util.http;

/**
 * com.xsy.utils.http.CFHttp.java
 * @author XChao
 */
public abstract class CFHttp {

	public static final CFHttp http = new CFHttp() {
		protected CFHttpRequest getHttpRequest(String url) {
			return new CFHttpRequest(url);
		}
	};

	public final static CFHttp https = new CFHttp() {
		protected CFHttpRequest getHttpRequest(String url) {
			return new CFHttpsRequest(url);
		}
	};

	protected abstract CFHttpRequest getHttpRequest(String url);

	public String getForUrlEncoded(String url, CFFormData formData) {
		try {
			CFHttpRequest connection = this.getHttpRequest(url);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			return connection.requestForUrlEncodedToString(formData);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String postForUrlEncoded(String url, CFFormData formData) {
		try {
			CFHttpRequest connection = this.getHttpRequest(url);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			return connection.requestForUrlEncodedToString(formData);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String postForMultipart(String url, CFFormData formData) {
		try {
			CFHttpRequest connection = this.getHttpRequest(url);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			return connection.requestForMultipartToString(formData);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String postForContentBody(String url, String body) {
		try {
			CFHttpRequest connection = this.getHttpRequest(url);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			return connection.requestForContentBodyToString(//
				new CFFormData().setContentBody(body));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getForUrlEncoded(String url, CFFormData formData, String charset) {
		try {
			CFHttpRequest connection = this.getHttpRequest(url);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Charset", charset);
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			return connection.requestForUrlEncodedToString(formData);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String postForUrlEncoded(String url, CFFormData formData, String charset) {
		try {
			CFHttpRequest connection = this.getHttpRequest(url);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Charset", charset);
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			return connection.requestForUrlEncodedToString(formData);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String postForMultipart(String url, CFFormData formData, String charset) {
		try {
			CFHttpRequest connection = this.getHttpRequest(url);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Charset", charset);
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			return connection.requestForMultipartToString(formData);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String postForContentBody(String url, String body, String charset) {
		try {
			CFHttpRequest connection = this.getHttpRequest(url);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Charset", charset);
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			return connection.requestForContentBodyToString(//
				new CFFormData().setContentBody(body));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
