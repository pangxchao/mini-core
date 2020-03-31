package com.mini.core.web.model;

import com.mini.core.jdbc.model.Paging;
import com.mini.core.web.view.PageViewResolver;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static com.mini.core.validation.Validator.status;
import static com.mini.core.web.util.ResponseCode.INTERNAL_SERVER_ERROR;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.slf4j.LoggerFactory.getLogger;


/**
 * Page Model 类实现
 * @author xchao
 */
public class PageModel extends IModel<PageModel> implements Serializable {
	private static final Logger log = getLogger(PageModel.class);
	private final Map<String, Object> data = new HashMap<>();
	private static final long serialVersionUID = 1L;
	private static final String TYPE = "text/html";
	
	public PageModel() {
		super(TYPE);
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
		try {
			status(INTERNAL_SERVER_ERROR).message("View path can not be null").is(isNotBlank(viewPath));
			PageViewResolver view = getConfigures().getPageViewResolver();
			view.generator(data, viewPath, request, response);
		} catch (IOException | Error e) {
			log.error(e.getMessage());
			response.setStatus(500);
		}
	}
}
