/**
 * Created the com.cfinal.util.http.CFHttpsRequest.java
 * @created 2016年11月1日 下午5:59:40
 * @version 1.0.0
 */
package sn.mini.util.http;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.security.Principal;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * com.cfinal.util.http.CFHttpsRequest.java
 * @author XChao
 */
public class HttpsRequest extends HttpRequest {
	public HttpsRequest(String url) {
		super(url);
		setSSLSocketFactory(getInitSSLSocketFactory());
	}

	public HttpsRequest(String url, Proxy proxy) {
		super(url, proxy);
		setSSLSocketFactory(getInitSSLSocketFactory());
	}

	public HttpsRequest(HttpURLConnection connection) {
		super(connection);
		setSSLSocketFactory(getInitSSLSocketFactory());
	}

	protected HttpsURLConnection getConnection() {
		return (HttpsURLConnection) super.getConnection();
	}

	public String getCipherSuite() {
		return this.getConnection().getCipherSuite();
	}

	public HostnameVerifier getHostnameVerifier() {
		return this.getConnection().getHostnameVerifier();
	}

	public Certificate[] getLocalCertificates() {
		return this.getConnection().getLocalCertificates();
	}

	public Principal getLocalPrincipal() {
		return this.getConnection().getLocalPrincipal();
	}

	public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
		return this.getConnection().getPeerPrincipal();
	}

	public SSLSocketFactory getSSLSocketFactory() {
		return this.getConnection().getSSLSocketFactory();
	}

	public Certificate[] getServerCertificates() throws SSLPeerUnverifiedException {
		return this.getConnection().getServerCertificates();
	}

	public void setHostnameVerifier(HostnameVerifier v) {
		this.getConnection().setHostnameVerifier(v);
	}

	public void setSSLSocketFactory(SSLSocketFactory sf) {
		this.getConnection().setSSLSocketFactory(sf);
	}

	/**
	 * 获取 SSLSocketFactory
	 * @return
	 * @throws Exception
	 */
	private static SSLSocketFactory getInitSSLSocketFactory() {
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext sslContext = SSLContext.getInstance("TLS");
			// 使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
			sslContext.init(null, new TrustManager[] { x509TrustManager }, new SecureRandom());
			// 创建SSLSocketFactory
			return sslContext.getSocketFactory();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * TrustManager
	 * @author XChao
	 */
	private static X509TrustManager x509TrustManager = new X509TrustManager() {
		public void checkClientTrusted(X509Certificate[] chain, String authType)//
			throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) //
			throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

}
