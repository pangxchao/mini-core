package com.mini.core.mvc.handler;

import com.mini.core.mvc.model.IModel;
import com.mini.core.mvc.support.config.Configures;
import com.mini.core.mvc.util.ResponseCode;
import com.mini.core.mvc.validation.ValidationException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.mini.core.util.ThrowableKt.hidden;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class ExceptionHandlerValidate implements ExceptionHandler, ResponseCode {
    private static final Logger log = getLogger(ExceptionHandlerDefault.class);

    @NotNull
    private final Configures configures;

    @Autowired
    public ExceptionHandlerValidate(@NotNull Configures configures) {
        this.configures = configures;
    }

    @Override
    public int handlerOnExecute() {
        return 0;
    }

    @Override
    public boolean supportException(@NotNull Throwable throwable) {
        return throwable instanceof ValidationException;
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public void handler(@NotNull IModel<?> model, @NotNull Throwable e, @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        try {
            final Locale locale = this.configures.getLocaleFactory().getLocal(request);
            ResourceBundle resource = ResourceBundle.getBundle("ValidationMessages", locale);
            String message = Optional.of(e).map(Throwable::getMessage).orElse("BAD_REQUEST");
            if (!message.isEmpty() && message.startsWith("{") && message.endsWith("}")) {
                message = message.substring(1, message.length() - 1);
                message = resource.getString(message);
            }
            model.setStatus(BAD_REQUEST);
            model.setMessage(message);
            // 输出调试日志
            log.info(request.getRequestURI() + "(" + e.getMessage() + ")");
        } catch (Exception | Error ex) {
            throw hidden(ex);
        }
    }
}
