/**
 * Created the sn.mini.java.web.model.ArraysModelData.java
 * @created 2017年10月30日 下午5:35:09
 * @version 1.0.0
 */
package sn.mini.java.web.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * sn.mini.java.web.model.ArraysModelData.java
 * @author XChao
 */
public class ArraysModelData extends AbstrctModelData<List<Object>> {

	protected ArraysModelData() {
		this.setDate(new ArrayList<>());
	}

	@Override
	public void put(String key, Object value) {
		throw new RuntimeException("Array cannot use Object method. ");
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> data) {
		throw new RuntimeException("Array cannot use Object method. ");
	}

	@Override
	public Object getValue(String key) {
		throw new RuntimeException("Array cannot use Object method. ");
	}

	@Override
	public Set<? extends String> keySet() {
		throw new RuntimeException("Array cannot use Object method. ");
	}

	@Override
	public void add(Object value) {
		this.getData().add(value);
	}

	@Override
	public void addAll(List<? extends Object> data) {
		this.getData().addAll(data);
	}

	@Override
	public Object getValue(int index) {
		return this.getData().get(index);
	}

	@Override
	public List<? extends Object> valSet() {
		return this.getData();
	}

}
