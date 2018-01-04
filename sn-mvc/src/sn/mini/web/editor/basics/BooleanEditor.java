/**
 * Created the sn.mini.web.editor.basics.BooleanEditor.java
 * @created 2017年10月28日 下午8:23:41
 * @version 1.0.0
 */
package sn.mini.web.editor.basics;

import sn.mini.util.lang.TypeUtil;
import sn.mini.web.editor.IEditor;

/**
 * sn.mini.web.editor.basics.BooleanEditor.java
 * @author XChao
 */
public class BooleanEditor implements IEditor {

	@Override
	public Object parse(String text) {
		return TypeUtil.castToBool(text);
	}

}
