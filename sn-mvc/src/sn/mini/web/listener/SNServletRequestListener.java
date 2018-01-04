/**
 * Created the sn.mini.web.listener.SNServletRequestListener.java
 * @created 2017年11月6日 上午11:56:20
 * @version 1.0.0
 */
package sn.mini.web.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * sn.mini.web.listener.SNServletRequestListener.java
 * @author XChao
 */
public interface SNServletRequestListener extends ServletRequestListener {

	public void requestInitialized(ServletRequestEvent event);

	public void requestDestroyed(ServletRequestEvent event);
}
