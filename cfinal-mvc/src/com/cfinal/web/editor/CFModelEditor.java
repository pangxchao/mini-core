/**
 * Created the com.cfinal.web.editor.CFModelEditor.java
 * @created 2017年7月25日 下午3:34:40
 * @version 1.0.0
 */
package com.cfinal.web.editor;

import com.cfinal.web.http.CFHttpServletRequest;
import com.cfinal.web.http.CFHttpServletResponse;
import com.cfinal.web.http.CFParameter;
import com.cfinal.web.model.CFModel;

/**
 * com.cfinal.web.editor.CFModelEditor.java
 * @author XChao
 */
public class CFModelEditor implements CFEditor {

	public Object value(String paramName, Class<?> paramType, CFHttpServletRequest request, //
		CFHttpServletResponse response) throws Exception {
		Object value = request.getAttribute(CFModel.MODEL_KEY);
		request.setAttribute(CFParameter.PARAMETER_KEY + paramName, value);
		return value;
	}

}
