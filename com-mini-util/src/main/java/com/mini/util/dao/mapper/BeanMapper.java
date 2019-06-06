package com.mini.util.dao.mapper;

import com.mini.util.dao.IRow;
import com.mini.util.dao.annotation.DBMapping;
import com.mini.util.lang.ClassUtil;
import com.mini.util.lang.StringUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class BeanMapper<T> extends AbstractMapper<T> {
    private static final Map<Class<?>, BeanMapper<?>> MAPPER_MAP = new ConcurrentHashMap<>();
    private final Map<String, Field> fields = new ConcurrentHashMap<>();
    private final Class<T> clazz;

    public static <T> BeanMapper getInstance(Class<T> clazz) {
        return MAPPER_MAP.computeIfAbsent(clazz, BeanMapper::new);
    }

    private BeanMapper(Class<T> clazz) {
        for (Field field : ClassUtil.getAllField(this.clazz = clazz)) {
            DBMapping m = field.getAnnotation(DBMapping.class);
            String mName = m == null ? field.getName() : m.value();
            String name = StringUtil.def(mName, field.getName());
            fields.putIfAbsent(StringUtil.toDBName(name), field);
        }
    }

    @Override
    public T execute(ResultSet rs, int number) throws SQLException {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            if (constructor == null) return null;

            ResultSetMetaData metaData = rs.getMetaData();
            T instance = constructor.newInstance();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                // 验证该字段在对应实体中是否存在
                String key = metaData.getColumnLabel(i);
                Field field = fields.get(key);
                if (field == null) continue;

                // 验证数据类型是否匹配常规数据类型
                Class<?> clazz = field.getType();
                IRow<?> row = getRow(clazz);
                if (row == null) continue;

                // 调用设置方法设置值
                Object v = row.execute(rs, i);
                field.setAccessible(true);
                field.set(instance, v);
            }
            return instance;
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new SQLException(e);
        }
    }
}
