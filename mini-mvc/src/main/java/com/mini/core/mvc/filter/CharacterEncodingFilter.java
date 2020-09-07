package com.mini.core.mvc.filter;

import com.mini.core.mvc.support.config.Configures;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class CharacterEncodingFilter implements Filter {
    @NotNull
    private final Configures configures;

    @Autowired
    public CharacterEncodingFilter(@NotNull Configures configures) {
        this.configures = configures;
    }

    @Override
    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        response.setCharacterEncoding(configures.getEncoding());
        request.setCharacterEncoding(configures.getEncoding());
        chain.doFilter(request, response);
    }
}
