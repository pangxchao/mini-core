/**
 * Created the sn.mini.web.http.view.IView.java
 * @created 2017年10月25日 上午11:13:04
 * @version 1.0.0
 */
package sn.mini.web.http.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sn.mini.web.model.IModel;

/**
 * sn.mini.web.http.view.IView.java
 * @author XChao
 */
public interface IView {
	
	/**
	 * 生成页面内容，并写入response
	 * @param model
	 * @param viewPath
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public abstract void generator(IModel model, String viewPath, HttpServletRequest request,
		HttpServletResponse response) throws Exception;
	
}
