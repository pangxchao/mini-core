/**
 * Created the sn.mini.web.editor.ArrayEidtor.java
 * @created 2017年10月30日 下午12:53:29
 * @version 1.0.0
 */
package sn.mini.web.editor;

import java.lang.reflect.Array;

import javax.servlet.http.HttpServletResponse;

import sn.mini.util.lang.StringUtil;
import sn.mini.web.http.SNHttpServletRequest;

/**
 * sn.mini.web.editor.ArrayEidtor.java
 * @author XChao
 */
public class ArrayEidtor<T> implements IEditor {
	private final Class<T> clazz;
	private final IEditor editor;

	public ArrayEidtor(Class<T> clazz, IEditor editor) {
		this.clazz = clazz;
		this.editor = editor;
	}

	@Override
	public Object value(String paramName, Class<?> paramType, SNHttpServletRequest request,
		HttpServletResponse response) throws Exception {
		return this.parse(request.getParameterValues(paramName));
	}

	public Object parse(String text) {
		return null;
	}

	public Object parse(String[] text) {
		if(text == null || text.length == 0) {
			return null;
		}
		if(text.length == 1 && StringUtil.isBlank(text[0])) {
			return null;
		}
		Object array = Array.newInstance(this.clazz, text.length);
		for (int i = 0; i < Array.getLength(array); i++) {
			Array.set(array, i, editor.parse(text[i]));
		}
		return array;
	}

}
