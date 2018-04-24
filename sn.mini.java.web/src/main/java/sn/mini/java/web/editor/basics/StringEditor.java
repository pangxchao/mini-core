/**
 * Created the sn.mini.java.web.editor.basics.StringEditor.java
 * @created 2017年10月28日 下午8:11:27
 * @version 1.0.0
 */
package sn.mini.java.web.editor.basics;

import sn.mini.java.web.editor.IEditor;

/**
 * sn.mini.java.web.editor.basics.StringEditor.java
 * @author XChao
 */
public class StringEditor implements IEditor {

	@Override
	public Object parse(String text) {
		return text;
	}

}
