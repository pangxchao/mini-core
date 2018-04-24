/**
 * Created the sn.mini.java.web.editor.http.PartArrayEditor.java
 * @created 2017年10月28日 下午8:27:09
 * @version 1.0.0
 */
package sn.mini.java.web.editor.http;

import javax.servlet.http.HttpServletResponse;

import sn.mini.java.web.editor.IEditor;
import sn.mini.java.web.http.SNHttpServletRequest;

/**
 * sn.mini.java.web.editor.http.PartArrayEditor.java
 * @author XChao
 */
public class PartArrayEditor implements IEditor {

	@Override
	public Object value(String paramName, Class<?> paramType, SNHttpServletRequest request,
		HttpServletResponse response) throws Exception {
		return request.getParts(paramName);
	}

	@Override
	public Object parse(String text) {
		return null;
	}
}
