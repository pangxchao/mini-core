/**
 * Created the sn.mini.web.editor.http.ResponseEditor.java
 * @created 2017年10月28日 下午8:25:19
 * @version 1.0.0
 */
package sn.mini.web.editor.http;

import javax.servlet.http.HttpServletResponse;

import sn.mini.web.editor.IEditor;
import sn.mini.web.http.SNHttpServletRequest;

/**
 * sn.mini.web.editor.http.ResponseEditor.java
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
