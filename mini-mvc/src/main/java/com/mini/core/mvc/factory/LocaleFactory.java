package com.mini.core.mvc.factory;

import org.jetbrains.annotations.NotNull;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

public interface LocaleFactory {
    @NotNull
    Locale getLocal( @NotNull HttpServletRequest request);
}
