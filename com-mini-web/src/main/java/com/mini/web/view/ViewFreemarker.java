package com.mini.web.view;

import com.mini.util.StringUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

import static freemarker.template.Configuration.VERSION_2_3_28;

@Singleton
public class ViewFreemarker implements IView, Serializable {
    private static final long serialVersionUID = 5687496460307660404L;
    private Configuration configuration;

    @Inject
    @Named("mini.mvc.view.prefix")
    private String prefix;

    @Inject
    @Named("mini.mvc.view.suffix")
    private String suffix;

    @Inject
    private ServletContext context;


    @Override
    public void generator(Map<String, Object> data, String viewPath, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try (OutputStream outputStream = response.getOutputStream()) {
            try (Writer out = new OutputStreamWriter(outputStream)) {
                getTemplate(viewPath).process(data, out);
            }
        }
    }

    /**
     * 获取  Template 对象
     * @param viewPath 模板路径
     * @return Template 对象
     */
    protected Template getTemplate(String viewPath) throws IOException {
        Configuration config = getConfiguration();
        return config.getTemplate(viewPath + suffix);
    }

    /**
     * 获取 Configuration 对象
     * @return Configuration 对象
     */
    protected Configuration getConfiguration() {
        if (this.configuration != null) return configuration;
        configuration = new Configuration(VERSION_2_3_28);
        if (StringUtil.startsWith(prefix, "classpath:")) {
            String pre = StringUtil.substring(prefix, 10);
            pre = StringUtil.def(pre, "templates");
            setClassForTemplateLoading(pre);
            return configuration;
        }
        String pre = StringUtil.def(prefix, "WEB-INF");
        setServletContextForTemplateLoading(pre);
        return configuration;
    }

    private Configuration setClassForTemplateLoading(String basePackagePath) {
        configuration.setClassForTemplateLoading(getClass(), basePackagePath);
        return configuration;
    }

    private Configuration setServletContextForTemplateLoading(String basePackagePath) {
        configuration.setServletContextForTemplateLoading(context, basePackagePath);
        return configuration;
    }
}
