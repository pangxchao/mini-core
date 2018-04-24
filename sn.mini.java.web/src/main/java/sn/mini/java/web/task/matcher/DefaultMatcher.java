/**
 * Created the sn.mini.java.web.task.matcher.AbstractMatcher.java
 * @created 2017年11月1日 下午12:05:18
 * @version 1.0.0
 */
package sn.mini.java.web.task.matcher;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * sn.mini.java.web.task.matcher.AbstractMatcher.java
 * @author XChao
 */
public class DefaultMatcher implements IMatcher {
	private final Set<Integer> values = new HashSet<>();

	@Override
	public boolean match(int value) {
		return values.contains(value);
	}

	@Override
	public Set<Integer> getValues() {
		return this.values;
	}

	@Override
	public void add(int value) {
		this.values.add(value);
	}

	@Override
	public void addAll(Collection<Integer> values) {
		this.values.addAll(values);
	}
}
