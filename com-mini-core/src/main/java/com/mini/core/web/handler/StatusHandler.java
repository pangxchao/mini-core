package com.mini.core.web.handler;

import com.mini.core.web.util.ResponseCode;
import com.mini.core.web.model.IModel;
import com.mini.core.web.util.ResponseCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface StatusHandler extends ResponseCode {
    /**
     * 该值所有处理器中不能重复
     * <P>配置该值时，不能小于"0"</P>
     * <P>该值越小，执行优先级越高</P>
     * @return 处理顺序
     */
    int handlerOnExecute();

    /**
     * 全局异常处理方法
     * @param model    数据模型渲染器
     * @param status   状态码
     * @param request  HttpServletRequest 对象
     * @param response HttpServletResponse 对象
     */
    void handler(IModel<?> model, int status, HttpServletRequest request, HttpServletResponse response);
}
