/**
 * Created the sn.mini.java.web.model.IModelData.java
 * @created 2017年10月30日 下午5:24:50
 * @version 1.0.0
 */
package sn.mini.java.web.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * sn.mini.java.web.model.IModelData.java
 * @author XChao
 */
public interface IModelData<T> {
	public int getError();

	public void setError(int error);

	public String getMessage();

	public void setMessage(String message);

	public T getData();

	public void setDate(T data);

	public void put(String key, Object value);

	public void putAll(Map<? extends String, ? extends Object> data);

	public Object getValue(String key);

	public Set<? extends String> keySet();

	public void add(Object value);

	public void addAll(List<? extends Object> data);

	public Object getValue(int index);

	public List<? extends Object> valSet();
}
