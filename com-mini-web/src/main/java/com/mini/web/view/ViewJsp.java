package com.mini.web.view;

import com.mini.web.util.IUser;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Map;

@Singleton
public  class ViewJsp implements IView, Serializable {
    private static final long serialVersionUID = 4125460056674096998L;

    @Inject
    @Named("mini.mvc.view.prefix")
    private String prefix;

    @Inject
    @Named("mini.mvc.view.suffix")
    private String suffix;


    @Override
    public void generator(Map<String, Object> data, String viewPath, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("_user", request.getSession().getAttribute(IUser.USER_KEY));
        request.setAttribute("contextPath", request.getContextPath());
        for (Map.Entry<String, Object> en : data.entrySet()) {
            request.setAttribute(en.getKey(), en.getValue());
        }
        forward(prefix + viewPath + suffix, request, response);
    }

    private void forward(String viewPath, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher(viewPath).forward(request, response);
    }
}
