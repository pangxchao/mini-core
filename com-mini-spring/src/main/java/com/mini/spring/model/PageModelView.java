package com.mini.spring.model;

import org.springframework.web.servlet.ModelAndView;

public final class PageModelView extends ModelAndView {

    public PageModelView setView(String view) {
        super.setViewName(view);
        return this;
    }

    public PageModelView addData(String key, Object value) {
        super.addObject(key, value);
        return this;
    }

}
