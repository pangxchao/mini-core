/**
 * Created the sn.mini.java.util.json.JSON.java
 * @created 2017年9月1日 上午10:49:59
 * @version 1.0.0
 */
package sn.mini.java.util.json;

/**
 * sn.mini.java.util.json.JSON.java
 * @author XChao
 */
public final class JSON {

	public static String toJsonString(Object object) {
		return com.alibaba.fastjson.JSON.toJSONString(object);
	}

	public static JSONObject parseObject(String text) {
		return new JSONObject(com.alibaba.fastjson.JSON.parseObject(text));
	}

	public static JSONArray parseArray(String text) {
		return new JSONArray(com.alibaba.fastjson.JSON.parseArray(text));
	}

	public static <T> T parse(String text, Class<T> clazz) {
		return com.alibaba.fastjson.JSON.parseObject(text, clazz);
	}
}
