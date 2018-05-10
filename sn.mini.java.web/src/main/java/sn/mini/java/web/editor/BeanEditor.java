/**
 * Created the sn.mini.java.web.editor.BeanEditor.java
 * @created 2017年10月28日 下午8:36:25
 * @version 1.0.0
 */
package sn.mini.java.web.editor;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import sn.mini.java.util.logger.Log;
import sn.mini.java.web.http.SNHttpServletRequest;

/**
 * sn.mini.java.web.editor.BeanEditor.java
 * @author XChao
 */
public class BeanEditor implements IEditor {

	@Override
	public Object value(String paramName, Class<?> paramType, SNHttpServletRequest request,
		HttpServletResponse response) throws Exception {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(paramType);
			Object instence = paramType.newInstance();
			for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
				//Field field = paramType.getDeclaredField(descriptor.getName());
				Optional.ofNullable(descriptor.getWriteMethod()).ifPresent(v -> {
					try {
						v.invoke(instence, EditorBind.getEditor(descriptor.getPropertyType())
							.value(descriptor.getName(), descriptor.getPropertyType(), request, response));
					} catch (Exception e) {
						Log.warn("Parameter instantiation failure: " + descriptor.getName() + ". ");
					}
				});
			}
			return instence;
		} catch (Exception e) {
			Log.warn("Parameter instantiation failure.");
		}
		return null;
	}

	@Override
	public Object parse(String text) {
		return null;
	}
}
