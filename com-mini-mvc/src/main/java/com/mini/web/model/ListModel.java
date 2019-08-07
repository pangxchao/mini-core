package com.mini.web.model;

import com.alibaba.fastjson.JSON;
import com.mini.util.collection.MiniArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.io.Writer;
import java.util.Collection;

/**
 * Json List Model 类实现
 * @author xchao
 */
public final class ListModel extends IModel<ListModel> implements Serializable {
    private static final long serialVersionUID = 6324502603232680211L;
    private final MiniArrayList data = new MiniArrayList();
    private static final String TYPE = "text/plain";

    public ListModel() {
        super(TYPE);
    }

    @Override
    protected final ListModel model() {
        return this;
    }

    /**
     * 获取所有的数据
     * @return 所有数据
     */
    public MiniArrayList getList() {
        return this.data;
    }

    /**
     * 添加数据
     * @param value 数据值
     * @return {@Code #this}
     */
    public ListModel addData(Object value) {
        data.add(value);
        return model();
    }

    /**
     * 添加数据
     * @param values 数据值
     * @return {@Code #this}
     */
    public ListModel addDataAll(Collection<?> values) {
        data.addAll(values);
        return model();
    }

    /**
     * 添加数据
     * @param index 数据索引
     * @param value 数据值
     * @return {@Code #this}
     */
    public ListModel setData(int index, Object value) {
        data.set(index, value);
        return model();
    }

    /**
     * 添加数据
     * @param index  数据索引
     * @param values 数据值
     * @return {@Code #this}
     */
    public ListModel setDataAll(int index, Collection<?> values) {
        data.addAll(index, values);
        return model();
    }

    @Override
    protected final void submit(HttpServletRequest request, HttpServletResponse response, String viewPath) throws Exception, Error {
        // 写入返回数据 并刷新流
        try (Writer writer = response.getWriter()) {
            writer.write(JSON.toJSONString(data));
            writer.flush();
        }
    }
}
