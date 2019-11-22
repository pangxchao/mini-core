package com.mini.web.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ViewNameMethodReturnValueHandler;

@Controller
@RequestMapping("/home")
public class HomeController {

    @RequestMapping("/index.htm")
    public void index(ModelAndView model) {
        model.addObject("userName", "超超");
    }

    @RequestMapping("center.htm")
    public void center(ModelAndView model) {
        System.out.println(model.getViewName());
    }

    @RequestMapping("center/aa.htm")
    public void center1(ModelAndView model) {
        ViewNameMethodReturnValueHandler a;
        System.out.println(model.getViewName());

    }
}
