/**
 * Created the sn.mini.web.editor.IUserEditor.java
 * @created 2017年10月28日 下午8:32:28
 * @version 1.0.0
 */
package sn.mini.web.editor;

import javax.servlet.http.HttpServletResponse;

import sn.mini.web.http.SNHttpServletRequest;
import sn.mini.web.util.IUser;

/**
 * sn.mini.web.editor.IUserEditor.java
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
