/**
 * Created the sn.mini.util.lang.reflect.IParanamer.java 
 * @created 2017年2月23日 下午5:37:28
 * @version 1.0.0
 */
package sn.mini.util.lang.reflect;

import java.lang.reflect.AccessibleObject;

/**
 * sn.mini.util.lang.reflect.IParanamer.java 
 * @author XChao
 */
public interface IParanamer {
	public static final String[] EMPTY_NAMES = new String[0];

	public String[] lookupParameterNames(AccessibleObject methodOrConstructor);

	public String[] lookupParameterNames(AccessibleObject methodOrConstructor, boolean throwExceptionIfMissing);
}
