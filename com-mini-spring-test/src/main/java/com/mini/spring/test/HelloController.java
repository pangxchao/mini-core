package com.mini.spring.test;

import com.alibaba.fastjson.JSON;
import com.mini.spring.model.PageModelView;
import com.mini.util.dao.Paging;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试类
 * @author xchao
 */
@RestController
public class HelloController {

    @RequestMapping("/hello.htm")
    public ModelAndView hello(PageModelView mv) {
        return mv.setView("hello");
    }

    @RequestMapping("/page.htm")
    public ModelAndView page(PageModelView mv, Paging paging) {
        paging.setTotal(2043);
        System.out.println(JSON.toJSONString(paging));

        List<String> list = new ArrayList<>();
        for (int i = 0; i < paging.getRows(); i++) {
            list.add("这是第" + (paging.getStart() + i + 1) + "第数据");
        }

        mv.addData("list", list);
        mv.addData("paging", paging);
        return mv.setView("page");
    }
}
