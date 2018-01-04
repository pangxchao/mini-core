/**
 * Created the sn.mini.util.lang.MapUtil.java
 * @created 2017年11月6日 上午10:30:02
 * @version 1.0.0
 */
package sn.mini.util.lang;

import java.util.Map;
import java.util.function.Supplier;

/**
 * sn.mini.util.lang.MapUtil.java
 * @author XChao
 */
public final class MapUtil {

	public static <K, V> V getOrDefaultAndPut(Map<K, V> map, K k, Supplier<V> v) {
		V result = map.get(k);
		if(result == null) {
			V v1 = v.get();
			map.put(k, v1);
			return v1;
		}
		return result;
	}
}
