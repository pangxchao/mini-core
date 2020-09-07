package com.mini.core.mvc.factory;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

@Component
public final class DefaultLocaleFactory implements LocaleFactory {

    @NotNull
    @Override
    public final Locale getLocal(@NotNull HttpServletRequest request) {
        return Optional.of(request).map(r -> r.getHeader("Accept-Language"))
                .map(Locale::forLanguageTag).orElse(Locale.getDefault());
    }
}
