/**
 * Created the com.cfinal.web.editor.CFLongEditor.java
 * @created 2017年7月25日 下午3:34:40
 * @version 1.0.0
 */
package com.cfinal.web.editor;

import org.apache.commons.lang.StringUtils;

import com.cfinal.util.cast.CFCastUtil;

/**
 * com.cfinal.web.editor.CFLongEditor.java
 * @author XChao
 */
public class CFLongEditor extends CFAbstractEditor {

	@Override
	public Object parse(String text) {
		if(StringUtils.isBlank(text)) {
			return null;
		}
		return CFCastUtil.castLong(text);
	}

}
