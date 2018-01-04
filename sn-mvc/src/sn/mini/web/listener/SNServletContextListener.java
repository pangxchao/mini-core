/**
 * Created the sn.mini.web.listener.SNInitListener.java
 * @created 2017年11月6日 上午11:43:28
 * @version 1.0.0
 */
package sn.mini.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * sn.mini.web.listener.SNInitListener.java
 * @author XChao
 */
public interface SNServletContextListener extends ServletContextListener {

	public void contextInitialized(ServletContextEvent event);

	public void contextDestroyed(ServletContextEvent event);
}
