/**
 * Created the sn.mini.web.editor.IEditor.java
 * @created 2017年10月25日 下午4:56:38
 * @version 1.0.0
 */
package sn.mini.web.editor;

import javax.servlet.http.HttpServletResponse;

import sn.mini.web.http.SNHttpServletRequest;

/**
 * sn.mini.web.editor.IEditor.java
 * @author XChao
 */
public interface IEditor {
	default Object value(String paramName, Class<?> paramType, SNHttpServletRequest request,
		HttpServletResponse response) throws Exception {
		return parse(request.getParameter(paramName));
	}

	public abstract Object parse(String text);
}
