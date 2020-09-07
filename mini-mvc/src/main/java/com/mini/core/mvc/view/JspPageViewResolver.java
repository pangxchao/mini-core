package com.mini.core.mvc.view;

import com.mini.core.mvc.support.config.Configures;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

@Component
public class JspPageViewResolver implements PageViewResolver, Serializable {
    private static final long serialVersionUID = -1L;

    @NotNull
    private final Configures configures;

    @Autowired
    public JspPageViewResolver(@NotNull Configures configures) {
        this.configures = configures;
    }

    @Override
    public void generator(Map<String, Object> data, String viewPath, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String view = configures.getViewPrefix() + viewPath + configures.getViewSuffix();
        data.forEach(request::setAttribute);
        forward(view, request, response);
    }

    private void forward(String viewPath, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}
