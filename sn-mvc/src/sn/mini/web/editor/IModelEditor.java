/**
 * Created the sn.mini.web.editor.IModelEditor.java
 * @created 2017年10月28日 下午8:34:14
 * @version 1.0.0
 */
package sn.mini.web.editor;

import javax.servlet.http.HttpServletResponse;

import sn.mini.web.http.SNHttpServletRequest;
import sn.mini.web.model.IModel;

/**
 * sn.mini.web.editor.IModelEditor.java
 * @author XChao
 */
public class IModelEditor implements IEditor {

	@Override
	public Object value(String paramName, Class<?> paramType, SNHttpServletRequest request,
		HttpServletResponse response) throws Exception {
		return request.getAttribute(IModel.MODEL_KEY);
	}

	public Object parse(String text) {
		return null;
	}
}
