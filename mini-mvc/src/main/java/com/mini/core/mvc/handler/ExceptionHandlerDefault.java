package com.mini.core.mvc.handler;

import com.mini.core.mvc.model.IModel;
import com.mini.core.mvc.support.config.Configures;
import com.mini.core.mvc.util.ResponseCode;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.mini.core.util.ThrowableKt.hidden;
import static java.util.Optional.of;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public final class ExceptionHandlerDefault implements ExceptionHandler, ResponseCode {
    private static final Logger log = getLogger(ExceptionHandlerDefault.class);

    @NotNull
    private final Configures configures;

    @Autowired
    public ExceptionHandlerDefault(@NotNull Configures configures) {
        this.configures = configures;
    }

    @Override
    public int handlerOnExecute() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean supportException(@NotNull Throwable exception) {
        return true;
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public void handler(@NotNull IModel<?> model, @NotNull Throwable e, @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        try {
            final Locale locale = this.configures.getLocaleFactory().getLocal(request);
            ResourceBundle resource = ResourceBundle.getBundle("ValidationMessages", locale);
            String message = of(e).map(Throwable::getMessage).orElse("INTERNAL_SERVER_ERROR");
            if (!message.isEmpty() && message.startsWith("{") && message.endsWith("}")) {
                message = message.substring(1, message.length() - 1);
                message = resource.getString(message);
            }
            model.setStatus(INTERNAL_SERVER_ERROR);
            model.setMessage(message);
            // 输出日志
            log.error(e.getMessage(), e);
        } catch (Exception | Error ex) {
            throw hidden(ex);
        }
    }
}
