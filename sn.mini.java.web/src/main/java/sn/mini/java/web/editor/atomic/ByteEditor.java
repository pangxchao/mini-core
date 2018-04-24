/**   
 * Created the sn.mini.java.web.editor.atomic.ByteEditor.java
 * @created 2017年10月28日 下午8:05:35 
 * @version 1.0.0 
 */
package sn.mini.java.web.editor.atomic;

import sn.mini.java.util.lang.TypeUtil;
import sn.mini.java.web.editor.IEditor;

/**   
 * sn.mini.java.web.editor.atomic.ByteEditor.java 
 * @author XChao  
 */
public class ByteEditor implements IEditor {

	@Override
	public Object parse(String text) {
		return TypeUtil.castToByteValue(text);
	}

}
