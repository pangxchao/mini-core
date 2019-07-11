package com.mini.web.test.controller;

import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.PageModel;

import javax.inject.Singleton;

@Singleton
@Controller(url = "group1", path = "group1")
public class GroupController {

    @Action(url = "user/index.htm")
    public void index(PageModel model) {
        System.out.println("======user/index.htm==========");
        model.setViewPath("index");
    }
}
