/**   
 * Created the sn.mini.web.editor.atomic.DoubleEditor.java
 * @created 2017年10月28日 下午8:06:51 
 * @version 1.0.0 
 */
package sn.mini.web.editor.atomic;

import sn.mini.util.lang.TypeUtil;
import sn.mini.web.editor.IEditor;

/**   
 * sn.mini.web.editor.atomic.DoubleEditor.java 
 * @author XChao  
 */
public class DoubleEditor implements IEditor {

	@Override
	public Object parse(String text) {
		return TypeUtil.castToDoubleValue(text);
	}

}
