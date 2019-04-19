/**
 * Created the sn.mini.util.lang.MethodUtil.java
 * @created 2017年9月1日 上午10:52:41
 * @version 1.0.0
 */
package sn.mini.java.util.lang;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import sn.mini.java.util.lang.reflect.ByteCodeParameter;
import sn.mini.java.util.lang.reflect.IParameter;
import sn.mini.java.util.lang.reflect.SNMethod;
import sn.mini.java.util.lang.reflect.SNParameter;

/**
 * sn.mini.util.lang.MethodUtil.java
 * @author XChao
 */
public final class MethodUtil {
	private MethodUtil() {
	}

	public static SNMethod getSNMethod(Method method) {
		return new SNMethod(method);
	}

	/**
	 * 获取方法参数列表
	 * @param method
	 * @return
	 * @throws Exception
	 */
	public static SNParameter[] getSNParameter(Method method) {
		IParameter parameterNames = new ByteCodeParameter();
		Parameter[] parameters = method.getParameters();
		String[] names = parameterNames.lookupParameterNames(method);
		if(parameters == null || names == null || parameters.length != names.length) {
			throw new RuntimeException("Read parameter to the method ‘" + method.getName() + "’ failed.");
		}
		SNParameter[] mParameters = new SNParameter[parameters.length];
		for (int i = 0, length = parameters.length; i < length; i++) {
			mParameters[i] = new SNParameter(parameters[i], names[i]);
		}
		return mParameters;
	}

}
