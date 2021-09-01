package com.mini.core.mvc;

import com.mini.core.mvc.validation.ValidateException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义错误处理类，防止自定义错误打印异常日志
 *
 * @author pangchao
 */
@ControllerAdvice
public class MiniErrorExceptionHandler {

    /**
     * 自定义错误处理异常类处理器
     *
     * @param request  HttpServletRequest 对象
     * @param response HttpServletResponse 对象
     */
    @ExceptionHandler({ValidateException.class})
    public void validate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/error").forward(request, response);
    }
}
