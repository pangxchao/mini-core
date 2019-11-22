package com.mini.web.model;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

import static com.alibaba.fastjson.JSON.toJSON;
import static java.util.Optional.ofNullable;

/**
 * JSON类型的数据实现
 * @author xchao
 */
public class JsonModel extends IModel<JsonModel> implements Serializable {
    private final Map<String, Object> map = new HashMap<>();
    private final List<Object> list = new ArrayList<>();
    private static final String TYPE = "text/plain";
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

    @Override
    protected void onSubmit(HttpServletRequest request, HttpServletResponse response, String viewPath) throws Exception, Error {
        try (PrintWriter writer = response.getWriter()) {
            ofNullable(toJSON(data)).ifPresent(o -> {
                writer.write(o.toString()); //
            });
            writer.flush();
        }
    }
}
