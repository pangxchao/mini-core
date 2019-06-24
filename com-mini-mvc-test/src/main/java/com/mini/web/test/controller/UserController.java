package com.mini.web.test.controller;

import com.mini.dao.Paging;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.ModelPage;

import javax.inject.Singleton;

@Singleton
@Controller(url = "front/user")
public class UserController {

    @Action(url = "index.htm")
    public void index(ModelPage mode, int index, int page, int rows) {
        System.out.println(mode);
    }

    @Action(url = "page.htm")
    public void page(ModelPage model, Paging paging) {
        model.addData("paging", paging);
    }
}
