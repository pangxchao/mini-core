/**
 * Created the sn.mini.jsp.compiler.tag.JspTag.java
 * @created 2017年12月4日 下午4:36:18
 * @version 1.0.0
 */
package sn.mini.jsp.compiler.tag;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import sn.mini.jsp.JspEngine;
import sn.mini.jsp.JspSession;
import sn.mini.jsp.compiler.JspWriter;
import sn.mini.util.lang.TypeUtil;
import sn.mini.util.logger.Log;

/**
 * sn.mini.jsp.compiler.tag.JspTag.java
 * @author XChao
 */
public abstract class JspTag {
	private Object text;
	private JspEngine engine;
	private JspSession jspSession;
	private Map<String, Object> context;
	private final List<JspTag> children = new Vector<>();
	private final Map<String, Object> attribute = new ConcurrentHashMap<>();
	private static Map<Class<? extends JspTag>, Map<String, PropertyDescriptor>> descriptors = new ConcurrentHashMap<>();

	public JspTag() {
		if(descriptors.get(this.getClass()) == null) {
			synchronized (this.getClass()) {
				if(descriptors.get(this.getClass()) == null) {
					try {
						Map<String, PropertyDescriptor> properties = new ConcurrentHashMap<>();
						BeanInfo beanInfo = Introspector.getBeanInfo(this.getClass());
						for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
							Optional.ofNullable(descriptor.getWriteMethod()).ifPresent(v -> {
								properties.put(descriptor.getName(), descriptor);
							});
						}
						descriptors.put(this.getClass(), properties);
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage(), e);
					}
				}
			}
		}
	}

	public final Object text() {
		return text;
	}

	public final void text(Object text) {
		this.text = text;
	}

	public final JspEngine engine() {
		return engine;
	}

	public final JspTag engine(JspEngine engine) {
		this.engine = engine;
		return this;
	}

	public final JspSession jspSession() {
		return jspSession;
	}

	public final JspTag jspSession(JspSession jspSession) {
		this.jspSession = jspSession;
		return this;
	}

	public final Map<String, Object> context() {
		return this.context;
	}

	public final JspTag context(Map<String, Object> context) {
		this.context = context;
		return this;
	}

	public final List<JspTag> children() {
		return children;
	}

	public final JspTag addChild(JspTag child) {
		this.children.add(child);
		return this;
	}

	public final Map<String, Object> attribute() {
		return attribute;
	}

	public final JspTag attribute(String key, Object value) {
		try {
			PropertyDescriptor descriptor = null;
			Map<String, PropertyDescriptor> properties = descriptors.get(this.getClass());
			if(properties != null && (descriptor = properties.get(key)) != null //
				&& descriptor.getWriteMethod() != null && value != null) {
				if(value instanceof String) {
					Object v = TypeUtil.cast((String) value, descriptor.getPropertyType());
					descriptor.getWriteMethod().invoke(this, v == null ? value : v);
					return this;
				}
				descriptor.getWriteMethod().invoke(this, descriptor.getPropertyType().cast(value));
			}
		} catch (Exception e) {
			Log.error(e.getMessage(), e);
		}
		return this;
	}

	public final void writerBody(JspWriter out) throws Exception {
		for (JspTag tag : this.children()) {
			tag.doTag(out);
		}
	}

	public abstract void doTag(JspWriter out) throws Exception;
}
