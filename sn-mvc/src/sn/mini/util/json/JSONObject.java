/**
 * Created the sn.mini.util.json.JSONObject.java
 * @created 2017年9月1日 下午12:12:22
 * @version 1.0.0
 */
package sn.mini.util.json;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * sn.mini.util.json.JSONObject.java
 * @author XChao
 */
public class JSONObject implements Map<String, Object>, java.io.Serializable {
	private static final long serialVersionUID = -635137829495384098L;

	private final com.alibaba.fastjson.JSONObject object;

	JSONObject(com.alibaba.fastjson.JSONObject object) {
		this.object = object;
	}

	public JSONObject() {
		this.object = new com.alibaba.fastjson.JSONObject();
	}

	public void clear() {
		object.clear();
	}

	public Object clone() {
		return object.clone();
	}

	public boolean containsKey(Object key) {
		return object.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return object.containsValue(value);
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return object.entrySet();
	}

	public boolean equals(Object obj) {
		return object.equals(obj);
	}

	public JSONObject fluentClear() {
		object.fluentClear();
		return this;
	}

	public JSONObject fluentPut(String key, Object value) {
		object.fluentPut(key, value);
		return this;
	}

	public JSONObject fluentPutAll(Map<? extends String, ? extends Object> m) {
		object.fluentPutAll(m);
		return this;
	}

	public JSONObject fluentRemove(Object key) {
		object.fluentRemove(key);
		return this;
	}

	public Object get(Object key) {
		return object.get(key);
	}

	public BigDecimal getBigDecimal(String key) {
		return object.getBigDecimal(key);
	}

	public BigInteger getBigInteger(String key) {
		return object.getBigInteger(key);
	}

	public Boolean getBoolean(String key) {
		return object.getBoolean(key);
	}

	public boolean getBooleanValue(String key) {
		return object.getBooleanValue(key);
	}

	public Byte getByte(String key) {
		return object.getByte(key);
	}

	public byte getByteValue(String key) {
		return object.getByteValue(key);
	}

	public byte[] getBytes(String key) {
		return object.getBytes(key);
	}

	public Date getDate(String key) {
		return object.getDate(key);
	}

	public Double getDouble(String key) {
		return object.getDouble(key);
	}

	public double getDoubleValue(String key) {
		return object.getDoubleValue(key);
	}

	public Float getFloat(String key) {
		return object.getFloat(key);
	}

	public float getFloatValue(String key) {
		return object.getFloatValue(key);
	}

	public int getIntValue(String key) {
		return object.getIntValue(key);
	}

	public Integer getInteger(String key) {
		return object.getInteger(key);
	}

	public JSONArray getJSONArray(String key) {
		return new JSONArray(object.getJSONArray(key));
	}

	public JSONObject getJSONObject(String key) {
		return new JSONObject(object.getJSONObject(key));
	}

	public Long getLong(String key) {
		return object.getLong(key);
	}

	public long getLongValue(String key) {
		return object.getLongValue(key);
	}

	public <T> T getObject(String key, Class<T> clazz) {
		return object.getObject(key, clazz);
	}

	public Short getShort(String key) {
		return object.getShort(key);
	}

	public short getShortValue(String key) {
		return object.getShortValue(key);
	}

	public java.sql.Date getSqlDate(String key) {
		return object.getSqlDate(key);
	}

	public String getString(String key) {
		return object.getString(key);
	}

	public Timestamp getTimestamp(String key) {
		return object.getTimestamp(key);
	}

	public int hashCode() {
		return object.hashCode();
	}

	public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
		return object.invoke(arg0, arg1, arg2);
	}

	public boolean isEmpty() {
		return object.isEmpty();
	}

	public Set<String> keySet() {
		return object.keySet();
	}

	public Object put(String key, Object value) {
		return object.put(key, value);
	}

	public void putAll(Map<? extends String, ? extends Object> m) {
		object.putAll(m);
	}

	public Object remove(Object key) {
		return object.remove(key);
	}

	public int size() {
		return object.size();
	}

	public Collection<Object> values() {
		return object.values();
	}

	public String toString() {
		return object.toString();
	}
}
