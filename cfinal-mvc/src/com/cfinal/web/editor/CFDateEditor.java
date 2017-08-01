/**
 * Created the com.cfinal.web.editor.CFDateEditor.java
 * @created 2017年7月25日 下午3:34:40
 * @version 1.0.0
 */
package com.cfinal.web.editor;

import com.alibaba.fastjson.util.TypeUtils;

/**
 * com.cfinal.web.editor.CFDateEditor.java
 * @author XChao
 */
public class CFDateEditor extends CFAbstractEditor {

	public Object parse(String text) {
		try {
			return TypeUtils.castToDate(text);
		} catch (Exception e) {
			throw new CFParamBindException(e);
		}
	}
}
