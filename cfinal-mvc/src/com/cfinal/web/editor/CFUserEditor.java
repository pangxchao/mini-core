/**
 * Created the com.cfinal.web.editor.CFUserEditor.java
 * @created 2016年10月24日 下午7:14:15
 * @version 1.0.0
 */
package com.cfinal.web.editor;

import com.cfinal.web.CFRequest;
import com.cfinal.web.CFResponse;
import com.cfinal.web.central.CFParameter;
import com.cfinal.web.entity.CFUser;

/**
 * com.cfinal.web.editor.CFUserEditor.java
 * @author XChao
 */
public class CFUserEditor implements CFEditor {

	@Override
	public Object value(String paramName, Class<?> paramType, CFRequest request, //
		CFResponse response) throws Exception {
		Object value = request.getSession().getAttribute(CFUser.USER_KEY);
		request.setAttribute(CFParameter.PARAMETER_KEY + paramName, value);
		return value;
	}

}
