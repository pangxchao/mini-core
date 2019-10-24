package com.mini.web.test.context;

import com.mini.web.servlet.AbstractDispatcherHttpServlet;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.function.BiConsumer;

/**
 * 自定义Servlet
 * @author xchao
 */
public final class MiniWebMvcServlet extends AbstractDispatcherHttpServlet {
    private static final long serialVersionUID = -5104342688489225990L;

    @Override
    protected String getInvocationProxyUri(@Nonnull String requestPath, HttpServletRequest request) {
        return "/front/user/group.htm";
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
        return (n, v) -> request.setAttribute("fileId", request.getServletPath());
    }
}
