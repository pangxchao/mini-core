package com.mini.web.model;

import static com.alibaba.fastjson.JSON.toJSONString;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Json List Model 类实现
 * @author xchao
 */
public final class ListModel extends IModel<ListModel> implements Serializable {
    private static final long serialVersionUID = 6324502603232680211L;
    private final List<Object> data = new ArrayList<>();
    private static final String TYPE = "text/plain";

    public ListModel() {
        super(TYPE);
    }

    @Override
    protected ListModel model() {
        return this;
    }

    /**
     * 获取所有的数据
     * @return 所有数据
     */
    public List<Object> getData() {
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
    protected void submit(HttpServletRequest request, HttpServletResponse response, String v) throws Exception, Error {
        try (PrintWriter writer = response.getWriter()) {
            writer.write(toJSONString(data));
            writer.flush();
        }
    }
}
