/**
 * Created the com.cfinal.web.editor.CFPartArrayEditor.java
 * @created 2017年7月25日 下午3:34:40
 * @version 1.0.0
 */
package com.cfinal.web.editor;

import com.cfinal.web.CFRequest;
import com.cfinal.web.CFResponse;
import com.cfinal.web.central.CFParameter;

/**
 * com.cfinal.web.editor.CFPartArrayEditor.java
 * @author XChao
 */
public class CFPartArrayEditor implements CFEditor {

	@Override
	public Object value(String paramName, Class<?> paramType, CFRequest request, CFResponse response)
		throws Exception {
		Object value = ((CFRequest) request).getParts(paramName);
		request.setAttribute(CFParameter.PARAMETER_KEY + paramName, value);
		return value;
	}

}
