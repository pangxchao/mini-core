/**
 * Created the com.cfinal.web.editor.CFPartArrayEditor.java
 * @created 2017年7月25日 下午3:34:40
 * @version 1.0.0
 */
package com.cfinal.web.editor.array;

import com.cfinal.web.editor.CFEditor;
import com.cfinal.web.http.CFHttpServletRequest;
import com.cfinal.web.http.CFHttpServletResponse;
import com.cfinal.web.http.CFParameter;

/**
 * com.cfinal.web.editor.CFPartArrayEditor.java
 * @author XChao
 */
public class CFPartArrayEditor implements CFEditor {

	@Override
	public Object value(String paramName, Class<?> paramType, CFHttpServletRequest request, CFHttpServletResponse response)
		throws Exception {
		Object value = ((CFHttpServletRequest) request).getParts(paramName);
		request.setAttribute(CFParameter.PARAMETER_KEY + paramName, value);
		return value;
	}

}
