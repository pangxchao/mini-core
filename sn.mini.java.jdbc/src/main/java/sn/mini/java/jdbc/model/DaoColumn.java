/**
 * Created the sn.mini.dao.model.DaoField.java
 *
 * @created 2017年11月2日 下午4:23:52
 * @version 1.0.0
 */
package sn.mini.java.jdbc.model;

import sn.mini.java.jdbc.annotaion.Column;
import sn.mini.java.util.lang.StringUtil;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * sn.mini.dao.model.DaoField.java
 *
 * @author XChao
 */
public final class DaoColumn {
    private final Optional<PropertyDescriptor> descriptor;
    private final Optional<Column> column;

    public DaoColumn(PropertyDescriptor des, Column col) {
        this.descriptor = Optional.ofNullable(des);
        this.column = Optional.ofNullable(col);
    }

    public String getName() {
        return descriptor.map(v -> v.getName()).orElseThrow(() ->
            new RuntimeException("PropertyDescriptor cannot be null. "));
    }

    public boolean isColumn() {
        return this.column.isPresent();
    }

    public boolean isPrimary() {
        return column.map(v -> v.des()).map(v -> ((v & 2) == 2)).orElse(false);
    }

    public boolean isForeign() {
        return column.map(v -> v.des()).map(v -> ((v & 4) == 4)).orElse(false);
    }

    public String getDbName() {
        return column.map(v -> v.value()).orElse(StringUtil.toDBName(getName()));
    }

    public Method getReadMethod() {
        return descriptor.map(v -> v.getReadMethod()).orElse(null);
    }

    public Method getWriteMethod() {
        return descriptor.map(v -> v.getWriteMethod()).orElse(null);
    }

    public Class<?> getType() {
        return descriptor.map(v -> v.getPropertyType()).orElseThrow(() -> //
                new RuntimeException("PropertyDescriptor cannot be null. "));
    }

    public Object getValue(Object instance) throws Exception {
        return descriptor.map(v -> v.getReadMethod()).map(v -> {
            try {
                return v.invoke(instance);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }).orElse(null);
    }

    public void setValue(Object instance, Object value) throws Exception {
        descriptor.map(v -> v.getWriteMethod()).map(v -> {
            try {
                return v.invoke(instance, value);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });
    }
}
