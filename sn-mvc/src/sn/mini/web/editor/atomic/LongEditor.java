/**   
 * Created the sn.mini.web.editor.atomic.LongEditor.java
 * @created 2017年10月25日 下午5:42:00 
 * @version 1.0.0 
 */
package sn.mini.web.editor.atomic;

import sn.mini.util.lang.TypeUtil;
import sn.mini.web.editor.IEditor;

/**   
 * sn.mini.web.editor.atomic.LongEditor.java 
 * @author XChao  
 */
public class LongEditor implements IEditor {

	@Override
	public Object parse(String text) {
		return TypeUtil.castToLongValue(text);
	}

}
