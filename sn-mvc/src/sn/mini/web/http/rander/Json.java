/**
 * Created the sn.mini.web.http.rander.Json.java
 * @created 2017年10月25日 下午4:53:56
 * @version 1.0.0
 */
package sn.mini.web.http.rander;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import sn.mini.util.lang.StringUtil;
import sn.mini.web.SNInitializer;
import sn.mini.web.http.view.IView;
import sn.mini.web.model.IModel;

/**
 * sn.mini.web.http.rander.Json.java
 * @author XChao
 */
public final class Json implements IRender {

	@Override
	public void render(IModel model, IView view, String viewPath, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		// 返回数据格式处理
		response.setContentType("text/plain; charset=" + SNInitializer.getEncoding());
		if(StringUtil.isNotBlank(model.getContentType())) {
			response.setContentType(model.getContentType());
		}
		try (Writer writer = response.getWriter();) {

			writer.write(JSON.toJSONString(model.getModelData()));
			writer.flush(); // 写入返回数据 并刷新流
		}
	}

}
