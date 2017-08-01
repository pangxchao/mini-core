/**
 * Created the com.cfinal.web.editor.CFEditor.java
 * @created 2017年7月25日 下午3:35:39
 * @version 1.0.0
 */
package com.cfinal.web.editor;

import com.cfinal.web.CFRequest;
import com.cfinal.web.CFResponse;

/**
 * com.cfinal.web.editor.CFEditor.java
 * @author XChao
 */
public interface CFEditor {
	public Object value(String paramName, Class<?> paramType, CFRequest request, CFResponse response)
		throws Exception;
}
