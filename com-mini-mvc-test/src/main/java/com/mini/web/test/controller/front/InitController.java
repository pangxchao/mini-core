package com.mini.web.test.controller.front;

import com.mini.jdbc.util.Paging;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.PageModel;
import com.mini.web.test.service.InitService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * InitController.java
 * @author xchao
 */
@Singleton
@Controller(
        path = "front/init",
        url = "front/init"
)
public class InitController {
    @Inject
    private InitService initService;

    /**
     * 实体列表首页
     * @param model 数据模型渲染器
     */
    @Action(
            url = "index.htm"
    )
    public void index(PageModel model) {
    }

    /**
     * 实体列表数据分页
     * @param model  数据模型渲染器
     * @param paging 数据分页工具
     */
    @Action(
            url = "pages.htm"
    )
    public void pages(PageModel model, Paging paging) {
        model.addData("data", initService.queryAll(paging));
        model.addData("paging", paging);
    }
}
