/**
 * Created the com.cfinal.util.http.CFHttpResponse.java
 * @created 2017年6月21日 上午10:59:00
 * @version 1.0.0
 */
package com.cfinal.util.http;

/**
 * com.cfinal.util.http.CFHttpResponse.java
 * @author XChao
 */
@FunctionalInterface
public interface CFHttpResponse {

	public void handler(CFHttpRequest request);
}
