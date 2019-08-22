package com.mini.web.test.controller.mobile;

import com.mini.util.PKGenerator;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Controller;
import com.mini.web.model.StringModel;
import com.mini.web.model.factory.ModelType;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

/**
 * 主键生成控制器
 * @author xchao
 */
@Singleton
@Controller(path = "unique", url = "unique")
public final class UniqueController {

    /**
     * 获取长整型类型的主键
     * @param model 数据模型渲染器
     */
    @Action(value = ModelType.STRING, url = "id.htm")
    public void id(StringModel model, HttpServletRequest request) {
        model.append("" + PKGenerator.id());
    }

    /**
     * 获取长整型类型的主键
     * @param model 数据模型渲染器
     */
    @Action(value = ModelType.STRING, url = "uuid.htm")
    public void uuid(StringModel model, HttpServletRequest request) {
        model.append(PKGenerator.uuid());
    }
}
