package com.mini.web.model;

import com.alibaba.fastjson.JSON;
import com.mini.util.map.MiniMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.LinkedHashMap;

/**
 * Json Map Model 类实现
 * @author xchao
 */
public final class ModelJsonMap extends LinkedHashMap<String, Object> implements MiniMap<String>, IModel<ModelJsonMap> {
    private static final long serialVersionUID = -1731063292578685253L;
    private int status = HttpServletResponse.SC_OK;
    private String contentType = "text/plain";
    private String message;


    @Override
    public ModelJsonMap toChild() {
        return this;
    }

    @Override
    public ModelJsonMap sendError(int status) {
        this.status = status;
        return toChild();
    }

    @Override
    public ModelJsonMap sendError(int status, String message) {
        this.message = message;
        this.status  = status;
        return toChild();
    }


    @Override
    public ModelJsonMap setContentType(String contentType) {
        this.contentType = contentType;
        return toChild();
    }

    /**
     * 添加数据
     * @param name  数据键名称
     * @param value 数据值
     * @return {@Code #this}
     */
    public ModelJsonMap addData(String name, Object value) {
        super.put(name, value);
        return toChild();
    }

    @Override
    public void submit(HttpServletRequest request, HttpServletResponse response) throws Exception, Error {
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
