/**
 * Created the sn.mini.java.web.editor.atomic.BooleanEditor.java
 * @created 2017年10月28日 下午8:08:40
 * @version 1.0.0
 */
package sn.mini.java.web.editor.atomic;

import sn.mini.java.util.lang.TypeUtil;
import sn.mini.java.web.editor.IEditor;

/**
 * sn.mini.java.web.editor.atomic.BooleanEditor.java
 * @author XChao
 */
public class BooleanEditor implements IEditor {

	@Override
	public Object parse(String text) {
		return TypeUtil.castToBoolValue(text);
	}

}
