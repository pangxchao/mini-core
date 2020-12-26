package com.mini.core.jdbc.builder.statement;

import com.mini.core.jdbc.builder.AbstractSql;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.addAll;

@SuppressWarnings("UnusedReturnValue")
public interface BaseStatement<T extends BaseStatement<T>> {
    T addValues(String... values);

    void builder(StringBuilder builder);

    abstract class BaseStatementImpl<T extends BaseStatement<T>> implements BaseStatement<T>, Serializable {
        private final List<String> values = new ArrayList<>();
        protected static final String AND = ") AND (";
        protected static final String OR = ") OR (";
        private final String join, open, close;
        protected final AbstractSql<?> sql;

        public BaseStatementImpl(AbstractSql<?> sql, String join, String open, String close) {
            this.close = close;
            this.join = join;
            this.open = open;
            this.sql = sql;
        }

        @NotNull
        protected abstract String getKeyword();

        protected final boolean isNotEmpty() {
            return !this.values.isEmpty();
        }

        @SuppressWarnings("unchecked")
        public final T addValues(String... values) {
            if (values != null && values.length > 0) {
                addAll(this.values, values);
            }
            return (T) this;
        }

        public final void builder(StringBuilder builder) {
            if (!BaseStatementImpl.this.isNotEmpty()) return;
            builder.append(getKeyword()).append(open);
            String last = "_________________________";
            for (int i = 0; i < values.size(); i++) {
                String part = this.values.get(i);
                if (this.isJoin(i, part, last)) {
                    builder.append(join);
                }
                builder.append(part);
                last = part;
            }
            builder.append(close);
        }

        private boolean isJoin(int index, String part, String last) {
            var b = index > 0 && !OR.equals(part) && !OR.equals(last);
            return b && !AND.equals(part) && !AND.equals(last);
        }
    }


}
