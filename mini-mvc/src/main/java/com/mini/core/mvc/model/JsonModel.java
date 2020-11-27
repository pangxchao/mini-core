package com.mini.core.mvc.model;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * JSON类型的数据实现
 *
 * @author xchao
 */
@SuppressWarnings("UnusedReturnValue")
public class JsonModel extends IModel<ResponseEntity<ModelMap>, JsonModel> {
    private final ExtendedModelMap model = new ExtendedModelMap();
    private final ExtendedModelMap map = new ExtendedModelMap();
    private final List<Object> list = new ArrayList<>();
    private Object data = map;

    public JsonModel(HttpServletRequest request, HttpServletResponse response) {
        super(request, response, MediaType.APPLICATION_JSON);
        this.model.put("timestamp", new Date());
        setStatus(HttpStatus.OK);
        setMessage("");
    }

    @Override
    protected final JsonModel getThis() {
        return this;
    }

    @Override
    public final JsonModel setStatus(@NotNull HttpStatus status) {
        model.put("error", status.getReasonPhrase());
        model.put("status", status.value());
        return super.setStatus(status);
    }

    @Override
    public final JsonModel setMessage(String message) {
        model.addAttribute("message", message);
        return super.setMessage(message);
    }

    @Override
    public final JsonModel setCode(Integer code) {
        model.addAttribute("code", code);
        return super.setCode(code);
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
    public final ModelMap getMapData() {
        return map;
    }

    /**
     * 设置数据-自定义结构的数据
     *
     * @param object 自定义数据
     * @return {this}
     */
    public final JsonModel setData(Object object) {
        JsonModel.this.data = object;
        model.put("data", data);
        return getThis();
    }

    /**
     * 添加数据-Map类型的数据
     *
     * @param name  数据键名称
     * @param value 数据值
     * @return {this}
     */
    public final JsonModel put(String name, Object value) {
        JsonModel.this.map.addAttribute(name, value);
        JsonModel.this.data = this.map;
        model.put("data", data);
        return getThis();
    }

    /**
     * 添加所有数据-Map结构数据
     *
     * @param map Map数据
     * @return {this}
     */
    public final JsonModel putAll(@NotNull Map<String, ?> map) {
        JsonModel.this.map.addAllAttributes(map);
        JsonModel.this.data = this.map;
        model.put("data", data);
        return getThis();
    }

    /**
     * 设置实体数据
     *
     * @param object 实体数据
     * @return {@link PageModel}
     */
    public final JsonModel putObject(Object object) {
        JsonModel.this.map.addAttribute(object);
        JsonModel.this.data = this.map;
        model.put("data", data);
        return getThis();
    }

    /**
     * 添加数据-List结构数据
     *
     * @param value 数据值
     * @return @this
     */
    public final JsonModel add(Object value) {
        JsonModel.this.list.add(value);
        JsonModel.this.data = list;
        model.put("data", data);
        return getThis();
    }

    /**
     * 添加数据-List结构数据
     *
     * @param values 数据值
     * @return {this}
     */
    public final JsonModel addAll(Collection<?> values) {
        JsonModel.this.list.addAll(values);
        JsonModel.this.data = this.list;
        model.put("data", data);
        return getThis();
    }

    /**
     * 添加数据-List结构数据
     *
     * @param index 数据索引
     * @param value 数据值
     * @return {this}
     */
    public final JsonModel set(int index, Object value) {
        JsonModel.this.list.set(index, value);
        JsonModel.this.data = this.list;
        model.put("data", data);
        return getThis();
    }

    /**
     * 添加数据-List结构数据
     *
     * @param index  数据索引
     * @param values 数据值
     * @return {this}
     */
    public final JsonModel setAll(int index, Collection<?> values) {
        JsonModel.this.list.addAll(index, values);
        JsonModel.this.data = this.list;
        model.put("data", data);
        return getThis();
    }

    @NotNull
    @Override
    public final ResponseEntity<ModelMap> build() {
        return ResponseEntity.status(getStatus())
                .headers(getHeaders())
                .body(model);
    }

    @Override
    protected final String getDispatcherPath() {
        return "/h/json";
    }

    public final ExtendedModelMap model() {
        return model;
    }
}
