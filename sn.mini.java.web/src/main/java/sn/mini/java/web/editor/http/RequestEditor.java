/**
 * Created the sn.mini.java.web.editor.http.RequestEditor.java
 * @created 2017年10月28日 下午8:24:27
 * @version 1.0.0
 */
package sn.mini.java.web.editor.http;

import javax.servlet.http.HttpServletResponse;

import sn.mini.java.web.editor.IEditor;
import sn.mini.java.web.http.SNHttpServletRequest;

/**
 * sn.mini.java.web.editor.http.RequestEditor.java
 * @author XChao
 */
public class RequestEditor implements IEditor {

	@Override
	public Object value(String paramName, Class<?> paramType, SNHttpServletRequest request,
		HttpServletResponse response) throws Exception {
		return request;
	}

	@Override
	public Object parse(String text) {
		return null;
	}
}
