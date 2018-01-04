/**
 * Created the sn.mini.web.editor.date.DateEditor.java
 * @created 2017年10月28日 下午8:31:25
 * @version 1.0.0
 */
package sn.mini.web.editor.date;

import sn.mini.util.lang.TypeUtil;
import sn.mini.web.editor.IEditor;

/**
 * sn.mini.web.editor.date.DateEditor.java
 * @author XChao
 */
public class DateEditor implements IEditor {

	@Override
	public Object parse(String text) {
		return TypeUtil.castToDate(text);
	}

}
