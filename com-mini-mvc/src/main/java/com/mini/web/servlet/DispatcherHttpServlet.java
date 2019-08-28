
package com.mini.web.servlet;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import java.util.function.BiConsumer;


/**
 * 默认的Servlet
 * @author xchao
 */
@Named
@Singleton
public final class DispatcherHttpServlet extends AbstractDispatcherHttpServlet {
    private static final long serialVersionUID = -522779044228588138L;

    @Override
    protected String getInvocationProxyUri(String servletPath) {
        return servletPath;
    }

    @Override
    protected boolean useSuffixPatternMatch(boolean suffixPattern) {
        return suffixPattern;
    }

    @Override
    protected boolean useTrailingSlashMatch(boolean trailingSlash) {
        return trailingSlash;
    }

    @Override
    protected BiConsumer<String, String> getBiConsumer(HttpServletRequest request) {
        return request::setAttribute;
    }
}
