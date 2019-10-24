package com.mini.mvc.kotlin.controller.back

import com.mini.web.annotation.Action
import com.mini.web.annotation.Controller
import com.mini.web.model.PageModel
import com.mini.web.util.IStatus
import javax.inject.Singleton

@Singleton
@Suppress("unused")
@Controller(path = "back/main", url = "back/main")
class MainController : IStatus {

    @Action(url = ["index.htm"])
    fun index(model: PageModel) {
        model.addData("logo", "//tva1.sinaimg.cn/crop.0.0.118.118.180/5db11ff4gw1e77d3nqrv8j203b03cweg.jpg")
        model.addData("userName", "超超")
    }

    @Action(url = ["center.htm"])
    fun center(model: PageModel) {
        println(model)
    }
}
