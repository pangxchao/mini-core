/**
 * Created the sn.mini.java.web.editor.http.ResponseEditor.java
 * @created 2017年10月28日 下午8:25:19
 * @version 1.0.0
 */
package sn.mini.java.web.editor.http;

import javax.servlet.http.HttpServletResponse;

import sn.mini.java.web.editor.IEditor;
import sn.mini.java.web.http.SNHttpServletRequest;

/**
 * sn.mini.java.web.editor.http.ResponseEditor.java
 * @author XChao
 */
public class ResponseEditor implements IEditor {

	@Override
	public Object value(String paramName, Class<?> paramType, SNHttpServletRequest request,
		HttpServletResponse response) throws Exception {
		return response;
	}

	@Override
	public Object parse(String text) {
		return null;
	}
}
