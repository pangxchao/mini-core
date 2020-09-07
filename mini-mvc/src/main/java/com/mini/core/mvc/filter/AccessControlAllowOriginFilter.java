package com.mini.core.mvc.filter;

import com.mini.core.mvc.support.config.Configures;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;

/**
 * 跨域请求过虑器
 *
 * @author xchao
 */
@Component
public class AccessControlAllowOriginFilter implements Filter {

    @NotNull
    private final Configures configures;

    @Autowired
    public AccessControlAllowOriginFilter(@NotNull Configures configures) {
        this.configures = configures;
    }

    @Override
    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ofNullable(response).filter(res -> res instanceof HttpServletResponse).map(res -> (HttpServletResponse) res).ifPresent(res -> {
            res.setHeader("Access-Control-Allow-Credentials", valueOf(configures.isAccessControlAllowCredentials()));
            res.setHeader("Access-Control-Allow-Methods", configures.getAccessControlAllowMethods());
            res.setHeader("Access-Control-Allow-Headers", configures.getAccessControlAllowHeaders());
            res.setHeader("Access-Control-Allow-Origin", configures.getAccessControlAllowOrigin());
            res.setHeader("Access-Control-Max-Age", valueOf(configures.getAccessControlMaxAge()));
        });
        chain.doFilter(request, response);
    }
}
