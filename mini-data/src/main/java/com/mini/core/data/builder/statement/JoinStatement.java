package com.mini.core.data.builder.statement;


import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnusedReturnValue")
public interface JoinStatement {

    /**
     * Join
     *
     * @param join 连接字符串
     * @return {@code this}
     */
    JoinStatement JOIN(String join);

    /**
     * Inner Join
     *
     * @param join 连接字符串
     * @return {@code this}
     */
    JoinStatement INNER_JOIN(String join);

    /**
     * Left Join
     *
     * @param join 连接字符串
     * @return {@code this}
     */
    JoinStatement LEFT_JOIN(String join);

    /**
     * Right Join
     *
     * @param join 连接字符串
     * @return {@code this}
     */
    JoinStatement RIGHT_JOIN(String join);

    /**
     * Left Outer Join
     *
     * @param join 连接字符串
     * @return {@code this}
     */
    JoinStatement LEFT_OUTER_JOIN(String join);

    /**
     * Right Outer Join
     *
     * @param join 连接字符串
     * @return {@code this}
     */
    JoinStatement RIGHT_OUTER_JOIN(String join);

    /**
     * Cross Join
     *
     * @param join 连接字符串
     * @return {@code this}
     */
    JoinStatement CROSS_JOIN(String join);

    final class JoinStatementImpl extends BaseStatement implements JoinStatement {
        private String word = "\nJOIN ";

        public JoinStatementImpl() {
            super("", "");
        }

        @NotNull
        protected final String getOpen() {
            return word;
        }

        @NotNull
        protected final String getClose() {
            return "";
        }

        @Override
        public final JoinStatement JOIN(String join) {
            this.word = "\nJOIN ";
            this.addValues(join);
            return this;
        }

        @Override
        public final JoinStatement INNER_JOIN(String join) {
            this.word = "\nINNER JOIN ";
            this.addValues(join);
            return this;
        }

        @Override
        public final JoinStatement LEFT_JOIN(String join) {
            this.word = "\nLEFT JOIN ";
            this.addValues(join);
            return this;
        }

        @Override
        public final JoinStatement RIGHT_JOIN(String join) {
            this.word = "\nRIGHT JOIN ";
            this.addValues(join);
            return this;
        }

        @Override
        public final JoinStatement LEFT_OUTER_JOIN(String join) {
            this.word = "\nLEFT OUTER JOIN ";
            this.addValues(join);
            return this;
        }

        @Override
        public final JoinStatement RIGHT_OUTER_JOIN(String join) {
            this.word = "\nRIGHT OUTER JOIN ";
            this.addValues(join);
            return this;
        }

        @Override
        public final JoinStatement CROSS_JOIN(String join) {
            this.word = "\nCROSS JOIN ";
            this.addValues(join);
            return this;
        }
    }
}
