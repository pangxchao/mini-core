package com.mini.core.jdbc;

import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.model.Page;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.of;

public abstract class JdbcAccessor extends org.springframework.jdbc.core.JdbcTemplate {
    public JdbcAccessor() {
    }

    public JdbcAccessor(DataSource dataSource) {
        super(dataSource);
    }

    public JdbcAccessor(DataSource dataSource, boolean lazyInit) {
        super(dataSource, lazyInit);
    }


    protected abstract String paging(int start, int limit, String sql);

    protected abstract String totals(String sql);

    protected final int executeUpdate(@NotNull String sql, Object... params) {
        return super.update(sql, params);
    }

    protected final int executeUpdate(@NotNull SQLBuilder builder) {
        return super.update(builder.getSql(), builder.getArgs());
    }

    @NotNull
    @Override
    public final int[] batchUpdate(@NotNull String... sql) {
        return super.batchUpdate(sql);
    }

    protected final int[] batchUpdate(@NotNull String sql, Object[]... paramsArray) {
        return super.batchUpdate(sql, Arrays.asList(paramsArray));
    }

    @NotNull
    protected final <T> List<T> executeQueryList(@NotNull String sql, RowMapper<T> mapper, Object... params) {
        return super.query(sql, mapper, params);
    }

    @NotNull
    protected final <T> List<T> executeQueryList(@NotNull SQLBuilder builder, RowMapper<T> mapper) {
        return executeQueryList(builder.getSql(), mapper, builder.getArgs());
    }

    @NotNull
    protected final <T> List<T> executeQueryList(int start, int limit, @NotNull String sql, @NotNull RowMapper<T> mapper, Object... params) {
        return executeQueryList(paging(start, limit, sql), mapper, params);
    }

    @NotNull
    protected final <T> List<T> executeQueryList(int start, int limit, @NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper) {
        return executeQueryList(start, limit, builder.getSql(), mapper, builder.getArgs());
    }

    @Nullable
    protected final <T> T executeQueryObject(String sql, RowMapper<T> mapper, Object... params) {
        return of(query(sql, mapper, params)).filter(it -> !it.isEmpty()).map(it -> it.get(0)).orElse(null);
    }

    @Nullable
    protected final <T> T executeQueryObject(@NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper) {
        return executeQueryObject(builder.getSql(), mapper, builder.getArgs());
    }

    @NotNull
    @Override
    protected final <T> RowMapper<T> getSingleColumnRowMapper(@NotNull Class<T> requiredType) {
        return super.getSingleColumnRowMapper(requiredType);
    }

    @NotNull
    protected final <T> RowMapper<T> getBeanPropertyRowMapper(@NotNull Class<T> requiredType) {
        return new BeanPropertyRowMapper<>(requiredType);
    }

    @NotNull
    @Override
    protected final RowMapper<Map<String, Object>> getColumnMapRowMapper() {
        return super.getColumnMapRowMapper();
    }


    @NotNull
    protected final <T> Page<T> executeQueryPage(int page, int limit, @NotNull String sql, @NotNull RowMapper<T> mapper, Object... params) {
        final com.mini.core.jdbc.model.Page<T> result = new com.mini.core.jdbc.model.Page<>(page, limit);
        result.setRows(executeQueryList(paging(result.getStart(), result.getLimit(), sql), mapper, params));
        Integer total = executeQueryObject(totals(sql), getSingleColumnRowMapper(Integer.class), params);
        result.setTotal(Optional.ofNullable(total).orElse(0));
        return result;
    }

    @NotNull
    protected final <T> Page<T> executeQueryPage(int page, int limit, @NotNull SQLBuilder builder, @NotNull RowMapper<T> mapper) {
        return executeQueryPage(page, limit, builder.getSql(), mapper, builder.getArgs());
    }
}
