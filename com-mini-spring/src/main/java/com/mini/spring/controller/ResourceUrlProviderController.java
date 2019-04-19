package com.mini.spring.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

@ControllerAdvice
public class ResourceUrlProviderController {

    private final ResourceUrlProvider resourceUrlProvider;

    public ResourceUrlProviderController(ResourceUrlProvider resourceUrlProvider) {
        this.resourceUrlProvider = resourceUrlProvider;
    }

    @ModelAttribute("urls")
    public ResourceUrlProvider urls() {
        return resourceUrlProvider;
    }
}
