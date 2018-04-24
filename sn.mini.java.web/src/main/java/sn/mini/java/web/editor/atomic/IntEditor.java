/**
 * Created the sn.mini.java.web.editor.atomic.IntEditor.java
 * @created 2017年10月25日 下午5:42:43
 * @version 1.0.0
 */
package sn.mini.java.web.editor.atomic;

import sn.mini.java.util.lang.TypeUtil;
import sn.mini.java.web.editor.IEditor;

/**
 * sn.mini.java.web.editor.atomic.IntEditor.java
 * @author XChao
 */
public class IntEditor implements IEditor{

	@Override
	public Object parse(String text) {
		return TypeUtil.castToIntValue(text);
	}

}
