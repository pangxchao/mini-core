package com.mini.util;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;

public final class MappingUri<V> extends ConcurrentHashMap<String, V> {
    private static final long serialVersionUID = -109530601761096337L;
    private static final String REGEX = "(?:(\\{)(?<N>\\w+)+(}))";
    private static final String SPLIT_REGEX = "([\\/\\-\\.])";

    /**
     * 获取路径匹配值
     * @param key  路径
     * @param func 回调
     * @return 匹配值
     */
    public V get(String key, Function.F2<String, String> func) {
        return getOrDefault(key, entrySet().stream().filter(entry -> {
            // 分段匹配正则表达式
            String[] nArr = StringUtil.split(entry.getKey(), SPLIT_REGEX);
            String[] kArr = StringUtil.split(key, SPLIT_REGEX);
            return Arrays.equals(nArr, kArr, (v, k) -> {
                if (StringUtil.equals(v, k)) return 0;
                Matcher matcher = compile(REGEX).matcher(v);
                if (!matcher.find()) return -1;
                //  查询正则表达式的参数值，并回调
                func.apply(matcher.group("N"), k);
                return 0;
            });
        }).findFirst().map(Entry::getValue).orElse(null));
    }

    /**
     * 去掉URL中最后的“/”
     * @param url URL
     * @return URL
     */
    public static String slashRemove(String url) {
        if (!StringUtil.endsWith(url, "/")) return url;
        return url.substring(0, url.length() - 1);
    }

    public static void main(String[] args) {
        String key1 = "/a/b/1.htm";
        String key2 = "/a/b/2.htm";
        String key3 = "/a/b/{id}.htm";
        String key4 = "/a/{id}.htm";

        MappingUri<Integer> uri = new MappingUri<>();
        uri.put(key1, 1);
        uri.put(key2, 2);
        uri.put(key3, 3);
        uri.put(key4, 4);

        System.out.println(uri.get("/b/3-4.htm", (name, value) -> {
            System.out.println(name + " = " + value);
        }));

    }
}
