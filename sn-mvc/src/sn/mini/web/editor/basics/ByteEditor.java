/**   
 * Created the sn.mini.web.editor.basics.ByteEditor.java
 * @created 2017年10月28日 下午8:21:57 
 * @version 1.0.0 
 */
package sn.mini.web.editor.basics;

import sn.mini.util.lang.TypeUtil;
import sn.mini.web.editor.IEditor;

/**   
 * sn.mini.web.editor.basics.ByteEditor.java 
 * @author XChao  
 */
public class ByteEditor implements IEditor {

	@Override
	public Object parse(String text) {
		return TypeUtil.castToByte(text);
	}

}
