/**
 * Created the com.cfinal.web.editor.CFSessionEditor.java
 * @created 2017年7月25日 下午3:34:40
 * @version 1.0.0
 */
package com.cfinal.web.editor;

import com.cfinal.web.CFRequest;
import com.cfinal.web.CFResponse;

/**
 * com.cfinal.web.editor.CFSessionEditor.java
 * @author XChao
 */
public class CFSessionEditor implements CFEditor {

	@Override
	public Object value(String paramName, Class<?> paramType, CFRequest request, //
		CFResponse response) throws Exception {
		return request.getSession();
	}
}
