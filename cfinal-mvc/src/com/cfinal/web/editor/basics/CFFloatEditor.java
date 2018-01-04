/**
 * Created the com.cfinal.web.editor.CFFloatEditor.java
 * @created 2017年7月25日 下午3:34:40
 * @version 1.0.0
 */
package com.cfinal.web.editor.basics;

import org.apache.commons.lang.StringUtils;

import com.cfinal.util.cast.CFCastUtil;
import com.cfinal.web.editor.CFAbstractEditor;

/**
 * com.cfinal.web.editor.CFFloatEditor.java
 * @author XChao
 */
public class CFFloatEditor extends CFAbstractEditor {

	@Override
	public Object parse(String text) {
		if(StringUtils.isBlank(text)) {
			return null;
		}
		return CFCastUtil.castFloat(text);
	}

}
