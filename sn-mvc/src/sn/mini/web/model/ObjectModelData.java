/**
 * Created the sn.mini.web.model.ObjectModelData.java
 * @created 2017年10月30日 下午5:28:55
 * @version 1.0.0
 */
package sn.mini.web.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * sn.mini.web.model.ObjectModelData.java
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
	public void putAll(Map<? extends String, ? extends Object> data) {
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
	public void addAll(List<? extends Object> data) {
		throw new RuntimeException("Object cannot use Array method. ");
	}

	@Override
	public Object getValue(int index) {
		throw new RuntimeException("Object cannot use Array method. ");
	}

	@Override
	public List<? extends Object> valSet() {
		throw new RuntimeException("Object cannot use Array method. ");
	}

}
