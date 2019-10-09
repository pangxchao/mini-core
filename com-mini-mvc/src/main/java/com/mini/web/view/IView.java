package com.mini.web.view;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 视图页面翻译
 * @author xchao
 */
public interface IView extends Serializable {
    /**
     * 生成页面
     * @param data     传入页面方法
     * @param viewPath 视图文件路径
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    void generator(Map<String, Object> data, String viewPath, HttpServletRequest request,
            HttpServletResponse response) throws Exception;
}
