package com.mini.web.model;

import com.alibaba.fastjson.JSON;
import com.mini.util.collection.MiniArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * Json List Model 类实现
 * @author xchao
 */
public final class ModelJsonList extends MiniArrayList implements IModel<ModelJsonList> {
    private static final long serialVersionUID = 6324502603232680211L;
    private int status = HttpServletResponse.SC_OK;
    private String contentType = "text/plain";
    private String message;

    @Override
    public ModelJsonList toChild() {
        return this;
    }

    @Override
    public ModelJsonList sendError(int status) {
        this.status = status;
        return toChild();
    }

    @Override
    public ModelJsonList sendError(int status, String message) {
        this.message = message;
        this.status  = status;
        return toChild();
    }


    @Override
    public ModelJsonList setContentType(String contentType) {
        this.contentType = contentType;
        return toChild();
    }


    /**
     * 添加数据
     * @param value 数据值
     * @return {@Code #this}
     */
    public ModelJsonList addData(Object value) {
        super.add(value);
        return toChild();
    }


    /**
     * 添加数据
     * @param index 数据索引
     * @param value 数据值
     * @return {@Code #this}
     */
    public ModelJsonList addData(int index, Object value) {
        super.set(index, value);
        return toChild();
    }

    @Override
    public void submit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 错误码处理和返回数据格式处理
        response.setContentType(contentType);
        if (status != HttpServletResponse.SC_OK) {
            response.sendError(status, message);
            return;
        }

        // 写入返回数据 并刷新流
        try (Writer writer = response.getWriter()) {
            writer.write(JSON.toJSONString(this));
            writer.flush();
        }
    }
}
