/**
 * Created the sn.mini.java.web.editor.IDaoEditor.java
 * @created 2017年10月28日 下午8:30:04
 * @version 1.0.0
 */
package sn.mini.java.web.editor;

import javax.servlet.http.HttpServletResponse;

import sn.mini.java.jdbc.DaoManager;
import sn.mini.java.web.http.SNHttpServletRequest;

/**
 * sn.mini.java.web.editor.IDaoEditor.java
 * @author XChao
 */
public class IDaoEditor implements IEditor {

	@Override
	public Object value(String paramName, Class<?> paramType, SNHttpServletRequest request,
		HttpServletResponse response) throws Exception {
		return DaoManager.getDao(paramName);
	}

	public Object parse(String text) {
		return DaoManager.getDao(text);
	}
}
