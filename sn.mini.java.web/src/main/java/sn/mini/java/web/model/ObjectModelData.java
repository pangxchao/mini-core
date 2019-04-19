package sn.mini.java.web.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * sn.mini.java.web.model.ObjectModelData.java
 *
 * @author XChao
 */
public class ObjectModelData extends AbstrctModelData<Map<String, Object>> {

    protected ObjectModelData() {
        this.setDate(new HashMap<>());
    }

    @Override
    public void put(String key, Object value) {
        this.getData().put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ?> data) {
        this.getData().putAll(data);
    }

    @Override
    public Object getValue(String key) {
        return this.getData().get(key);
    }

    @Override
    public Set<? extends String> keySet() {
        return this.getData().keySet();
    }

    @Override
    public void add(Object value) {
        throw new RuntimeException("Object cannot use Array method. ");
    }

    @Override
    public void addAll(List<?> data) {
        throw new RuntimeException("Object cannot use Array method. ");
    }

    @Override
    public Object getValue(int index) {
        throw new RuntimeException("Object cannot use Array method. ");
    }

    @Override
    public List<?> valSet() {
        throw new RuntimeException("Object cannot use Array method. ");
    }

}
