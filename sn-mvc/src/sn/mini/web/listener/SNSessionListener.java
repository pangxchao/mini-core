/**
 * Created the sn.mini.web.listener.SNSessionListener.java
 * @created 2017年11月6日 上午11:49:43
 * @version 1.0.0
 */
package sn.mini.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * sn.mini.web.listener.SNSessionListener.java
 * @author XChao
 */
public interface SNSessionListener extends HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event);

	public void sessionDestroyed(HttpSessionEvent event);

}
