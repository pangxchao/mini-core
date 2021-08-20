package com.mini.core.jdbc.mybatis;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.mini.core.util.PKGenerator;

public class MiniMybatisIdGenerator implements IdentifierGenerator {
    @Override
    public Number nextId(Object entity) {
        return PKGenerator.id();
    }

    @Override
    public String nextUUID(Object entity) {
        return PKGenerator.uuid();
    }
}
