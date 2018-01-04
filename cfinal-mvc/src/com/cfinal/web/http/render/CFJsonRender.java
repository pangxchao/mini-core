/**
 * Created the com.cfinal.web.render.CFJsonRender.java
 * @created 2016年10月6日 下午8:55:56
 * @version 1.0.0
 */
package com.cfinal.web.http.render;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.cfinal.web.http.CFHttpServletRequest;
import com.cfinal.web.http.CFHttpServletResponse;
import com.cfinal.web.http.view.CFView;
import com.cfinal.web.model.CFModel;

/**
 * com.cfinal.web.render.CFJsonRender.java
 * @author XChao
 */
public class CFJsonRender extends CFRender {

	@Override
	public void render(CFModel model, CFView view, String viewPath, CFHttpServletRequest request, //
		CFHttpServletResponse response) throws Exception {
		// 返回数据格式处理
		response.setContentType("text/plain; charset=" + getContext().getEncoding());
		if(StringUtils.isNotBlank(model.getContentType())) {
			response.setContentType(model.getContentType());
		}
		try {
			// 写入返回数据
			String result = JSON.toJSONString(model.getDataModel());
			response.getWriter().write(result);
			response.getWriter().flush();
		} finally {
			response.getWriter().close();
		}
	}

}
