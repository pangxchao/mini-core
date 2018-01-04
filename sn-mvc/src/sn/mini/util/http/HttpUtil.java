/**
 * Created the com.xsy.utils.http.CFHttp.java
 * @created 2016年10月19日 下午4:43:12
 * @version 1.0.0
 */
package sn.mini.util.http;

/**
 * com.xsy.utils.http.CFHttp.java
 * @author XChao
 */
public abstract class HttpUtil {

	public static final HttpUtil http = new HttpUtil() {
		protected HttpRequest getHttpRequest(String url) {
			return new HttpRequest(url);
		}
	};

	public final static HttpUtil https = new HttpUtil() {
		protected HttpRequest getHttpRequest(String url) {
			return new HttpsRequest(url);
		}
	};

	protected abstract HttpRequest getHttpRequest(String url);

	public String getForUrlEncoded(String url, FormData formData) {
		try {
			HttpRequest connection = this.getHttpRequest(url);
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

	public String postForUrlEncoded(String url, FormData formData) {
		try {
			HttpRequest connection = this.getHttpRequest(url);
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

	public String postForMultipart(String url, FormData formData) {
		try {
			HttpRequest connection = this.getHttpRequest(url);
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
			HttpRequest connection = this.getHttpRequest(url);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Charset", "UTF-8");
			connection.setRequestProperty("Accept-Charset", "UTF-8");
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			return connection.requestForContentBodyToString(//
				new FormData().setContentBody(body));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getForUrlEncoded(String url, FormData formData, String charset) {
		try {
			HttpRequest connection = this.getHttpRequest(url);
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

	public String postForUrlEncoded(String url, FormData formData, String charset) {
		try {
			HttpRequest connection = this.getHttpRequest(url);
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

	public String postForMultipart(String url, FormData formData, String charset) {
		try {
			HttpRequest connection = this.getHttpRequest(url);
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
			HttpRequest connection = this.getHttpRequest(url);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Charset", charset);
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			return connection.requestForContentBodyToString(//
				new FormData().setContentBody(body));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
