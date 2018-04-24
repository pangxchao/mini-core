/**
 * Created the sn.mini.java.web.http.interceptor.Interceptor.java
 * @created 2017年10月9日 下午5:53:37
 * @version 1.0.0
 */
package sn.mini.java.web.http.interceptor;

import sn.mini.java.web.http.ActionInvoke;

/**
 * sn.mini.java.web.http.interceptor.Interceptor.java
 * @author XChao
 */
public interface Interceptor {
	public String interceptor(ActionInvoke invoke) throws Exception;
}
