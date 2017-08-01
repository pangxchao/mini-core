/**
 * Created the com.cfinal.web.editor.CFBeanEditor.java
 * @created 2017年7月25日 下午3:37:17
 * @version 1.0.0
 */
package com.cfinal.web.editor;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.cfinal.util.logger.CFLogger;
import com.cfinal.web.CFRequest;
import com.cfinal.web.CFResponse;
import com.cfinal.web.central.CFParameter;
import com.cfinal.web.control.CFActionParameter;

/**
 * com.cfinal.web.editor.CFBeanEditor.java
 * @author XChao
 */
public class CFBeanEditor implements CFEditor {
	private static final Map<Class<?>, BeanEditorMethod> CLAZZ_METHOD = new HashMap<>();

	private static final class BeanEditorMethod extends CFActionParameter {
		final Map<String, Method> method = new HashMap<>();

		public void add(String name, Class<?> type, CFEditor editor, Method method, Object value) {
			this.addParameter(name, type, editor, value);
			this.method.put(name, method);
		}

		public Method getMethod(int index) {
			return this.method.get(this.getName(index));
		}
	}

	@Override
	public Object value(String paramName, Class<?> paramType, CFRequest request, CFResponse response) {
		try {
			BeanEditorMethod methods = CLAZZ_METHOD.get(paramType);
			if(methods == null) {
				methods = new BeanEditorMethod();
				CFEditorBinding editorBinding = CFEditorBinding.getInstence();
				BeanInfo beanInfo = Introspector.getBeanInfo(paramType);
				for (PropertyDescriptor property : beanInfo.getPropertyDescriptors()) {
					if(property.getWriteMethod() == null) {
						continue; // 如果该属性的设置方法为空时， 该属性不处理
					}
					Class<?> parameterType = property.getPropertyType();
					// 根据类型 获取方法属性取值的编辑器
					CFEditor editor = editorBinding.getEditor(parameterType);
					// 设置方法常用数据
					methods.add(property.getName(), parameterType, editor, //
						property.getWriteMethod(), null);
				}
				CLAZZ_METHOD.put(paramType, methods);
			}

			Object instence = paramType.newInstance();
			for (int i = 0, len = methods.length(); i < len; i++) {
				methods.getMethod(i).invoke(instence, // 强制换行
					methods.getEditor(i).value(methods.getName(i), // 强制换行
						methods.getType(i), request, response));
			}
			// 将参数设置到request 的 attrbute 属性中
			request.setAttribute(CFParameter.PARAMETER_KEY + paramName, instence);
			return instence;
		} catch (Exception e) {
			CFLogger.severe("Parameter instantiation failed: ", e);
		}
		return null;
	}

}
