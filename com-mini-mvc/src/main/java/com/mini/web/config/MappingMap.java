package com.mini.web.config;

import com.mini.core.logger.Logger;
import com.mini.core.util.StringUtil;
import com.mini.core.util.matcher.PathMatcher;
import com.mini.core.util.matcher.PathMatcherAnt;
import com.mini.web.annotation.Action;
import com.mini.web.annotation.Action.Method;
import com.mini.web.interceptor.ActionProxy;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.EventListener;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mini.core.logger.LoggerFactory.getLogger;
import static java.lang.String.format;

public final class MappingMap implements Serializable, EventListener {
    Map<String, Map<Method, ActionProxy>> mapping = new ConcurrentHashMap<>();
    private static final Logger logger = getLogger(Configure.class);
    private final PathMatcher matcher = new PathMatcherAnt();
    private static final long serialVersionUID = 1L;

    public final Map<Method, ActionProxy> get(@Nonnull String requestUri) {
        String uri = StringUtil.strip(requestUri.strip(), "/");
        return Optional.ofNullable(mapping.get(uri)).orElseGet(() -> {
            String key = mapping.keySet().stream()   //
                    .filter(k -> matcher.match(k, uri))
                    .min(matcher.getPatternComparator(uri))
                    .orElse(null);
            if (key == null) {
                return null;
            }
            // 获取结果
            return mapping.get(key);
        });
    }


    public synchronized void put(String uri, ActionProxy proxy) {
        String key = StringUtil.strip(uri.strip(), "/");
        Optional.ofNullable(this.mapping.get(uri)).ifPresentOrElse(map -> {
            // 获取两个对象的交集，如果交集不为空，则表示有冲突，打印异常信息
            for (Action.Method method : proxy.getSupportMethod()) {
                if (map.get(method) != null) {
                    throw new RuntimeException(format("The url '%s' already exists \n%s \n%s ", //
                            key, map.get(method).getMethod(), proxy.getMethod()));
                }
                map.put(method, proxy);
            }
        }, () -> {
            Map<Method, ActionProxy> map = new ConcurrentHashMap<>();
            Stream.of(proxy.getSupportMethod()).forEach(method -> {
                map.put(method, proxy); //
            });
            mapping.put(key, map);
            logger.debug("Register Action: " + uri);
        });
    }

    public final Set<ActionProxy> getActionProxySet() {
        return mapping.values().stream().flatMap(v -> {
            return v.values().stream(); //
        }).collect(Collectors.toSet());
    }

}
