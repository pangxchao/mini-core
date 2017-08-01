/**
 * Created the com.cfinal.web.editor.CFDBEditor.java
 * @created 2017年7月25日 下午3:34:40
 * @version 1.0.0
 */
package com.cfinal.web.editor;

import com.cfinal.db.CFDBFactory;
import com.cfinal.db.CFDB;
import com.cfinal.web.CFRequest;
import com.cfinal.web.CFResponse;
import com.cfinal.web.central.CFParameter;

/**
 * com.cfinal.web.editor.CFDBEditor.java
 * @author XChao
 */
public class CFDBEditor implements CFEditor {
	public Object value(String paramName, Class<?> paramType, CFRequest request, //
		CFResponse response) {
		try {
			CFDB db = CFDBFactory.create(paramName);
			request.setAttribute(CFParameter.PARAMETER_KEY + paramName, db);
			return db;
		} catch (Exception e) {
			throw new CFParamBindException(e);
		}
	}

}
