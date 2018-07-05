/**
 * Created the sn.mini.java.web.http.rander.IRender.java
 *
 * @created 2017年10月19日 下午4:08:09
 * @version 1.0.0
 */
package sn.mini.java.web.http.rander;

import sn.mini.java.web.http.view.IView;
import sn.mini.java.web.model.IModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * sn.mini.java.web.http.rander.IRender.java 
 * @author XChao
 */
public interface IRender {
    /**
     * 页面渲染方法
     * @param model 视图和数据
     * @param request
     * @param response
     * @throws IOException
     */
    void render(IModel model, IView view, String viewPath, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
