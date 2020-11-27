package com.mini.core.data.builder.statement;

import com.mini.core.data.builder.AbstractSql;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static com.mini.core.util.StringKt.join;
import static java.lang.String.format;

@SuppressWarnings("UnusedReturnValue")
public interface FilterStatement<T extends FilterStatement<T>> extends BaseStatement<T> {

    /**
     * 添加“AND”连接符
     *
     * @return {@code this}
     */
    T and();

    /**
     * 添加“OR”连接符
     *
     * @return {@code this}
     */
    T or();

    /**
     * “=”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T eqNative(@NotNull String column, @NotNull String target, Object... args);

    /**
     * “=”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T eq(@NotNull String column, @NotNull Object arg);

    /**
     * “<>”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T neqNative(@NotNull String column, @NotNull String target, Object... args);


    /**
     * “<>”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T neq(@NotNull String column, @NotNull Object arg);


    /**
     * “>”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T gtNative(@NotNull String column, @NotNull String target, Object... args);

    /**
     * “>”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T gt(@NotNull String column, @NotNull Object arg);


    /**
     * “<”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T ltNative(@NotNull String column, @NotNull String target, Object... args);

    /**
     * “<”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T lt(@NotNull String column, @NotNull Object arg);


    /**
     * “>=”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T gteNative(@NotNull String column, @NotNull String target, Object... args);

    /**
     * “>=”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T gte(@NotNull String column, @NotNull Object arg);

    /**
     * “<=”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T lteNative(@NotNull String column, @NotNull String target, Object... args);

    /**
     * “<=”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T lte(@NotNull String column, @NotNull Object arg);

    /**
     * “<=”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T inNative(@NotNull String column, @NotNull String target, Object... args);


    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    <O, C extends Collection<O>> T in(@NotNull String column, @NotNull C args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    <O> T in(@NotNull String column, @NotNull O[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(@NotNull String column, @NotNull long[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(@NotNull String column, @NotNull int[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(@NotNull String column, @NotNull short[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(@NotNull String column, @NotNull byte[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(@NotNull String column, @NotNull double[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(@NotNull String column, @NotNull float[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(@NotNull String column, @NotNull boolean[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(@NotNull String column, @NotNull char[] args);

    /**
     * “LIKE”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T likeNative(@NotNull String column, @NotNull String target, Object... args);

    /**
     * "LIKE" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T like(@NotNull String column, @NotNull Object arg);

    /**
     * "LIKE %_%" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T contain(@NotNull String column, @NotNull Object arg);

    /**
     * "LIKE _%" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T startWith(@NotNull String column, @NotNull Object arg);

    /**
     * "LIKE %_" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T endWith(@NotNull String column, @NotNull Object arg);


    /**
     * “MATCH(?)  AGAINST(target)”条件
     *
     * @param columns 字段名称
     * @param target  目标字符串
     * @param args    目标字符串中的参数值
     * @return {@code this}
     */
    T matchNative(@NotNull String[] columns, @NotNull String target, Object... args);

    /**
     * "MATCH(?)  AGAINST(?)" 条件
     *
     * @param columns 字段名称
     * @param arg     参数值
     * @return {@code this}
     */
    T match(@NotNull String[] columns, @NotNull Object arg);

    /**
     * “MATCH(?) AGAINST(target IN BOOLEAN MODE)”条件
     *
     * @param columns 字段名称
     * @param target  目标字符串
     * @param args    目标字符串中的参数值
     * @return {@code this}
     */
    T matchInBoolNative(@NotNull String[] columns, @NotNull String target, Object... args);

    /**
     * "MATCH(?)  AGAINST(? IN BOOLEAN MODE)" 条件
     *
     * @param columns 字段名称
     * @param arg     参数值
     * @return {@code this}
     */
    T matchInBool(@NotNull String[] columns, @NotNull Object arg);

    /**
     * “BETWEEN ? AND ?”条件
     *
     * @param column    字段名称
     * @param targetMin 最小目标字符串
     * @param targetMax 最大目标字符串
     * @param args      目标字符串中的参数值
     * @return {@code this}
     */
    T betweenNative(@NotNull String column, @NotNull String targetMin, @NotNull String targetMax, Object... args);

    /**
     * "BETWEEN ? AND ?" 条件
     *
     * @param column 字段名称
     * @param min    最小参数值
     * @param max    最大参数值
     * @return {@code this}
     */
    T between(@NotNull String column, @NotNull Object min, @NotNull Object max);


    abstract class FilterStatementImpl<T extends FilterStatement<T>> extends BaseStatementImpl<T> implements FilterStatement<T> {
        protected FilterStatementImpl(AbstractSql<?> sql) {
            super(sql, AND, "(", ") ");
        }

        protected abstract T self();

        @Override
        public final T and() {
            if (this.isNotEmpty()) {
                addValues(AND);
            }
            return self();
        }

        @Override
        public final T or() {
            if (this.isNotEmpty()) {
                this.addValues(OR);
            }
            return self();
        }

        @Override
        public final T eqNative(@NotNull String column, @NotNull String target, Object... args) {
            addValues(format("%s = %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T eq(@NotNull String column, @NotNull Object arg) {
            return eqNative(column, "?", arg);
        }


        @Override
        public final T neqNative(@NotNull String column, @NotNull String target, Object... args) {
            addValues(format("%s <> %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T neq(@NotNull String column, @NotNull Object arg) {
            return neqNative(column, "?", arg);
        }

        @Override
        public final T gtNative(@NotNull String column, @NotNull String target, Object... args) {
            addValues(format("%s > %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T gt(@NotNull String column, @NotNull Object arg) {
            return gtNative(column, "?", arg);
        }

        @Override
        public final T ltNative(@NotNull String column, @NotNull String target, Object... args) {
            addValues(format("%s < %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T lt(@NotNull String column, @NotNull Object arg) {
            return ltNative(column, "?", arg);
        }

        @Override
        public final T gteNative(@NotNull String column, @NotNull String target, Object... args) {
            addValues(format("%s >= %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T gte(@NotNull String column, @NotNull Object arg) {
            return ltNative(column, "?", arg);
        }

        @Override
        public final T lteNative(@NotNull String column, @NotNull String target, Object... args) {
            addValues(format("%s <= %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T lte(@NotNull String column, @NotNull Object arg) {
            return ltNative(column, "?", arg);
        }

        @Override
        public final T inNative(@NotNull String column, @NotNull String target, Object... args) {
            addValues(format("%s IN (%s)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final <O, C extends Collection<O>> T in(@NotNull String column, @NotNull C args) {
            String target = join(args, ", ", "", "", -1, "", it -> "?");
            addValues(format("%s IN (%s)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final <O> T in(@NotNull String column, @NotNull O[] args) {
            String target = join(args, ", ", "", "", -1, "", it -> "?");
            addValues(format("%s IN (%s)", column, target));
            this.sql.args((Object[]) args);
            return self();
        }

        @Override
        public final T in(@NotNull String column, @NotNull long[] args) {
            String target = join(args, ", ", "", "", -1, "", it -> "?");
            addValues(format("%s IN (%s)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T in(@NotNull String column, @NotNull int[] args) {
            String target = join(args, ", ", "", "", -1, "", it -> "?");
            addValues(format("%s IN (%s)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T in(@NotNull String column, @NotNull short[] args) {
            String target = join(args, ", ", "", "", -1, "", it -> "?");
            addValues(format("%s IN (%s)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T in(@NotNull String column, @NotNull byte[] args) {
            String target = join(args, ", ", "", "", -1, "", it -> "?");
            addValues(format("%s IN (%s)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T in(@NotNull String column, @NotNull double[] args) {
            String target = join(args, ", ", "", "", -1, "", it -> "?");
            addValues(format("%s IN (%s)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T in(@NotNull String column, @NotNull float[] args) {
            String target = join(args, ", ", "", "", -1, "", it -> "?");
            addValues(format("%s IN (%s)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T in(@NotNull String column, @NotNull boolean[] args) {
            String target = join(args, ", ", "", "", -1, "", it -> "?");
            addValues(format("%s IN (%s)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T in(@NotNull String column, @NotNull char[] args) {
            String target = join(args, ", ", "", "", -1, "", it -> "?");
            addValues(format("%s IN (%s)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T likeNative(@NotNull String column, @NotNull String target, Object... args) {
            addValues(format("%s LIKE %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T like(@NotNull String column, @NotNull Object arg) {
            return likeNative(column, "?", arg);
        }

        @Override
        public final T contain(@NotNull String column, @NotNull Object arg) {
            return likeNative(column, "?", "%" + arg + "%");
        }

        @Override
        public final T startWith(@NotNull String column, @NotNull Object arg) {
            return likeNative(column, "?", arg + "%");
        }

        @Override
        public final T endWith(@NotNull String column, @NotNull Object arg) {
            return likeNative(column, "?", "%" + arg);
        }

        @Override
        public final T matchNative(@NotNull String[] columns, @NotNull String target, Object... args) {
            String column = join(columns, ", ", "", "", -1, "", it -> "?");
            addValues(format("MATCH(%s) AGAINST(%s)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T match(@NotNull String[] columns, @NotNull Object arg) {
            return matchNative(columns, "?", arg);
        }

        @Override
        public final T matchInBoolNative(@NotNull String[] columns, @NotNull String target, Object... args) {
            final String column = join(columns, ", ", "", "", -1, "", it -> "?");
            addValues(format("MATCH(%s) AGAINST(%s IN BOOLEAN MODE)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T matchInBool(@NotNull String[] columns, @NotNull Object arg) {
            return matchInBoolNative(columns, "?", arg);
        }

        @Override
        public final T betweenNative(@NotNull String column, @NotNull String targetMin, @NotNull String targetMax, Object... args) {
            addValues(format("%s BETWEEN %s AND %s", column, targetMin, targetMax));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T between(@NotNull String column, @NotNull Object min, @NotNull Object max) {
            return betweenNative(column, "?", "?", min, max);
        }
    }
}
