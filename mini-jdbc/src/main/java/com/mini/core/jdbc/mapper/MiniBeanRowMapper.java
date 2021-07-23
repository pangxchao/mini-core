package com.mini.core.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.util.DirectFieldAccessFallbackBeanWrapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.springframework.core.convert.support.DefaultConversionService.getSharedInstance;
import static org.springframework.util.ReflectionUtils.findField;

public class MiniBeanRowMapper<T> implements RowMapper<T> {
    protected static final Logger log = LoggerFactory.getLogger(MiniBeanRowMapper.class);
    private final Map<String, PropertyDescriptor> mappedColumns = new HashMap<>();
    private final Map<String, PropertyDescriptor> mappedFields = new HashMap<>();
    private ConversionService conversionService = getSharedInstance();
    private final Class<T> mappedClass;

    /**
     * 默认构造器
     *
     * @param mappedClass Mapper 类型
     */
    public MiniBeanRowMapper(@Nonnull Class<T> mappedClass) {
        this.mappedClass = mappedClass;

        // 读取属性信息
        for (PropertyDescriptor pd : BeanUtils.getPropertyDescriptors(mappedClass)) {
            if (pd.getWriteMethod() == null) continue;

            // 添加属性映射到Map对象中
            this.mappedFields.put(lowerCaseName(pd.getName()), pd);
            String underscoredName = underscoreName(pd.getName());
            if (!lowerCaseName(pd.getName()).equals(underscoredName)) {
                this.mappedFields.put(underscoredName, pd);
            }

            // 读取字段信息
            Field field = findField(mappedClass, pd.getName());
            if (field == null) continue;

            // 读取注解信息
            Column column = field.getAnnotation(Column.class);
            if (column == null || column.value().isBlank()) {
                continue;
            }
            // 注解字段名称不为空，添加到映射关系
            mappedColumns.put(column.value(), pd);
        }
    }

    /**
     * 获取Mapper Class
     *
     * @return Mapper Class
     */
    @Nonnull
    public final Class<T> getMappedClass() {
        return this.mappedClass;
    }

    /**
     * 设置数据转换器
     *
     * @param conversionService 数据转换器
     */
    public void setConversionService(@Nonnull ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * 获取数据转换器
     *
     * @return 数据转换器
     */
    @Nonnull
    public ConversionService getConversionService() {
        return this.conversionService;
    }

    // 初始化BeanWrapper对象
    protected void initBeanWrapper(BeanWrapper bw) {
        ConversionService cs = getConversionService();
        bw.setConversionService(cs);
    }

    // 字段格式转换
    protected String underscoreName(String name) {
        if (!StringUtils.hasLength(name)) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(lowerCaseName(name.substring(0, 1)));
        for (int i = 1; i < name.length(); i++) {
            String s = name.substring(i, i + 1);
            String slc = lowerCaseName(s);
            if (!s.equals(slc)) {
                builder.append("_").append(slc);
            } else {
                builder.append(s);
            }
        }
        return builder.toString();
    }

    // 字段字母大小写转换
    protected String lowerCaseName(String name) {
        return name.toLowerCase(Locale.US);
    }

    @Override
    public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
        final T mappedObject = BeanUtils.instantiateClass(this.mappedClass);
        var beanWrapper = new DirectFieldAccessFallbackBeanWrapper(mappedObject);
        MiniBeanRowMapper.this.initBeanWrapper(beanWrapper);

        // 读取ResultSetMetaData信息并获取查询结果
        final ResultSetMetaData rsMetaData = rs.getMetaData();
        for (int index = 1; index <= rsMetaData.getColumnCount(); index++) {
            String column = JdbcUtils.lookupColumnName(rsMetaData, index);
            String field = lowerCaseName(StringUtils.delete(column, " "));

            var pd = mappedColumns.getOrDefault(field, mappedFields.get(field));
            if (pd == null) continue;
            try {
                final Object value = getColumnValue(rs, index, pd);
                try {
                    beanWrapper.setPropertyValue(pd.getName(), value);
                } catch (TypeMismatchException ex) {
                    log.debug("Intercepted TypeMismatchException for row " + rowNumber + " and column '"
                            + column + "' with null value when setting property '" + pd.getName() +
                            "' of type '" + ClassUtils.getQualifiedName(pd.getPropertyType()) +
                            "' on object: " + mappedObject, ex);
                }
            } catch (NotWritablePropertyException ex) {
                log.warn("Unable to map column '" + column + "' to property '" + pd.getName() + "'", ex);
            }
        }
        return mappedObject;
    }

    /**
     * 获取结果集
     *
     * @param rs    ResultSet 对象
     * @param index 字段索引
     * @param pd    PropertyDescriptor 对象
     * @return 结果集字段值
     */
    @Nullable
    protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
        return JdbcUtils.getResultSetValue(rs, index, pd.getPropertyType());
    }

    /**
     * 创建 Row Mapper 对象
     *
     * @param mapperClass Row Mapper Class
     * @return Row Mapper 对象
     */
    public static <T> RowMapper<T> create(@Nonnull Class<T> mapperClass) {
        return new MiniBeanRowMapper<>(mapperClass);
    }
}
