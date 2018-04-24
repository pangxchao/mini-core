/**   
 * Created the sn.mini.java.web.editor.basics.DoubleEditor.java
 * @created 2017年10月28日 下午8:22:34 
 * @version 1.0.0 
 */
package sn.mini.java.web.editor.basics;

import sn.mini.java.util.lang.TypeUtil;
import sn.mini.java.web.editor.IEditor;

/**   
 * sn.mini.java.web.editor.basics.DoubleEditor.java 
 * @author XChao  
 */
public class DoubleEditor implements IEditor {

	@Override
	public Object parse(String text) {
		return TypeUtil.castToDouble(text);
	}

}
