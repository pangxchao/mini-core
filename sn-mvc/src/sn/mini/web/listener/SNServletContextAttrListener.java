/**
 * Created the sn.mini.web.listener.SNServletContextAttrListener.java
 * @created 2017年11月6日 上午11:52:50
 * @version 1.0.0
 */
package sn.mini.web.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

/**
 * sn.mini.web.listener.SNServletContextAttrListener.java
 * @author XChao
 */
public interface SNServletContextAttrListener extends ServletContextAttributeListener {

	public void attributeAdded(ServletContextAttributeEvent event);

	public void attributeRemoved(ServletContextAttributeEvent event);

	public void attributeReplaced(ServletContextAttributeEvent event);

}
