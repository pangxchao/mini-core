/**
 * Created the sn.mini.dao.util.DaoThreadLocal.java
 * @created 2017年11月2日 上午11:50:00
 * @version 1.0.0
 */
package sn.mini.java.jdbc.util;

import sn.mini.java.jdbc.IDao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * sn.mini.dao.util.DaoThreadLocal.java
 * @author XChao
 */
public class DaoThreadLocal extends ThreadLocal<Map<String, IDao>> {
    public Map<String, IDao> initialValue() {
        return new ConcurrentHashMap<>();
    }
}
