/**
 * Created the sn.mini.dao.util.DaoThreadLocal.java
 * @created 2017年11月2日 上午11:50:00
 * @version 1.0.0
 */
package sn.mini.dao.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sn.mini.dao.IDao;

/**
 * sn.mini.dao.util.DaoThreadLocal.java
 * @author XChao
 */
public class DaoThreadLocal extends ThreadLocal<Map<String, IDao>> {
	public Map<String, IDao> initialValue() {
		return new ConcurrentHashMap<String, IDao>(); // 保存当前线程的所有数据库连接
	}
}
