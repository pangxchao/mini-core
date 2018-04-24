/**
 * Created the sn.mini.java.web.editor.date.DateEditor.java
 * @created 2017年10月28日 下午8:31:25
 * @version 1.0.0
 */
package sn.mini.java.web.editor.date;

import sn.mini.java.util.lang.TypeUtil;
import sn.mini.java.web.editor.IEditor;

/**
 * sn.mini.java.web.editor.date.DateEditor.java
 * @author XChao
 */
public class DateEditor implements IEditor {

	@Override
	public Object parse(String text) {
		return TypeUtil.castToDate(text);
	}

}
