package com.mini.web.model;

import com.mini.util.StringUtil;

import javax.annotation.Nonnull;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

public interface IModel<T extends IModel> extends Serializable {
    String MODEL_KEY = "MINI_REQUEST_MODEL_KEY";

    /**
     * 转成实现类实例
     * @return {@Code this}
     */
    T toChild();

    /**
     * 发送一个错误码
     * @param status 错误码
     * @return {@Code this}
     * @see HttpServletResponse#sendError(int)
     */
    T sendError(int status);

    /**
     * 发送一个错误码
     * @param status  错误码
     * @param message 错误消息
     * @return {@Code this}
     * @see HttpServletResponse#sendError(int, String)
     */
    T sendError(int status, String message);

    /**
     * 设置 contentType.
     * @param contentType contentType
     * @return {@Code this}
     */
    T setContentType(String contentType);

    /**
     * 提交渲染页面
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    void submit(HttpServletRequest request, HttpServletResponse response) throws Exception, Error;

    // 是否为绝对路径
    private boolean isAbsoluteHttpPath(String lower) throws Error {
        return lower.matches("http(s)?://([\\s\\S])+");
    }
}
