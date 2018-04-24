/**
 * Created the sn.mini.java.web.editor.IUserEditor.java
 * @created 2017年10月28日 下午8:32:28
 * @version 1.0.0
 */
package sn.mini.java.web.editor;

import javax.servlet.http.HttpServletResponse;

import sn.mini.java.web.http.SNHttpServletRequest;
import sn.mini.java.web.util.IUser;

/**
 * sn.mini.java.web.editor.IUserEditor.java
 * @author XChao
 */
public class IUserEditor implements IEditor {

	@Override
	public Object value(String paramName, Class<?> paramType, SNHttpServletRequest request,
		HttpServletResponse response) throws Exception {
		return request.getSession().getAttribute(IUser.USER_KEY);
	}

	public Object parse(String text) {
		return null;
	}
}
