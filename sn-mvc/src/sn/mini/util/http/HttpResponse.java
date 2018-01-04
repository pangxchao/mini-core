/**
 * Created the com.cfinal.util.http.CFHttpResponse.java
 * @created 2017年6月21日 上午10:59:00
 * @version 1.0.0
 */
package sn.mini.util.http;

/**
 * com.cfinal.util.http.CFHttpResponse.java
 * @author XChao
 */
@FunctionalInterface
public interface HttpResponse {

	public void handler(HttpRequest request);
}
