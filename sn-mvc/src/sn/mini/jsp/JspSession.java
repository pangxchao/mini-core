/**
 * Created the sn.mini.jsp.JspSession.java
 * @created 2017年11月28日 下午12:00:18
 * @version 1.0.0
 */
package sn.mini.jsp;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sn.mini.jsp.JspSession.java
 * @author XChao
 */
public final class JspSession extends ConcurrentHashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public void setAttribute(String key, Object value) {
		this.put(key, value);
	}

	public Object getAttribute(String key) {
		return this.get(key);
	}

	public void removeAttribute(String key) {
		this.remove(key);
	}

	public Enumeration<String> getAttributeNames() {
		return this.keys();
	}
}
