/**
 * Created the com.cfinal.web.editor.CFAbstractEditor.java
 * @created 2017年7月25日 下午3:34:40
 * @version 1.0.0
 */
package com.cfinal.web.editor;

import com.cfinal.web.CFRequest;
import com.cfinal.web.CFResponse;
import com.cfinal.web.central.CFParameter;

/**
 * com.cfinal.web.editor.CFAbstractEditor.java
 * @author XChao
 */
public abstract class CFAbstractEditor implements CFEditor {

	@Override
	public Object value(String paramName, Class<?> paramType, CFRequest request, //
		CFResponse response) throws Exception {
		Object value = parse(request.getParameter(paramName));
		request.setAttribute(CFParameter.PARAMETER_KEY + paramName, value);
		return value;
	}

	public abstract Object parse(String text);
}
