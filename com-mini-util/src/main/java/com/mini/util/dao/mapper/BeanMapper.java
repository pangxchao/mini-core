package com.mini.util.dao.mapper;

import com.mini.util.dao.IRow;

import javax.annotation.Nonnull;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class BeanMapper<T> extends AbstractMapper<T> {
    private static final Map<Class<?>, BeanMapper<?>> MAPPER_MAP = new ConcurrentHashMap<>();
    private final Map<String, PropertyDescriptor> descriptors = new ConcurrentHashMap<>();
    private final Class<T> clazz;

    public static <T> BeanMapper getInstance(Class<T> clazz) {
        return MAPPER_MAP.computeIfAbsent(clazz, BeanMapper::new);
    }

    private BeanMapper(@Nonnull Class<T> clazz) {
        try {
            this.clazz = clazz;
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
                descriptors.put(descriptor.getName(), descriptor);
            }
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T execute(ResultSet rs, int number) throws SQLException {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            if (constructor == null) return null;

            ResultSetMetaData metaData = rs.getMetaData();
            T t = constructor.newInstance();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                // 验证该字段在对应实体中是否存在
                String key = metaData.getColumnLabel(i);
                PropertyDescriptor des = descriptors.get(key);
                if (des == null) continue;

                // SETTER 方法为空时不设置
                Method method = des.getWriteMethod();
                if (method == null) continue;

                // 验证数据类型是否匹配常规数据类型
                Class<?> clazz = des.getPropertyType();
                IRow<?> row = getRow(clazz);
                if (row == null) continue;

                // 调用设置方法设置值
                method.invoke(t, row.execute(rs, i));
            }
            return t;
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
