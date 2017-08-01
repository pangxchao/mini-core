/**
 * Created the com.cfinal.web.editor.atomic.CFAtomicShortEditor.java
 * @created 2016年9月25日 上午3:20:07
 * @version 1.0.0
 */
package com.cfinal.web.editor.atomic;

import com.cfinal.util.cast.CFCastUtil;
import com.cfinal.web.editor.CFAbstractEditor;

/**
 * com.cfinal.web.editor.atomic.CFAtomicShortEditor.java
 * @author XChao
 */
public class CFAtomicShortEditor extends CFAbstractEditor {
	@Override
	public Object parse(String text) {
		return CFCastUtil.castShort(text);
	}

}
