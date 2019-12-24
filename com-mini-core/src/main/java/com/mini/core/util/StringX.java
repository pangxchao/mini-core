package com.mini.core.util;

import javax.annotation.processing.Processor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.EventListener;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.mini.core.validate.ValidateUtil.MOBILE;
import static java.lang.Character.*;

public interface StringX extends EventListener {

    static Function<String, String> format(Map<String, Object> map) {
        return value -> {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object key = entry.getKey(), val = entry.getValue();
                value = value.replaceAll("\\{" + key + "}", val + "");
            }
            return value;
        };
    }

    static Function<String, URL> toURL() {
        return value -> {
            try {
                return new URL(value);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        };
    }

    static Function<String, String> urlEncode(String charset) {
        return value -> {
            try {
                return URLEncoder.encode(value, charset);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    static Function<String, String> urlEncode(Charset charset) {
        return value -> {
            try {
                return URLEncoder.encode(value, charset);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    static Function<String, String> firstUpperCase() {
        return value -> toUpperCase(value.charAt(0)) //
                + value.substring(1);
    }

    static Function<String, String> firstLowerCase() {
        return value -> toLowerCase(value.charAt(0)) //
                + value.substring(1);
    }

    static Function<String, String> toJavaName(boolean firstUpperCase) {
        return value -> {
            String result = Stream.of(value.split("_")).map(firstUpperCase())
                    .collect(Collectors.joining());
            return firstUpperCase ? result : firstLowerCase().apply(result);
        };
    }

    static Function<String, String> toDBName() {
        return value -> {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < value.length(); i++) {
                char ch = value.charAt(i);
                if (i > 0 && isUpperCase(value.charAt(i))) {
                    result.append("_");
                }
                result.append(toLowerCase(ch));
            }
            return result.toString();
        };
    }

    static Function<String, String> phoneEncode() {
        return value -> {
            if (!value.matches(MOBILE) || value.length() < 11) {
                return value;
            }
            return value.substring(0, 3) + "****" //
                    + value.substring(7);
        };
    }
}
