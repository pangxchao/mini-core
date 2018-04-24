/**
 * Created the sn.mini.java.util.json.JSONArray.java
 * @created 2017年9月1日 下午12:22:32
 * @version 1.0.0
 */
package sn.mini.java.util.json;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * sn.mini.java.util.json.JSONArray.java
 * @author XChao
 */
public class JSONArray implements List<Object>, java.io.Serializable {
	private static final long serialVersionUID = -8803852407417812581L;
	
	private final com.alibaba.fastjson.JSONArray array;

	JSONArray(com.alibaba.fastjson.JSONArray array) {
		this.array = array;
	}

	public JSONArray() {
		this.array = new com.alibaba.fastjson.JSONArray();
	}

	public void add(int index, Object element) {
		array.add(index, element);
	}

	public boolean add(Object e) {
		return array.add(e);
	}

	public boolean addAll(Collection<? extends Object> c) {
		return array.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends Object> c) {
		return array.addAll(index, c);
	}

	public void clear() {
		array.clear();
	}

	public Object clone() {
		return array.clone();
	}

	public boolean contains(Object o) {
		return array.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return array.containsAll(c);
	}

	public boolean equals(Object obj) {
		return array.equals(obj);
	}

	public JSONArray fluentAdd(int index, Object element) {
		return new JSONArray(array.fluentAdd(index, element));
	}

	public JSONArray fluentAdd(Object e) {
		array.fluentAdd(e);
		return this;
	}

	public JSONArray fluentAddAll(Collection<? extends Object> c) {
		array.fluentAddAll(c);
		return this;
	}

	public JSONArray fluentAddAll(int index, Collection<? extends Object> c) {
		array.fluentAddAll(index, c);
		return this;
	}

	public JSONArray fluentClear() {
		array.fluentClear();
		return this;
	}

	public JSONArray fluentRemove(int index) {
		array.fluentRemove(index);
		return this;
	}

	public JSONArray fluentRemove(Object o) {
		array.fluentRemove(o);
		return this;
	}

	public JSONArray fluentRemoveAll(Collection<?> c) {
		array.fluentRemoveAll(c);
		return this;
	}

	public JSONArray fluentRetainAll(Collection<?> c) {
		array.fluentRetainAll(c);
		return this;
	}

	public JSONArray fluentSet(int index, Object element) {
		array.fluentSet(index, element);
		return this;
	}

	public Object get(int index) {
		array.get(index);
		return this;
	}

	public BigDecimal getBigDecimal(int index) {
		return array.getBigDecimal(index);
	}

	public BigInteger getBigInteger(int index) {
		return array.getBigInteger(index);
	}

	public Boolean getBoolean(int index) {
		return array.getBoolean(index);
	}

	public boolean getBooleanValue(int index) {
		return array.getBooleanValue(index);
	}

	public Byte getByte(int index) {
		return array.getByte(index);
	}

	public byte getByteValue(int index) {
		return array.getByteValue(index);
	}

	public Type getComponentType() {
		return array.getComponentType();
	}

	public Date getDate(int index) {
		return array.getDate(index);
	}

	public Double getDouble(int index) {
		return array.getDouble(index);
	}

	public double getDoubleValue(int index) {
		return array.getDoubleValue(index);
	}

	public Float getFloat(int index) {
		return array.getFloat(index);
	}

	public float getFloatValue(int index) {
		return array.getFloatValue(index);
	}

	public int getIntValue(int index) {
		return array.getIntValue(index);
	}

	public Integer getInteger(int index) {
		return array.getInteger(index);
	}

	public JSONArray getJSONArray(int index) {
		return new JSONArray(array.getJSONArray(index));
	}

	public JSONObject getJSONObject(int index) {
		return new JSONObject(array.getJSONObject(index));
	}

	public Long getLong(int index) {
		return array.getLong(index);
	}

	public long getLongValue(int index) {
		return array.getLongValue(index);
	}

	public <T> T getObject(int index, Class<T> clazz) {
		return array.getObject(index, clazz);
	}

	public Object getRelatedArray() {
		return array.getRelatedArray();
	}

	public Short getShort(int index) {
		return array.getShort(index);
	}

	public short getShortValue(int index) {
		return array.getShortValue(index);
	}

	public java.sql.Date getSqlDate(int index) {
		return array.getSqlDate(index);
	}

	public String getString(int index) {
		return array.getString(index);
	}

	public Timestamp getTimestamp(int index) {
		return array.getTimestamp(index);
	}

	public int hashCode() {
		return array.hashCode();
	}

	public int indexOf(Object o) {
		return array.indexOf(o);
	}

	public boolean isEmpty() {
		return array.isEmpty();
	}

	public Iterator<Object> iterator() {
		return array.iterator();
	}

	public int lastIndexOf(Object o) {
		return array.lastIndexOf(o);
	}

	public ListIterator<Object> listIterator() {
		return array.listIterator();
	}

	public ListIterator<Object> listIterator(int index) {
		return array.listIterator(index);
	}

	public Object remove(int index) {
		return array.remove(index);
	}

	public boolean remove(Object o) {
		return array.remove(o);
	}

	public boolean removeAll(Collection<?> c) {
		return array.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return array.retainAll(c);
	}

	public Object set(int arg0, Object arg1) {
		return array.set(arg0, arg1);
	}

	public void setComponentType(Type componentType) {
		array.setComponentType(componentType);
	}

	public void setRelatedArray(Object relatedArray) {
		array.setRelatedArray(relatedArray);
	}

	public int size() {
		return array.size();
	}

	public List<Object> subList(int fromIndex, int toIndex) {
		return array.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return array.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return array.toArray(a);
	}
	
	public String toString() {
		return array.toString();
	}
}
