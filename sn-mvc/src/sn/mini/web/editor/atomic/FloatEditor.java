/**   
 * Created the sn.mini.web.editor.atomic.FloatEditor.java
 * @created 2017年10月28日 下午8:07:33 
 * @version 1.0.0 
 */
package sn.mini.web.editor.atomic;

import sn.mini.util.lang.TypeUtil;
import sn.mini.web.editor.IEditor;

/**   
 * sn.mini.web.editor.atomic.FloatEditor.java 
 * @author XChao  
 */
public class FloatEditor implements IEditor{

	@Override
	public Object parse(String text) {
		return TypeUtil.castToFloatValue(text);
	}

}
