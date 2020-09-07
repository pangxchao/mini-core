package com.mini.core.mvc.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mini.core.mvc.support.config.Configures;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

import static java.util.Objects.requireNonNullElse;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * JSON类型的数据实现
 *
 * @author xchao
 */
@Component
@SuppressWarnings("UnusedReturnValue")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JsonModel extends IModel<JsonModel> implements Serializable {
    private static final Logger log = getLogger(JsonModel.class);
    private final Map<String, Object> map = new HashMap<>();
    private static final String TYPE = "application/json";
    private final List<Object> list = new ArrayList<>();
    private static final long serialVersionUID = 1L;
    private Object data = map;

    @Autowired
    public JsonModel(Configures configures) {
        super(configures, TYPE);
    }

    @Override
    protected JsonModel model() {
        return this;
    }

    /**
     * 获取所有的数据-有效的数据
     *
     * @return 所有数据
     */
    public final Object getData() {
        return this.data;
    }

    /**
     * 获取所有的数据-List结构的数据
     *
     * @return 所有数据
     */
    public final List<Object> getListData() {
        return this.list;
    }

    /**
     * 获取所有数据-Map结构的数据
     *
     * @return 所有数据
     */
    public final Map<String, Object> getMapData() {
        return map;
    }

    /**
     * 设置数据-自定义结构的数据
     *
     * @param data 自定义数据
     * @return {this}
     */
    public final JsonModel setData(Object data) {
        this.data = data;
        return model();
    }

    /**
     * 添加数据-Map类型的数据
     *
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
     * 添加数据-Map类型的数据
     *
     * @param name  数据键名称
     * @param value 数据值
     * @return {this}
     */
    public final JsonModel put(String name, Object value) {
        return this.addData(name, value);
    }

    /**
     * 添加所有数据-Map结构数据
     *
     * @param map Map数据
     * @return {this}
     */
    public final JsonModel addDataAll(@Nonnull Map<? extends String, ?> map) {
        this.map.putAll(map);
        data = this.map;
        return model();
    }

    /**
     * 添加所有数据-Map结构数据
     *
     * @param map Map数据
     * @return {this}
     */
    public final JsonModel putAll(@Nonnull Map<? extends String, ?> map) {
        return this.addDataAll(map);
    }

    /**
     * 添加数据-List结构数据
     *
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
     *
     * @param value 数据值
     * @return @this
     */
    public final JsonModel add(Object value) {
        return this.addData(value);
    }

    /**
     * 添加数据-List结构数据
     *
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
     *
     * @param values 数据值
     * @return {this}
     */
    public final JsonModel addAll(Collection<?> values) {
        return this.addDataAll(values);
    }

    /**
     * 添加数据-List结构数据
     *
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
     *
     * @param index 数据索引
     * @param value 数据值
     * @return {this}
     */
    public final JsonModel set(int index, Object value) {
        return this.setData(index, value);
    }

    /**
     * 添加数据-List结构数据
     *
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
     * 添加数据-List结构数据
     *
     * @param index  数据索引
     * @param values 数据值
     * @return {this}
     */
    public final JsonModel setAll(int index, Collection<?> values) {
        return setDataAll(index, values);
    }

    /**
     * 设置实体数据
     *
     * @param data 实体数据
     * @return {@link PageModel}
     */
    public JsonModel putDataAll(Object data) {
        Optional.ofNullable(data).map(JSON::toJSON)
                .filter(it -> it instanceof JSONObject)
                .map(it -> (JSONObject) it)
                .ifPresent(it -> it.forEach(this::addData));
        return model();
    }

    /**
     * 设置实体数据
     *
     * @param data 实体数据
     * @return {@link PageModel}
     */
    public JsonModel putAll(Object data) {
        return putDataAll(data);
    }

    @Override
    protected void onError(HttpServletRequest request, HttpServletResponse response) {
        String message = requireNonNullElse(getMessage(), "Service Error");
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
    protected void doSubmit(HttpServletRequest request, HttpServletResponse response) {
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
