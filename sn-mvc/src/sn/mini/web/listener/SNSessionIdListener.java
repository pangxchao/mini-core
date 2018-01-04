/**
 * Created the sn.mini.web.listener.SNSessionIdListener.java
 * @created 2017年11月6日 上午11:51:17
 * @version 1.0.0
 */
package sn.mini.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;

/**
 * sn.mini.web.listener.SNSessionIdListener.java
 * @author XChao
 */
public interface SNSessionIdListener extends HttpSessionIdListener {

	public void sessionIdChanged(HttpSessionEvent event, String sessionId);

}
