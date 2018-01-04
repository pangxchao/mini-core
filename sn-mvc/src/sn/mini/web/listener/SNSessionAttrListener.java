/**   
 * Created the sn.mini.web.listener.SessionAttrListener.java
 * @created 2017年11月6日 上午11:47:59 
 * @version 1.0.0 
 */
package sn.mini.web.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**   
 * sn.mini.web.listener.SessionAttrListener.java 
 * @author XChao  
 */
public interface SNSessionAttrListener extends HttpSessionAttributeListener {

	public void attributeAdded(HttpSessionBindingEvent event);

	public void attributeRemoved(HttpSessionBindingEvent event);

	public void attributeReplaced(HttpSessionBindingEvent event);
}
