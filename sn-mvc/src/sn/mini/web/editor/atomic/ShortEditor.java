/**   
 * Created the sn.mini.web.editor.atomic.ShortEditor.java
 * @created 2017年10月25日 下午5:43:32 
 * @version 1.0.0 
 */
package sn.mini.web.editor.atomic;

import sn.mini.util.lang.TypeUtil;
import sn.mini.web.editor.IEditor;

/**   
 * sn.mini.web.editor.atomic.ShortEditor.java 
 * @author XChao  
 */
public class ShortEditor implements IEditor {

	@Override
	public Object parse(String text) {
		return (short)TypeUtil.castToIntValue(text);
	}

}
