/**
 * Created the com.cfinal.web.editor.CFArrayEditorF.java 
 * @created 2017年7月25日 下午3:34:40
 * @version 1.0.0
 */
package com.cfinal.web.editor;

import java.lang.reflect.Array;

import org.apache.commons.lang.StringUtils;

import com.cfinal.web.CFRequest;
import com.cfinal.web.CFResponse;
import com.cfinal.web.central.CFParameter;

/**
 * 
 * com.cfinal.web.editor.CFArrayEditor.java 
 * @author XChao
 */
public class CFArrayEditor<T> implements CFEditor {
	private Class<T> clazz;
	private CFEditor editor;

	public CFArrayEditor(Class<T> clazz, CFEditor xiEditor) {
		this.clazz = clazz;
		this.editor = xiEditor;
	}

	@Override
	public Object value(String paramName, Class<?> paramType, CFRequest request, //
		CFResponse response) throws Exception {
		Object value = parse(request.getParameterValues(paramName));
		request.setAttribute(CFParameter.PARAMETER_KEY + paramName, value);
		return value;
	}

	public Object parse(String[] text) {
		if(text == null || text.length == 0) {
			return null;
		}
		if(text.length == 1 && StringUtils.isBlank(text[0])) {
			return null;
		}
		Object array = Array.newInstance(this.clazz, text.length);
		for (int i = 0; i < Array.getLength(array); i++) {
			Array.set(array, i, ((CFAbstractEditor) editor).parse(text[i]));
		}
		return array;
	}
}
