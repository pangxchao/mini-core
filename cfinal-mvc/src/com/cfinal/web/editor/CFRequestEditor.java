/**
 * Created the com.cfinal.web.editor.CFRequestEditor.java
 * @created 2017年7月25日 下午3:34:40
 * @version 1.0.0
 */
package com.cfinal.web.editor;

import com.cfinal.web.http.CFHttpServletRequest;
import com.cfinal.web.http.CFHttpServletResponse;

/**
 * com.cfinal.web.editor.CFRequestEditor.java
 * @author XChao
 */
public class CFRequestEditor implements CFEditor {
	public Object value(String paramName, Class<?> paramType, CFHttpServletRequest request, //
		CFHttpServletResponse response) throws Exception {
		return request;
	}
}
