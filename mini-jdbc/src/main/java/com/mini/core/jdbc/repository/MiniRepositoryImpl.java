package com.mini.core.jdbc.repository;

import com.mini.core.jdbc.DefaultMiniJdbcTemplate;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;

import javax.sql.DataSource;
import java.io.Serializable;

public class MiniRepositoryImpl extends DefaultMiniJdbcTemplate implements MiniRepository {
    private final JdbcAggregateOperations entityOperations;

    public MiniRepositoryImpl(DataSource dataSource, JdbcAggregateOperations entityOperations) {
        super(dataSource, true);
        this.entityOperations = entityOperations;
    }

    @Override
    public <E extends Serializable> E insert(E entity) {
        return entityOperations.insert(entity);
    }

    @Override
    public <E extends Serializable> E update(E entity) {
        return entityOperations.update(entity);
    }
}
