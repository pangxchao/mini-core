package com.mini.web.view;

import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@Component("viewJsp")
public class ViewJsp implements IView, Serializable {
    private static final long serialVersionUID = 4125460056674096998L;

    //@Inject
    //@Named("mini.mvc.view.prefix")
    private String prefix;

    //@Inject
    //@Named("mini.mvc.view.suffix")
    private String suffix;


    @Override
    public void generator(Map<String, Object> data, String viewPath, HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        data.keySet().forEach(key -> request.setAttribute(key, data.get(key)));
        forward(prefix + viewPath + suffix, request, response);
    }

    private void forward(String viewPath, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}
