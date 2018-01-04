/**
 * Created the sn.mini.web.model.EntryModelData.java
 * @created 2017年10月30日 下午5:43:33
 * @version 1.0.0
 */
package sn.mini.web.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * sn.mini.web.model.EntryModelData.java
 * @author XChao
 */
public class EntryModelData<T> extends AbstrctModelData<T> {

	protected EntryModelData() {
	}

	@Override
	public void put(String key, Object value) {
		throw new RuntimeException("Entry cannot use Object method. ");

	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> data) {
		throw new RuntimeException("Entry cannot use Object method. ");
	}

	@Override
	public Object getValue(String key) {
		throw new RuntimeException("Entry cannot use Object method. ");
	}

	@Override
	public Set<? extends String> keySet() {
		throw new RuntimeException("Entry cannot use Object method. ");
	}

	@Override
	public void add(Object value) {
		throw new RuntimeException("Entry cannot use Array method. ");
	}

	@Override
	public void addAll(List<? extends Object> data) {
		throw new RuntimeException("Entry cannot use Array method. ");
	}

	@Override
	public Object getValue(int index) {
		throw new RuntimeException("Entry cannot use Array method. ");
	}

	@Override
	public List<? extends Object> valSet() {
		throw new RuntimeException("Entry cannot use Array method. ");
	}

}
