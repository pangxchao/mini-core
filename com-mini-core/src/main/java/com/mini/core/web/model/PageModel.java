package com.mini.core.web.model;

import com.mini.core.jdbc.model.Paging;
import com.mini.core.validate.ValidateUtil;
import com.mini.core.web.view.PageViewResolver;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * Page Model 类实现
 * @author xchao
 */
public class PageModel extends IModel<PageModel> implements Serializable {
	private final Map<String, Object> data = new HashMap<>();
	private static final long serialVersionUID = 1L;
	private static final String TYPE = "text/html";
	private final PageViewResolver view;

	public PageModel(PageViewResolver view) {
		setContentType(TYPE);
		this.view = view;
	}

	@Override
	protected PageModel model() {
		return this;
	}

	/**
	 * 获取所有数据
	 * @return 所有数据
	 */
	public final Map<String, Object> getData() {
		return data;
	}

	/**
	 * 设置分页数据
	 * @param paging 分页数据
	 * @return {@link PageModel}
	 */
	public final PageModel setData(Paging<?> paging) {
		this.data.put("total", paging.getTotal());
		this.data.put("rows", paging.getRows());
		this.data.put("page", paging.getPage());
		return model();
	}

	/**
	 * 添加数据
	 * @param name  数据键名称
	 * @param value 数据值
	 * @return {@link PageModel}
	 */
	public final PageModel addData(String name, Object value) {
		data.put(name, value);
		return model();
	}

	/**
	 * 设置分页数据
	 * @param map 数据
	 * @return {@link PageModel}
	 */
	public final PageModel setData(@Nonnull Map<? extends String, ?> map) {
		this.data.clear();
		addDataAll(map);
		return model();
	}

	/**
	 * 添加所有数据
	 * @param map Map数据
	 * @return {@link PageModel}
	 */
	public final PageModel addDataAll(@Nonnull Map<? extends String, ?> map) {
		data.putAll(map);
		return model();
	}

	@Override
	protected void onSubmit(HttpServletRequest request, HttpServletResponse response, String viewPath) throws Exception, Error {
		ValidateUtil.isNotBlank(viewPath, INTERNAL_SERVER_ERROR, "View path can not be null");
		// 生成页面
		data.put("request", request);
		data.put("response", response);
		data.put("session", request.getSession());
		data.put("context", request.getServletContext());
		view.generator(data, viewPath, request, response);
	}
}
