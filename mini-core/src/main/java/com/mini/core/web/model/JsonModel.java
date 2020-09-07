package com.mini.core.web.model;

import com.alibaba.fastjson.JSON;
import com.mini.core.jdbc.model.Page;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * JSON类型的数据实现
 * @author xchao
 */
public class JsonModel extends IModel<JsonModel> implements Serializable {
	private static final Logger log = getLogger(JsonModel.class);
	private final Map<String, Object> map = new HashMap<>();
	private static final String TYPE = "application/json";
	private final List<Object> list = new ArrayList<>();
	private static final long serialVersionUID = 1L;
	private Object data = map;
	
	public JsonModel() {
		super(TYPE);
	}
	
	@Override
	protected JsonModel model() {
		return this;
	}
	
	/**
	 * 获取所有的数据-有效的数据
	 * @return 所有数据
	 */
	public final Object getData() {
		return this.data;
	}
	
	/**
	 * 获取所有的数据-List结构的数据
	 * @return 所有数据
	 */
	public final List<Object> getListData() {
		return this.list;
	}
	
	/**
	 * 获取所有数据-Map结构的数据
	 * @return 所有数据
	 */
	public final Map<String, Object> getMapData() {
		return map;
	}
	
	/**
	 * 设置数据-自定义结构的数据
	 * @param data 自定义数据
	 * @return {this}
	 */
	public final JsonModel setData(Object data) {
		this.data = data;
		return model();
	}
	
	/**
	 * 添加数据-Map类型的数据
	 * @param name  数据键名称
	 * @param value 数据值
	 * @return {this}
	 */
	public final JsonModel addData(String name, Object value) {
		this.map.put(name, value);
		this.data = this.map;
		return model();
	}
	
	/**
	 * 添加所有数据-Map结构数据
	 * @param map Map数据
	 * @return {this}
	 */
	public final JsonModel addDataAll(@Nonnull Map<? extends String, ?> map) {
		this.map.putAll(map);
		data = this.map;
		return model();
	}
	
	/**
	 * 添加数据-List结构数据
	 * @param value 数据值
	 * @return @this
	 */
	public final JsonModel addData(Object value) {
		this.list.add(value);
		this.data = list;
		return model();
	}
	
	/**
	 * 添加数据-List结构数据
	 * @param values 数据值
	 * @return {this}
	 */
	public final JsonModel addDataAll(Collection<?> values) {
		this.list.addAll(values);
		this.data = this.list;
		return model();
	}
	
	/**
	 * 添加数据-List结构数据
	 * @param index 数据索引
	 * @param value 数据值
	 * @return {this}
	 */
	public final JsonModel setData(int index, Object value) {
		this.list.set(index, value);
		this.data = this.list;
		return model();
	}
	
	/**
	 * 添加数据-List结构数据
	 * @param index  数据索引
	 * @param values 数据值
	 * @return {this}
	 */
	public final JsonModel setDataAll(int index, Collection<?> values) {
		this.list.addAll(index, values);
		this.data = this.list;
		return model();
	}
	
	/**
	 * 设置分页数据
	 * @param paging 分页数据
	 * @return {@link PageModel}
	 */
	public JsonModel setData(Page<?> paging) {
		this.map.put("total", paging.getTotal());
		this.map.put("limit", paging.getLimit());
		this.map.put("rows", paging.getRows());
		this.map.put("page", paging.getPage());
		this.data = this.map;
		return model();
	}
	
	@Override
	protected void onError(HttpServletRequest request, HttpServletResponse response) {
		String message = defaultIfEmpty(getMessage(), "Service Error");
		try (PrintWriter writer = response.getWriter()) {
			writer.write(JSON.toJSON(new HashMap<>() {{
				put("error", getStatus());
				put("message", message);
			}}).toString());
			// 设置返回状态并刷新数据
			response.setStatus(OK);
			writer.flush();
		} catch (IOException | Error e) {
			response.setStatus(INTERNAL_SERVER_ERROR);
			log.error(e.getMessage());
		}
	}
	
	@Override
	protected void onSubmit(HttpServletRequest request, HttpServletResponse response, String viewPath) {
		try (PrintWriter writer = response.getWriter()) {
			writer.write(JSON.toJSON(new HashMap<>() {{
				put("message", "Success");
				put("data", getData());
				put("error", 0);
			}}).toString());
			// 设置返回状态并刷新数据
			response.setStatus(OK);
			writer.flush();
		} catch (IOException | Error e) {
			response.setStatus(INTERNAL_SERVER_ERROR);
			log.error(e.getMessage());
		}
	}
}
