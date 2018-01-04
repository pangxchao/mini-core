/**
 * Created the sn.mini.web.task.matcher.IMatcher.java
 * @created 2017年11月1日 上午11:20:08
 * @version 1.0.0
 */
package sn.mini.web.task.matcher;

import java.util.Collection;
import java.util.Set;

/**
 * sn.mini.web.task.matcher.IMatcher.java
 * @author XChao
 */
public interface IMatcher {
	public abstract boolean match(int value);

	public abstract Set<Integer> getValues();

	public abstract void add(int value);
	
	public abstract void addAll(Collection<Integer> values);
}
