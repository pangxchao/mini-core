package com.mini.core.mvc;

import com.mini.core.mvc.model.IModel;
import com.mini.core.mvc.model.JsonModel;
import com.mini.core.mvc.model.PageModel;
import com.mini.core.mvc.model.StreamModel;
import com.mini.core.mvc.util.ResponseCode;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.EventListener;
import java.util.Optional;

import static com.mini.core.mvc.model.IModel.MODEL_KEY;
import static java.util.Objects.requireNonNull;

@Controller
@RequestMapping("/h")
public class MiniMessageSupportController implements ResponseCode, EventListener {
    @Nullable
    protected final <T extends IModel<?, T>> T getModel(HttpServletRequest request, Class<T> modelType) {
        return Optional.ofNullable(request.getAttribute(MODEL_KEY))
                .filter(modelType::isInstance)
                .map(modelType::cast)
                .orElse(null);
    }

    @RequestMapping(path = "/stream")
    public final ResponseEntity<Resource> stream(HttpServletRequest request) {
        var model = getModel(request, StreamModel.class);
        return requireNonNull(model).build();
    }

    @ResponseBody
    @RequestMapping(path = "/json")
    public final ResponseEntity<ModelMap> json(HttpServletRequest request) {
        var model = getModel(request, JsonModel.class);
        return requireNonNull(model).build();
    }

    @RequestMapping(path = "/page")
    public final ModelAndView page(HttpServletRequest request) {
        var model = getModel(request, PageModel.class);
        return requireNonNull(model).build();
    }
}
