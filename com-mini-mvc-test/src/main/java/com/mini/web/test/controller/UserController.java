package com.mini.web.test.controller;

import com.mini.jdbc.util.Paging;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.MapModel;
import com.mini.web.model.PageModel;

import javax.inject.Singleton;

@Singleton
@Controller(url = "front/user")
public class UserController {

    @Action(url = "index.htm")
    public void index(PageModel mode, int index, int page, int rows) {
        System.out.println(mode);
        System.out.println("---------index.htm-----------");
    }

    @Action(url = "page.htm")
    public void page(PageModel model, Paging paging) {
        model.addData("paging", paging);
        System.out.println("---------page.htm-----------");
    }

    @Action(value = MapModel.class, url = "group.htm")
    public void group(MapModel model, String fileId) {
        System.out.println("---------group.htm-----------");
        model.addData("name", "value");
        System.out.println(fileId);
    }
}
