/**
 * Created the sn.mini.web.editor.http.PartArrayEditor.java
 * @created 2017年10月28日 下午8:27:09
 * @version 1.0.0
 */
package sn.mini.web.editor.http;

import javax.servlet.http.HttpServletResponse;

import sn.mini.web.editor.IEditor;
import sn.mini.web.http.SNHttpServletRequest;

/**
 * sn.mini.web.editor.http.PartArrayEditor.java
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
