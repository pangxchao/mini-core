package com.mini.web.filter;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@Singleton
public final class CharacterEncodingFilter implements Filter {

    private String encoding = "UTF-8";

    @Inject
    public void setEncoding(@Named("mini.http.encoding.charset") @Nullable String encoding) {
        this.encoding = encoding;
    }

    @Override
    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws java.io.IOException, javax.servlet.ServletException {
        response.setCharacterEncoding(encoding);
        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }
}
