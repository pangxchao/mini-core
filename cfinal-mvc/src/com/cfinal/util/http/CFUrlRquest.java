/**
 * Created the com.cfinal.util.http.CFURLRquest.java
 * @created 2016年10月19日 下午3:04:07
 * @version 1.0.0
 */
package com.cfinal.util.http;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.security.Permission;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cfinal.util.logger.CFLogger;

/**
 * com.cfinal.util.http.CFURLRquest.java
 * @author XChao
 */
public class CFUrlRquest {
	public static final String END = "\r\n";
	public static final String HYPHENS = "--";
	public static final String BOUNDARY = "*****";

	private URLConnection connection;

	public CFUrlRquest(String url) {
		try {
			if(StringUtils.isBlank(url)) {
				throw new RuntimeException("URL is connot be blank.");
			}
			if(url.indexOf("?") < 0) {
				url = url + "?";
			}
			connection = new URL(url).openConnection();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public CFUrlRquest(String url, Proxy proxy) {
		try {
			if(StringUtils.isBlank(url)) {
				throw new RuntimeException("URL is connot be blank.");
			}
			if(url.indexOf("?") < 0) {
				url = url + "?";
			}
			connection = new URL(url).openConnection(proxy);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public CFUrlRquest(URLConnection connection) {
		this.connection = connection;
	}

	protected URLConnection getConnection() {
		return this.connection;
	}

	public void connect() throws IOException {
		connection.connect();
	}

	public String getHeaderFieldKey(int n) {
		return connection.getHeaderFieldKey(n);
	}

	public String getHeaderField(int n) {
		return connection.getHeaderField(n);
	}

	public long getHeaderFieldDate(String name, long Default) {
		return connection.getHeaderFieldDate(name, Default);
	}

	public Permission getPermission() throws IOException {
		return connection.getPermission();
	}

	public URL getURL() {
		return connection.getURL();
	}

	public int getContentLength() {
		return connection.getContentLength();
	}

	public long getContentLengthLong() {
		return connection.getContentLengthLong();
	}

	public String getContentType() {
		return connection.getContentType();
	}

	public String getContentEncoding() {
		return connection.getContentEncoding();
	}

	public long getExpiration() {
		return connection.getExpiration();
	}

	public long getDate() {
		return connection.getDate();
	}

	public long getLastModified() {
		return connection.getLastModified();
	}

	public String getHeaderField(String name) {
		return connection.getHeaderField(name);
	}

	public Map<String, List<String>> getHeaderFields() {
		return connection.getHeaderFields();
	}

	public int getHeaderFieldInt(String name, int Default) {
		return connection.getHeaderFieldInt(name, Default);
	}

	public long getHeaderFieldLong(String name, long Default) {
		return connection.getHeaderFieldLong(name, Default);
	}

	public Object getContent() throws IOException {
		return connection.getContent();
	}

	public Object getContent(Class<?>[] classes) throws IOException {
		return connection.getContent(classes);
	}

	public InputStream getInputStream() throws IOException {
		return connection.getInputStream();
	}

	public OutputStream getOutputStream() throws IOException {
		return connection.getOutputStream();
	}

	public String toString() {
		return connection.toString();
	}

	public void setDoInput(boolean doinput) {
		connection.setDoInput(doinput);
	}

	public boolean getDoInput() {
		return connection.getDoInput();
	}

	public void setDoOutput(boolean dooutput) {
		connection.setDoOutput(dooutput);
	}

	public boolean getDoOutput() {
		return connection.getDoOutput();
	}

	public void setAllowUserInteraction(boolean allowuserinteraction) {
		connection.setAllowUserInteraction(allowuserinteraction);
	}

	public boolean getAllowUserInteraction() {
		return connection.getAllowUserInteraction();
	}

	public void setUseCaches(boolean usecaches) {
		connection.setUseCaches(usecaches);
	}

	public boolean getUseCaches() {
		return connection.getUseCaches();
	}

	public void setIfModifiedSince(long ifmodifiedsince) {
		connection.setIfModifiedSince(ifmodifiedsince);
	}

	public long getIfModifiedSince() {
		return connection.getIfModifiedSince();
	}

	public boolean getDefaultUseCaches() {
		return connection.getDefaultUseCaches();
	}

	public void setDefaultUseCaches(boolean defaultusecaches) {
		connection.setDefaultUseCaches(defaultusecaches);
	}

	public void setRequestProperty(String key, String value) {
		connection.setRequestProperty(key, value);
	}

	public void addRequestProperty(String key, String value) {
		connection.addRequestProperty(key, value);
	}

	public String getRequestProperty(String key) {
		return connection.getRequestProperty(key);
	}

	public Map<String, List<String>> getRequestProperties() {
		return connection.getRequestProperties();
	}

	/**
	 * 将InputStream 转换成String对象
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	protected String inputStreamToString(InputStream inputStream) {
		InputStreamReader streamReader = null;
		BufferedReader bufferedReader = null;
		try {
			streamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(streamReader);
			StringBuilder builder = new StringBuilder();
			String tempLine = null;
			while ((tempLine = bufferedReader.readLine()) != null) {
				builder.append(tempLine);
				builder.append(END);
			}
			return builder.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(bufferedReader).close(streamReader);
		}
	}

	/**
	 * 流关闭方法
	 * @param closeable
	 * @return
	 */
	protected CFUrlRquest close(Closeable closeable) {
		if(closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				CFLogger.warning(e.getMessage(), e);
			}
		}
		return this;
	}
}
