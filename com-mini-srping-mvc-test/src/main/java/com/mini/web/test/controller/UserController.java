package com.mini.web.test.controller;

import com.mini.jdbc.Paging;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.ModelJsonMap;
import com.mini.web.model.ModelPage;

import org.springframework.stereotype.Component;

@Component
@Controller(url = "front/user")
public class UserController {

    @Action(url = "index.htm")
    public void index(ModelPage mode, int index, int page, int rows) {
        System.out.println(mode);
        System.out.println("---------index.htm-----------");
    }

    @Action(url = "page.htm")
    public void page(ModelPage model, Paging paging) {
        model.addData("paging", paging);
        System.out.println("---------page.htm-----------");
    }

    @Action(value = ModelJsonMap.class, url = "group.htm")
    public void group(ModelJsonMap model, String fileId) {
        System.out.println("---------group.htm-----------");
        model.addData("name", "value");
        System.out.println(fileId);
    }
}
