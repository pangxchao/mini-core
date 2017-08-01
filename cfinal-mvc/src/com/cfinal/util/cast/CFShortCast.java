/**
 * Created the com.cfinal.util.cast.CFShortCast.java
 * @created 2017年2月22日 下午4:07:28
 * @version 1.0.0
 */
package com.cfinal.util.cast;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * com.cfinal.util.cast.CFShortCast.java 
 * @author XChao
 */
public class CFShortCast implements CFCast {

	@Override
	public Object cast(Object value) {
		if(value == null) {
			return new Short((short) 0);
		}
		String val = String.valueOf(value);
		if (StringUtils.isBlank(val)) {
			return new Short((short) 0);
		}
		return Short.valueOf(val);
	}

}
