/**   
 * Created the sn.mini.dao.model.ModelMapping.java
 * @created 2017年11月6日 上午10:53:48 
 * @version 1.0.0 
 */
package sn.mini.dao.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sn.mini.util.lang.MapUtil;

/**   
 * sn.mini.dao.model.ModelMapping.java 
 * @author XChao  
 */
public class ModelMapping {
	private static final Map<Class<?>, DaoTable> DAO_TABLE = new ConcurrentHashMap<>();

	public static DaoTable getDaoTable(Class<?> clazz) {
		return MapUtil.getOrDefaultAndPut(DAO_TABLE, clazz, () -> new DaoTable(clazz));
	}
}
