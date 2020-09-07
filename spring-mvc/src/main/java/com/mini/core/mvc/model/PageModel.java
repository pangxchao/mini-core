package com.mini.core.mvc.model;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.Serializable;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

/**
 * Page Model 类实现
 *
 * @author xchao
 */
public class PageModel extends ModelAndView implements Serializable {

    public PageModel putAll(@NotNull Map<String, Object> map) {
        this.addAllObjects(map);
        return this;
    }

    public PageModel put(@NotNull String name, Object value) {
        this.addObject(name, value);
        return this;
    }

    public PageModel setData(Object data) {
        this.addObject(data);
        return this;
    }

    public PageModel status(HttpStatus status) {
        this.setStatus(status);
        return this;
    }

    public PageModel ok() {
        return status(OK);
    }

//    /**
//     * 返回成功响应数据
//     * @return [JsonModel]
//     */
//    fun ok(): PageModel {
//        status = HttpStatus.OK
//        return this
//    }
//
//    fun error(status: HttpStatus = HttpStatus.BAD_REQUEST, message: String? = null): PageModel {
//        // MappingJackson2JsonView
//        // StreamingResponseBody
////         ResponseBodyEmitter
//        // ResponseEntity.status(status)
//        // HttpEntity
//        // SseEmitter
////        InputStreamResource
//        this.put("message", message)
//        this.status = status
//        return this
//    }
}