/**
 * Created the sn.mini.util.lang.MethodUtil.java
 * @created 2017年9月1日 上午10:52:41
 * @version 1.0.0
 */
package sn.mini.util.lang;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import sn.mini.util.lang.reflect.BytecodeParanamer;
import sn.mini.util.lang.reflect.IParanamer;
import sn.mini.util.lang.reflect.SNMethod;
import sn.mini.util.lang.reflect.SNParameter;

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
		IParanamer parameterNames = new BytecodeParanamer();
		Parameter[] paramters = method.getParameters();
		String[] names = parameterNames.lookupParameterNames(method);
		if(paramters == null || names == null || paramters.length != names.length) {
			throw new RuntimeException("Read parameter to the method ‘" + method.getName() + "’ failed.");
		}
		SNParameter[] mParameters = new SNParameter[paramters.length];
		for (int i = 0, length = paramters.length; i < length; i++) {
			mParameters[i] = new SNParameter(paramters[i], names[i]);
		}
		return mParameters;
	}

}
