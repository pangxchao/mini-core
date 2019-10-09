package com.mini.web.model;

import static com.alibaba.fastjson.JSON.toJSONString;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Map Model 类实现
 * @author xchao
 */
public final class MapModel extends IModel<MapModel> implements Serializable {
    private static final long serialVersionUID = -1731063292578685253L;
    private final Map<String, Object> data = new HashMap<>();
    private static final String TYPE = "text/plain";

    public MapModel() {
        super(TYPE);
    }

    @Override
    protected MapModel model() {
        return this;
    }

    /**
     * 获取所有数据
     * @return 所有数据
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * 添加数据
     * @param name  数据键名称
     * @param value 数据值
     * @return {@Code #this}
     */
    public MapModel addData(String name, Object value) {
        data.put(name, value);
        return model();
    }

    /**
     * 添加所有数据
     * @param map Map数据
     * @return {@Code #this}
     */

    public MapModel addDataAll(@Nonnull Map<? extends String, ?> map) {
        data.putAll(map);
        return model();
    }

    @Override
    protected void submit(HttpServletRequest request, HttpServletResponse response, String v) throws Exception, Error {
        try (PrintWriter writer = response.getWriter()) {
            writer.write(toJSONString(data));
            writer.flush();
        }
    }
}
