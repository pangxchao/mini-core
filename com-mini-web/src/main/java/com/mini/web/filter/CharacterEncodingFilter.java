package com.mini.web.filter;

import com.mini.util.StringUtil;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Singleton
public final class CharacterEncodingFilter implements Filter {

    @Inject
    @Named("mini.http.encoding.charset")
    private String encoding;

    @Override
    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws java.io.IOException, javax.servlet.ServletException {
        if (!StringUtil.isBlank(encoding)) {
            request.setCharacterEncoding(encoding);
            response.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }
}
