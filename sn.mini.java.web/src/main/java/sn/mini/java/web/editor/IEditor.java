/**
 * Created the sn.mini.java.web.editor.IEditor.java
 *
 * @created 2017年10月25日 下午4:56:38
 * @version 1.0.0
 */
package sn.mini.java.web.editor;

import javax.servlet.http.HttpServletResponse;

import sn.mini.java.web.http.SNHttpServletRequest;

/**
 * sn.mini.java.web.editor.IEditor.java
 * @author XChao
 */
public interface IEditor {
    default Object value(String paramName, Class<?> paramType, SNHttpServletRequest request, HttpServletResponse response) throws Exception {
        return parse(request.getParameter(paramName));
    }

    Object parse(String text);
}
