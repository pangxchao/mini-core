/**   
 * Created the sn.mini.java.web.editor.basics.LongEditor.java
 * @created 2017年10月28日 下午8:20:01 
 * @version 1.0.0 
 */
package sn.mini.java.web.editor.basics;

import sn.mini.java.util.lang.TypeUtil;
import sn.mini.java.web.editor.IEditor;

/**   
 * sn.mini.java.web.editor.basics.LongEditor.java 
 * @author XChao  
 */
public class LongEditor implements IEditor {

	@Override
	public Object parse(String text) {
		return TypeUtil.castToLong(text);
	}

}
