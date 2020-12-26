package com.mini.core.jdbc.builder.statement;

import com.mini.core.jdbc.builder.AbstractSql;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnusedReturnValue")
public interface JoinOnStatement extends FilterStatement<JoinOnStatement> {
    /**
     * 添加“AND”连接符
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @return {@code this}
     */
    JoinOnStatement and(@NotNull String column, @NotNull String target);

    /**
     * 添加“OR”连接符
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @return {@code this}
     */
    JoinOnStatement or(@NotNull String column, @NotNull String target);


    final class JoinOnStatementImpl extends FilterStatementImpl<JoinOnStatement> implements JoinOnStatement {
        public JoinOnStatementImpl(AbstractSql<?> sql) {
            super(sql);
        }

        @NotNull
        @Override
        protected final String getKeyword() {
            return " ON ";
        }

        @Override
        protected JoinOnStatement self() {
            return this;
        }

        @Override
        public JoinOnStatement and(@NotNull String column, @NotNull String target) {
            return and().eqNative(column, target);
        }

        @Override
        public JoinOnStatement or(@NotNull String column, @NotNull String target) {
            return or().eqNative(column, target);
        }
    }
}
