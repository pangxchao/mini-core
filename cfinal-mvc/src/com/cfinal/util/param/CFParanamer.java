/**
 * Created the com.cfinal.util.param.CFParanamer.java
 * @created 2017年2月23日 下午5:37:28
 * @version 1.0.0
 */
package com.cfinal.util.param;

import java.lang.reflect.AccessibleObject;

/**
 * com.cfinal.util.param.CFIParanamer.java
 * @author XChao
 */
public interface CFParanamer {
	public static final String[] EMPTY_NAMES = new String[0];

	public String[] lookupParameterNames(AccessibleObject methodOrConstructor);

	public String[] lookupParameterNames(AccessibleObject methodOrConstructor, boolean throwExceptionIfMissing);
}
