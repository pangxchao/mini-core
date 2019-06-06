package com.mini.spring.test.controller;

import com.mini.spring.model.PageModelView;
import com.mini.spring.test.entity.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test/main")
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 首页
     * @param mv 数据和视图
     * @return 数据视图
     */
    @RequestMapping("index.htm")
    public PageModelView index(PageModelView mv) {
        System.out.println(userService);

        return mv.setView("test/main/index");
    }

}
