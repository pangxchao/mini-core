/**
 * Created the com.cfinal.util.lang.CFObject.java
 * @created 2017年5月24日 下午6:13:46
 * @version 1.0.0
 */
package com.cfinal.util.lang;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 常用公共对象工具类，Object对象工具类
 * @author XChao
 */
public class CFObject {

	public static <T> T letval(JSONObject object, Class<T> clazz, String key) {
		if(object == null) {
			return null;
		}
		return object.getObject(key, clazz);
	}

	public static <T> T letval(JSONArray array, Class<T> clazz, int index) {
		if(array == null) {
			return null;
		}
		return array.getObject(index, clazz);
	}

	public static JSONObject letobj(JSONObject object, String key) {
		if(object == null) {
			return null;
		}
		return object.getJSONObject(key);
	}

	public static JSONObject letobj(JSONArray object, int index) {
		if(object == null) {
			return null;
		}
		return object.getJSONObject(index);
	}

	public static JSONArray letarr(JSONObject object, String key) {
		if(object == null) {
			return null;
		}
		return object.getJSONArray(key);
	}

	public static JSONArray letarr(JSONArray object, int index) {
		if(object == null) {
			return null;
		}
		return object.getJSONArray(index);
	}
}
