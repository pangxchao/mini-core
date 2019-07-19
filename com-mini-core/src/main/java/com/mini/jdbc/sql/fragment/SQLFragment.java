package com.mini.jdbc.sql.fragment;


import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

import static com.mini.util.StringUtil.join;

public interface SQLFragment {

    /**
     * 获取SQL片断内容
     * @return 片断内容
     */
    @Nonnull
    String toString();

    /**
     * 获取连接结果
     * @param join      连接字符
     * @param fragments 片段列表
     * @return 连接结果
     */
    default String toText(String join, SQLFragment... fragments) {
        return join(join, Arrays.stream(fragments).map( //
                SQLFragment::toString).toArray());
    }

    /**
     * 获取连接结果
     * @param join         连接字符
     * @param fragmentList 片段列表
     * @return 连接结果
     */
    default String toText(String join, List<?> fragmentList) {
        return join(join, fragmentList.stream().map( //
                Object::toString).toArray());
    }

    /**
     * 获取连接结果
     * @param join    连接字符
     * @param strings 片段列表
     * @return 连接结果
     */
    default String toText(String join, String... strings) {
        return join(join, strings);
    }
}
