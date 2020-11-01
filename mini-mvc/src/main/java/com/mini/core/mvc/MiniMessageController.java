package com.mini.core.mvc;

import com.mini.core.mvc.model.IModel;
import com.mini.core.mvc.model.JsonModel;
import com.mini.core.mvc.model.PageModel;
import com.mini.core.mvc.model.StreamModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static com.mini.core.mvc.model.IModel.MODEL_KEY;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static javax.servlet.RequestDispatcher.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class MiniMessageController implements ErrorController {

    private Charset defaultCharset;

    @Autowired(required = false)
    public void setDefaultCharset(Charset defaultCharset) {
        this.defaultCharset = defaultCharset;
    }

    @NotNull
    protected final Charset getDefaultCharset() {
        if (defaultCharset == null) {
            return UTF_8;
        }
        return defaultCharset;
    }

    @Nullable
    protected final Object getErrorRequestUri(HttpServletRequest request) {
        return request.getAttribute(ERROR_REQUEST_URI);
    }

    @Nullable
    protected final Object getErrorMessage(HttpServletRequest request) {
        return request.getAttribute(ERROR_MESSAGE);
    }

    @NotNull
    protected final HttpStatus getStatus(HttpServletRequest request) {
        return ofNullable(request.getAttribute(ERROR_STATUS_CODE))
                .map(it -> HttpStatus.valueOf((Integer) it))
                .orElse(INTERNAL_SERVER_ERROR);
    }

    @NotNull
    protected Map<String, Object> getAttr(HttpServletRequest request, @NotNull HttpStatus status) {
        final LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("timestamp", new Date(System.currentTimeMillis()));
        map.put("path", this.getErrorRequestUri(request));
        map.put("message", getErrorMessage(request));
        map.put("error", status.getReasonPhrase());
        map.put("status", status.value());
        return map;
    }


    @RequestMapping(produces = {"text/html"})
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = MiniMessageController.this.getStatus(request);
        Map<String, Object> model = getAttr(request, status);
        return new ModelAndView("error", model, status);
    }

    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = MiniMessageController.this.getStatus(request);
        var model = status != NO_CONTENT ? getAttr(request, status) : null;
        return new ResponseEntity<>(model, status);
    }


    @Nullable
    protected final <T extends IModel<?, T>> T getModel(HttpServletRequest request, Class<T> modelType) {
        return Optional.ofNullable(request.getAttribute(MODEL_KEY))
                .filter(modelType::isInstance)
                .map(modelType::cast)
                .orElse(null);
    }

    @RequestMapping(path = "stream")
    public ResponseEntity<Resource> stream(HttpServletRequest request) {
        var model = getModel(request, StreamModel.class);
        return requireNonNull(model).build();
    }

    @ResponseBody
    @RequestMapping(path = "json")
    public ResponseEntity<Object> json(HttpServletRequest request) {
        var model = getModel(request, JsonModel.class);
        return requireNonNull(model).build();
    }

    @RequestMapping(path = "page")
    public ModelAndView page(HttpServletRequest request) {
        var model = getModel(request, PageModel.class);
        return requireNonNull(model).build();
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
