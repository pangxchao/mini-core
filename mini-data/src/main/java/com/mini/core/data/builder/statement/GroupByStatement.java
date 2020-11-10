package com.mini.core.data.builder.statement;

import org.jetbrains.annotations.NotNull;

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

        @NotNull
        public final String getOpen() {
            return "";
        }

        @NotNull
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
