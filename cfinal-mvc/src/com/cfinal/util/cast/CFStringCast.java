/**
 * Created the com.cfinal.util.cast.CFStringCast.java
 * @created 2017年2月22日 下午4:07:06
 * @version 1.0.0
 */
package com.cfinal.util.cast;

/**
 * com.cfinal.util.cast.CFStringCast.java
 * @author XChao
 */
public class CFStringCast implements CFCast {

	@Override
	public Object cast(Object value) {
		if(value == null) {
			return null;
		}
		return String.valueOf(value);
	}

}
