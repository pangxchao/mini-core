package com.mini.core.jdbc.builder.statement;

import com.mini.core.jdbc.builder.AbstractSql;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collection;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Arrays.stream;

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
    T eqNative(String column, String target, Object... args);

    /**
     * “=”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T eq(String column, Object arg);

    /**
     * “<>”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T notEqNative(String column, String target, Object... args);


    /**
     * “<>”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T notEq(String column, Object arg);

    /**
     * 字段必须为空
     *
     * @param column 字段名称
     * @return {@code this}
     */
    T isNull(String column);

    /**
     * 字段必须不为空
     *
     * @param column 字段名称
     * @return {@code this}
     */
    T isNotNull(String column);


    /**
     * “>”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T gtNative(String column, String target, Object... args);

    /**
     * “>”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T gt(String column, Object arg);


    /**
     * “<”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T ltNative(String column, String target, Object... args);

    /**
     * “<”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T lt(String column, Object arg);


    /**
     * “>=”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T gteNative(String column, String target, Object... args);

    /**
     * “>=”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T gte(String column, Object arg);

    /**
     * “<=”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T lteNative(String column, String target, Object... args);

    /**
     * “<=”条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T lte(String column, Object arg);

    /**
     * “IN”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T inNative(String column, String target, Object... args);


    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    <O, C extends Collection<O>> T in(String column, C args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    <O> T in(String column, O[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(String column, long[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(String column, int[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(String column, short[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(String column, byte[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(String column, double[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(String column, float[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(String column, boolean[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T in(String column, char[] args);

    /**
     * “NOT IN”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T notInNative(String column, String target, Object... args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    <O, C extends Collection<O>> T notIn(String column, C args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    <O> T notIn(String column, O[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T notIn(String column, long[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T notIn(String column, int[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T notIn(String column, short[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T notIn(String column, byte[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T notIn(String column, double[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T notIn(String column, float[] args);

    /**
     * "IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T notIn(String column, boolean[] args);

    /**
     * "NOT IN" 条件
     *
     * @param column 字段名称
     * @param args   参数值
     * @return {@code this}
     */
    T notIn(String column, char[] args);

    /**
     * “LIKE”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T likeNative(String column, String target, Object... args);

    /**
     * "LIKE" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T like(String column, Object arg);

    /**
     * "LIKE %_%" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T contain(String column, Object arg);

    /**
     * "LIKE _%" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T startWith(String column, Object arg);

    /**
     * "LIKE %_" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T endWith(String column, Object arg);

    /**
     * “LIKE”条件
     *
     * @param column 字段名称
     * @param target 目标字符串
     * @param args   目标字符串中的参数值
     * @return {@code this}
     */
    T notLikeNative(String column, String target, Object... args);

    /**
     * "LIKE" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T notLike(String column, Object arg);

    /**
     * "LIKE %_%" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T notContain(String column, Object arg);

    /**
     * "LIKE _%" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T notStartWith(String column, Object arg);

    /**
     * "LIKE %_" 条件
     *
     * @param column 字段名称
     * @param arg    参数值
     * @return {@code this}
     */
    T notEndWith(String column, Object arg);

    /**
     * “MATCH(?)  AGAINST(target)”条件
     *
     * @param columns 字段名称
     * @param target  目标字符串
     * @param args    目标字符串中的参数值
     * @return {@code this}
     */
    T matchNative(String[] columns, String target, Object... args);

    /**
     * "MATCH(?)  AGAINST(?)" 条件
     *
     * @param columns 字段名称
     * @param arg     参数值
     * @return {@code this}
     */
    T match(String[] columns, Object arg);

    /**
     * “MATCH(?)  AGAINST(target)”条件
     *
     * @param columns 字段名称
     * @param target  目标字符串
     * @param args    目标字符串中的参数值
     * @return {@code this}
     */
    T notMatchNative(String[] columns, String target, Object... args);

    /**
     * "MATCH(?)  AGAINST(?)" 条件
     *
     * @param columns 字段名称
     * @param arg     参数值
     * @return {@code this}
     */
    T notMatch(String[] columns, Object arg);

    /**
     * “MATCH(?) AGAINST(target IN BOOLEAN MODE)”条件
     *
     * @param columns 字段名称
     * @param target  目标字符串
     * @param args    目标字符串中的参数值
     * @return {@code this}
     */
    T matchInBoolNative(String[] columns, String target, Object... args);

    /**
     * "MATCH(?)  AGAINST(? IN BOOLEAN MODE)" 条件
     *
     * @param columns 字段名称
     * @param arg     参数值
     * @return {@code this}
     */
    T matchInBool(String[] columns, Object arg);

    /**
     * “MATCH(?) AGAINST(target IN BOOLEAN MODE)”条件
     *
     * @param columns 字段名称
     * @param target  目标字符串
     * @param args    目标字符串中的参数值
     * @return {@code this}
     */
    T notMatchInBoolNative(String[] columns, String target, Object... args);

    /**
     * "MATCH(?)  AGAINST(? IN BOOLEAN MODE)" 条件
     *
     * @param columns 字段名称
     * @param arg     参数值
     * @return {@code this}
     */
    T notMatchInBool(String[] columns, Object arg);

    /**
     * “BETWEEN ? AND ?”条件
     *
     * @param column    字段名称
     * @param targetMin 最小目标字符串
     * @param targetMax 最大目标字符串
     * @param args      目标字符串中的参数值
     * @return {@code this}
     */
    T betweenNative(String column, String targetMin, String targetMax, Object... args);

    /**
     * "BETWEEN ? AND ?" 条件
     *
     * @param column 字段名称
     * @param min    最小参数值
     * @param max    最大参数值
     * @return {@code this}
     */
    T between(String column, Object min, Object max);

    /**
     * “BETWEEN ? AND ?”条件
     *
     * @param column    字段名称
     * @param targetMin 最小目标字符串
     * @param targetMax 最大目标字符串
     * @param args      目标字符串中的参数值
     * @return {@code this}
     */
    T notBetweenNative(String column, String targetMin, String targetMax, Object... args);

    /**
     * "BETWEEN ? AND ?" 条件
     *
     * @param column 字段名称
     * @param min    最小参数值
     * @param max    最大参数值
     * @return {@code this}
     */
    T notBetween(String column, Object min, Object max);


    abstract class FilterStatementImpl<T extends FilterStatement<T>> extends BaseStatementImpl<T> implements FilterStatement<T> {
        protected FilterStatementImpl(AbstractSql<?> sql) {
            super(sql, AND, "(", ")");
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
        public final T eqNative(String column, String target, Object... args) {
            addValues(format("%s = %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T eq(String column, Object arg) {
            return eqNative(column, "?", arg);
        }


        @Override
        public final T notEqNative(String column, String target, Object... args) {
            addValues(format("%s <> %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T notEq(String column, Object arg) {
            return notEqNative(column, "?", arg);
        }

        @Override
        public final T isNull(String column) {
            addValues(column + " IS NULL");
            return self();
        }

        @Override
        public final T isNotNull(String column) {
            addValues(column + " IS NOT NULL");
            return self();
        }

        @Override
        public final T gtNative(String column, String target, Object... args) {
            addValues(format("%s > %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T gt(String column, Object arg) {
            return gtNative(column, "?", arg);
        }

        @Override
        public final T ltNative(String column, String target, Object... args) {
            addValues(format("%s < %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T lt(String column, Object arg) {
            return ltNative(column, "?", arg);
        }

        @Override
        public final T gteNative(String column, String target, Object... args) {
            addValues(format("%s >= %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T gte(String column, Object arg) {
            return gteNative(column, "?", arg);
        }

        @Override
        public final T lteNative(String column, String target, Object... args) {
            addValues(format("%s <= %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T lte(String column, Object arg) {
            return lteNative(column, "?", arg);
        }

        private void $InNative(String column, String target) {
            addValues(format("%s IN (%s)", column, target));
        }

        @Override
        public final T inNative(String column, String target, Object... args) {
            this.$InNative(column, target);
            this.sql.args(args);
            return self();
        }

        @Override
        public final <O, C extends Collection<O>> T in(String column, C args) {
            $InNative(column, join(args.stream(), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final <O> T in(String column, O[] args) {
            $InNative(column, join(stream(args), ", ", "", "", it -> "?"));
            this.sql.args((Object[]) args);
            return self();
        }

        @Override
        public final T in(String column, long[] args) {
            $InNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T in(String column, int[] args) {
            $InNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T in(String column, short[] args) {
            $InNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T in(String column, byte[] args) {
            $InNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T in(String column, double[] args) {
            $InNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T in(String column, float[] args) {
            $InNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T in(String column, boolean[] args) {
            $InNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T in(String column, char[] args) {
            $InNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        private void $NotInNative(String column, String target) {
            addValues(format("%s NOT IN (%s)", column, target));
        }

        @Override
        public final T notInNative(String column, String target, Object... args) {
            addValues(format("%s NOT IN (%s)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final <O, C extends Collection<O>> T notIn(String column, C args) {
            $NotInNative(column, join(args.stream(), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final <O> T notIn(String column, O[] args) {
            $NotInNative(column, join(stream(args), ", ", "", "", it -> "?"));
            this.sql.args((Object[]) args);
            return self();
        }

        @Override
        public final T notIn(String column, long[] args) {
            $NotInNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T notIn(String column, int[] args) {
            $NotInNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T notIn(String column, short[] args) {
            $NotInNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T notIn(String column, byte[] args) {
            $NotInNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T notIn(String column, double[] args) {
            $NotInNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T notIn(String column, float[] args) {
            $NotInNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T notIn(String column, boolean[] args) {
            $NotInNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T notIn(String column, char[] args) {
            $NotInNative(column, join(stream(ArrayUtils.toObject(args)), ", ", "", "", it -> "?"));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T likeNative(String column, String target, Object... args) {
            addValues(format("%s LIKE %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T like(String column, Object arg) {
            return likeNative(column, "?", arg);
        }

        @Override
        public final T contain(String column, Object arg) {
            return likeNative(column, "?", "%" + arg + "%");
        }

        @Override
        public final T startWith(String column, Object arg) {
            return likeNative(column, "?", arg + "%");
        }

        @Override
        public final T endWith(String column, Object arg) {
            return likeNative(column, "?", "%" + arg);
        }

        @Override
        public T notLikeNative(String column, String target, Object... args) {
            addValues(format("%s NOT LIKE %s", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T notLike(String column, Object arg) {
            return notLikeNative(column, "?", arg);
        }

        @Override
        public final T notContain(String column, Object arg) {
            return notLikeNative(column, "?", "%" + arg + "%");
        }

        @Override
        public final T notStartWith(String column, Object arg) {
            return notLikeNative(column, "?", arg + "%");
        }

        @Override
        public final T notEndWith(String column, Object arg) {
            return notLikeNative(column, "?", "%" + arg);
        }

        @Override
        public final T matchNative(String[] columns, String target, Object... args) {
            String column = join(stream(columns), ", ", "", "", it -> it);
            addValues(format("MATCH(%s) AGAINST(%s)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T match(String[] columns, Object arg) {
            return matchNative(columns, "?", arg);
        }

        @Override
        public final T notMatchNative(String[] columns, String target, Object... args) {
            String column = join(stream(columns), ", ", "", "", it -> it);
            addValues(format("NOT MATCH(%s) AGAINST(%s)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T notMatch(String[] columns, Object arg) {
            return notMatchNative(columns, "?", arg);
        }

        @Override
        public final T matchInBoolNative(String[] columns, String target, Object... args) {
            final String column = join(stream(columns), ", ", "", "", it -> it);
            addValues(format("MATCH(%s) AGAINST(%s IN BOOLEAN MODE)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T matchInBool(String[] columns, Object arg) {
            return matchInBoolNative(columns, "?", arg);
        }

        @Override
        public final T notMatchInBoolNative(String[] columns, String target, Object... args) {
            final String column = join(stream(columns), ", ", "", "", it -> it);
            addValues(format("NOT MATCH(%s) AGAINST(%s IN BOOLEAN MODE)", column, target));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T notMatchInBool(String[] columns, Object arg) {
            return notMatchInBoolNative(columns, "?", arg);
        }

        @Override
        public final T betweenNative(String column, String targetMin, String targetMax, Object... args) {
            addValues(format("%s BETWEEN %s AND %s", column, targetMin, targetMax));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T between(String column, Object min, Object max) {
            return betweenNative(column, "?", "?", min, max);
        }

        @Override
        public final T notBetweenNative(String column, String targetMin, String targetMax, Object... args) {
            addValues(format("%s NOT BETWEEN %s AND %s", column, targetMin, targetMax));
            this.sql.args(args);
            return self();
        }

        @Override
        public final T notBetween(String column, Object min, Object max) {
            return notBetweenNative(column, "?", "?", min, max);
        }

        @SuppressWarnings("SameParameterValue")
        private <S> String join(Stream<S> stream, CharSequence delimiter, CharSequence prefix, CharSequence suffix, Function<S, String> function) {
            StringJoiner joiner = new StringJoiner(delimiter, prefix, suffix);
            stream.forEach(it -> joiner.add(function.apply(it)));
            return joiner.toString();
        }
    }
}
