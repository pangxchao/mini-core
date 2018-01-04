/**
 * Created the sn.mini.web.listener.SNServletRequestAttrListener.java
 * @created 2017年11月6日 上午11:54:49
 * @version 1.0.0
 */
package sn.mini.web.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;

/**
 * sn.mini.web.listener.SNServletRequestAttrListener.java
 * @author XChao
 */
public interface SNServletRequestAttrListener extends ServletRequestAttributeListener {

	public void attributeAdded(ServletRequestAttributeEvent event);

	public void attributeRemoved(ServletRequestAttributeEvent event);

	public void attributeReplaced(ServletRequestAttributeEvent event);

}
