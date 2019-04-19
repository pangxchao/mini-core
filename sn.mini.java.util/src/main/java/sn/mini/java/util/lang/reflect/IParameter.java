/**
 * Created the sn.mini.util.lang.reflect.IParameter.java
 * @created 2017年2月23日 下午5:37:28
 * @version 1.0.0
 */
package sn.mini.java.util.lang.reflect;

import java.lang.reflect.AccessibleObject;

/**
 * sn.mini.util.lang.reflect.IParameter.java
 * @author XChao
 */
public interface IParameter {
	String[] EMPTY_NAMES = new String[0];

	String[] lookupParameterNames(AccessibleObject methodOrConstructor);

	String[] lookupParameterNames(AccessibleObject methodOrConstructor, boolean throwExceptionIfMissing);
}
