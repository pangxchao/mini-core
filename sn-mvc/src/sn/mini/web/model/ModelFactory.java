/**
 * Created the sn.mini.web.model.ModelFactory.java
 * @created 2017年10月30日 下午6:24:07
 * @version 1.0.0
 */
package sn.mini.web.model;

import javax.servlet.http.HttpServletRequest;

/**
 * sn.mini.web.model.ModelFactory.java
 * @author XChao
 */
public class ModelFactory {

	public static IModel createDefaultModel(HttpServletRequest request) {
		IModel model = new DefaultModel();
		request.setAttribute(IModel.MODEL_KEY, model);
		return model;
	}
}
