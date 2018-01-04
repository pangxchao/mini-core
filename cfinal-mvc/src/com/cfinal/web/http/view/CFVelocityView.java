/**
 * Created the com.cfinal.web.http.view.CFVelocityView.java
 * @created 2017年2月11日 下午12:12:15
 * @version 1.0.0
 */
package com.cfinal.web.http.view;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.log.NullLogChute;

import com.cfinal.web.CFServletContext;
import com.cfinal.web.http.CFHttpServletRequest;
import com.cfinal.web.http.CFHttpServletResponse;
import com.cfinal.web.model.CFModel;
import com.cfinal.web.model.CFUser;

/**
 * com.cfinal.web.http.view.CFVelocityView.java
 * @author XChao
 */
public abstract class CFVelocityView extends CFView {
	private org.apache.velocity.app.VelocityEngine velocityEngine;
	private static final String FILE_SUFFIX = ".vm";

	protected void initialized(CFServletContext context) throws Exception {
		Properties properties = new Properties();
		File loadPthFile = new File(context.getWebRealPath(), "WEB-INF");
		properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, loadPthFile.toString());
		properties.setProperty(Velocity.ENCODING_DEFAULT, getContext().getEncoding());
		properties.setProperty(Velocity.INPUT_ENCODING, getContext().getEncoding());
		properties.setProperty(Velocity.OUTPUT_ENCODING, getContext().getEncoding());
		properties.setProperty(Velocity.RUNTIME_LOG_LOGSYSTEM_CLASS, NullLogChute.class.getName());
		velocityEngine = new org.apache.velocity.app.VelocityEngine();
		velocityEngine.init(properties);
	}

	@Override
	public void generator(CFModel model, String viewPath, CFHttpServletRequest request, //
		CFHttpServletResponse response) throws Exception {
		Map<String, Object> data = new HashMap<>();
		data.put("error", model.getError());
		data.put("message", model.getMessage());
		for (String key : model.keySet()) {
			data.put(key, model.getData(key));
		}
		this.generator(data, viewPath, request, response);
	}

	/**
	 * 生成页面内容，并写入response
	 * @param data
	 * @param viewPath
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void generator(Map<String, Object> data, String viewPath, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		Writer writer = null;
		try {
			data.put("sysBasePath", getContext().getWebRootPath());
			data.put("iuser", request.getSession().getAttribute(CFUser.USER_KEY));
			Template template = velocityEngine.getTemplate(viewPath + FILE_SUFFIX);
			VelocityContext context = new VelocityContext(data);
			writer = response.getWriter();
			template.merge(context, writer);
			writer.flush();
		} finally {
			if(writer != null) {
				writer.close();
			}
		}
	}

	/**
	 * 生成字符串内容
	 * @param data
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public String merge(Map<String, Object> data, String viewPath) throws Exception {
		data.put("sysBasePath", getContext().getWebRootPath());
		Template template = velocityEngine.getTemplate(viewPath + FILE_SUFFIX);
		VelocityContext context = new VelocityContext(data);
		StringWriter writer = new StringWriter();
		template.merge(context, writer);
		return writer.toString();
	}

	/**
	 * 生成字符串内容
	 * @param data 数据
	 * @param content velocity字符串
	 * @return
	 * @throws Exception
	 */
	public String evaluate(Map<String, Object> data, String content) throws Exception {
		data.put("sysBasePath", getContext().getWebRootPath());
		VelocityContext context = new VelocityContext(data);
		StringWriter writer = new StringWriter();
		velocityEngine.evaluate(context, writer, "", content);
		return writer.toString();
	}
}
