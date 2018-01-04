/**
 * Created the sn.mini.web.editor.atomic.BooleanEditor.java
 * @created 2017年10月28日 下午8:08:40
 * @version 1.0.0
 */
package sn.mini.web.editor.atomic;

import sn.mini.util.lang.TypeUtil;
import sn.mini.web.editor.IEditor;

/**
 * sn.mini.web.editor.atomic.BooleanEditor.java
 * @author XChao
 */
public class BooleanEditor implements IEditor {

	@Override
	public Object parse(String text) {
		return TypeUtil.castToBoolValue(text);
	}

}
