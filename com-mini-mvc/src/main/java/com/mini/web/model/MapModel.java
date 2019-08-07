package com.mini.web.model;

import com.alibaba.fastjson.JSON;
import com.mini.util.map.MiniHashMap;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Map Model 类实现
 * @author xchao
 */
public final class MapModel extends IModel<MapModel> implements Serializable {
    private static final long serialVersionUID = -1731063292578685253L;
    private final MiniHashMap<String> data = new MiniHashMap<>();
    private static final String TYPE = "text/plain";

    public MapModel() {
        super(TYPE);
    }

    @Override
    protected MapModel model() {

        return this;
    }

    @Nonnull
    public final Set<String> keySet() {
        return data.keySet();
    }

    @Nonnull
    public final Collection<Object> values() {
        return data.values();
    }

    @Nonnull
    public final Set<Map.Entry<String, Object>> entrySet() {
        return data.entrySet();
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
    protected final void submit(HttpServletRequest request, HttpServletResponse response, String viewPath) throws Exception, Error {
        try (Writer writer = response.getWriter()) {
            writer.write(JSON.toJSONString(data));
            writer.flush();
        }
    }
}
