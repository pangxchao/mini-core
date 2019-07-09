package com.mini.web.config;

import com.mini.logger.Logger;
import com.mini.util.MappingUri;
import com.mini.web.interceptor.ActionInvocationProxy;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static com.mini.logger.LoggerFactory.getLogger;

@Component
public final class ActionInvocationProxyConfigure implements Serializable {
    private static final Logger LOGGER = getLogger(ActionInvocationProxyConfigure.class);
    private final MappingUri<ActionInvocationProxy> invocationProxy = new MappingUri<>();
    private static final long serialVersionUID = 5400886049425794748L;

    /**
     * 注册 ActionInvocation
     * @param url             URL
     * @param invocationProxy Action 代理对象
     * @return ｛@Code this｝
     */
    public ActionInvocationProxyConfigure addInvocationProxy(String url, ActionInvocationProxy invocationProxy) {
        this.invocationProxy.putIfAbsent(url, invocationProxy);
        LOGGER.debug("Register Action: " + url);
        return this;
    }

    /**
     * 获取所有 ActionInvocation 映射
     * @return 所有 ActionInvocation 映射
     */
    public MappingUri<ActionInvocationProxy> getInvocationProxy() {
        return invocationProxy;
    }
}
