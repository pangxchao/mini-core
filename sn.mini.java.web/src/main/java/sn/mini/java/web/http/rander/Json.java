package sn.mini.java.web.http.rander;

import com.alibaba.fastjson.JSON;
import sn.mini.java.util.lang.StringUtil;
import sn.mini.java.web.SNInitializer;
import sn.mini.java.web.http.view.IView;
import sn.mini.java.web.model.IModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * sn.mini.java.web.http.rander.Json.java
 *
 * @author XChao
 */
public final class Json implements IRender {

    @Override
    public void render(IModel model, IView view, String viewPath, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 返回数据格式处理
        response.setContentType("text/plain; charset=" + SNInitializer.getEncoding());
        if (StringUtil.isNotBlank(model.getContentType())) {
            response.setContentType(model.getContentType());
        }
        try (Writer writer = response.getWriter()) {
            writer.write(JSON.toJSONString(model.getModelData()));
            writer.flush(); // 写入返回数据 并刷新流
        }
    }

}
