package com.mini.util;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mini.util.StringUtil.split;
import static java.lang.String.format;

public final class MappingUri<V> extends ConcurrentHashMap<String, V> {
    private static final long serialVersionUID = -109530601761096337L;
    private static final String REGEX = "(?:(\\{)([\\w$])+?(?:}))";
    private static final String SPLIT_REGEX = "(\\/)";

    /**
     * 获取路径匹配值
     * @param key  路径
     * @param func 回调
     * @return 匹配值
     */
    public V get(String key, BiConsumer<String, String> func) {
        return getOrDefault(key, entrySet().stream().sorted((v, o) -> {
            String[] vArr = split(v.getKey(), SPLIT_REGEX);
            String[] oArr = split(o.getKey(), SPLIT_REGEX);
            return oArr.length - vArr.length;
        }).filter(entry -> {
            Matcher matcher = Pattern.compile(REGEX).matcher(entry.getKey());
            List<String> list = new CopyOnWriteArrayList<>();
            String regex = matcher.replaceAll(result -> {
                list.add(result.group(result.groupCount()));
                return format("(?<N%d>[\\\\w-$]+)+", list.size());
            });

            regex = StringUtil.format("(?:%s)", regex);
            Matcher m = Pattern.compile(regex).matcher(key);
            while (m.find()) for (int i = 0; i < list.size(); i++) {
                try {
                    String groupName = format("N%d", (i + 1));
                    func.accept(list.get(i), m.group(groupName));
                } catch (Exception | Error e) {
                    func.accept(list.get(i), "");
                }
            }

            return m.matches();
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
}
