package com.mini.web.view;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Map;

@Singleton
public class JspView implements IView, Serializable {
    private static final long serialVersionUID = -1L;

    @Inject
    @Named("mini.mvc.view.prefix")
    private String prefix;

    @Inject
    @Named("mini.mvc.view.suffix")
    private String suffix;

    @Override
    public void generator(Map<String, Object> data, String viewPath, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String view = prefix + viewPath + suffix;
        data.forEach(request::setAttribute);
        forward(view, request, response);
    }

    private void forward(String viewPath, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}
