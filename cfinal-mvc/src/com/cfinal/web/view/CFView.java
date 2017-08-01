/**
 * Created the com.xcc.web.view.CFView.CFView.java
 * @created 2017年2月11日 上午11:56:46
 * @version 1.0.0
 */
package com.cfinal.web.view;

import com.cfinal.web.CFRequest;
import com.cfinal.web.CFResponse;
import com.cfinal.web.central.CFBasics;
import com.cfinal.web.model.CFModel;

/**
 * com.xcc.web.view.CFView.CFView.java
 * @author XChao
 */
public interface CFView extends CFBasics {

	/**
	 * 生成页面内容，并写入response
	 * @param model
	 * @param viewPath
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	void generator(CFModel model, String viewPath, CFRequest request, CFResponse response)
		throws Exception;
}
