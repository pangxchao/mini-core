/**
 * Created the com.cfinal.web.render.CFRender.java
 * @created 2016年9月24日 下午7:44:26
 * @version 1.0.0
 */
package com.cfinal.web.render;

import java.io.IOException;

import com.cfinal.web.CFRequest;
import com.cfinal.web.CFResponse;
import com.cfinal.web.central.CFBasics;
import com.cfinal.web.control.CFActionPorxy;
import com.cfinal.web.model.CFModel;

/**
 * 页面渲染器，用于渲染action的数据到页面。 用户自定义的页面渲染器必须实现改接口
 * @author XChao
 */
public interface CFRender extends CFBasics {

	/**
	 * 页面渲染方法
	 * @param model 视图和数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void render(CFModel model, CFActionPorxy porxy, CFRequest request, //
		CFResponse response) throws Exception;
}
