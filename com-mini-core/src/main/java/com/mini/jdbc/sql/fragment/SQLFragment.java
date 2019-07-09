package com.mini.jdbc.sql.fragment;


import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Function;

import static com.mini.util.StringUtil.join;

public interface SQLFragment {

    /**
     * 获取SQL片断内容
     * @return 片断内容
     */
    @Nonnull
    String content();

    /**
     * 获取连接结果
     * @param join 连接字符
     * @param list 片段列表
     * @return 连接结果
     */
    default String text(String join, List<SQLFragment> list) {
        return join(join, list.stream().map(func()).toArray());
    }

    private Function<SQLFragment, String> func() {
        return SQLFragment::content;
    }
}
