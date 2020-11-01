package com.mini.core.jdbc.builder.statement;

import javax.annotation.Nonnull;

@SuppressWarnings("UnusedReturnValue")
public interface GroupByStatement {
    /**
     * 分组
     *
     * @param column 分组字段
     * @return {@code this}
     */
    GroupByStatement GROUP_BY(String... column);

    final class GroupByStatementImpl extends BaseStatement implements GroupByStatement {
        public GroupByStatementImpl() {
            super("\nGROUP BY ", ", ");
        }

        @Nonnull
        public final String getOpen() {
            return "";
        }

        @Nonnull
        public final String getClose() {
            return " ";
        }

        @Override
        public final GroupByStatement GROUP_BY(String... column) {
            this.addValues(column);
            return this;
        }
    }
}
