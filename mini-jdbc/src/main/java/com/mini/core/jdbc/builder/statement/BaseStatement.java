package com.mini.core.jdbc.builder.statement;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.addAll;

@SuppressWarnings("UnusedReturnValue")
public abstract class BaseStatement implements Serializable {
    protected final List<String> values = new ArrayList<>();
    protected static final String AND = ") AND (";
    protected static final String OR = ") OR (";
    private final String keyWord, join;

    public BaseStatement(String keyWord, String join) {
        this.keyWord = keyWord;
        this.join = join;
    }

    @Nonnull
    protected String getOpen() {
        return "";
    }

    @Nonnull
    protected String getClose() {
        return "";
    }

    protected final void addValues(String... values) {
        if (values != null && values.length > 0) {
            addAll(this.values, values);
        }
    }

    public final StringBuilder builder(StringBuilder builder) {
        if (this.values.isEmpty()) {
            return builder;
        }
        builder.append(keyWord).append(getOpen());
        String last = "_________________________";
        for (int i = 0; i < values.size(); i++) {
            String part = this.values.get(i);
            if (this.isJoin(i, part, last)) {
                builder.append(join);
            }
            builder.append(part);
            last = part;
        }
        builder.append(getClose());
        return builder;
    }

    private boolean isJoin(int index, String part, String last) {
        var b = index > 0 && !OR.equals(part) && !OR.equals(last);
        return b && !AND.equals(part) && !AND.equals(last);
    }

}
