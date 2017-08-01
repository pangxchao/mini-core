/**
 * Created the com.cfinal.util.cast.CFIntCast.java
 * @created 2017年2月22日 下午4:07:57
 * @version 1.0.0
 */
package com.cfinal.util.cast;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * com.cfinal.util.cast.CFIntCast.java 
 * @author XChao
 */
public class CFIntCast implements CFCast {

	@Override
	public Object cast(Object value) {
		if(value == null) {
			return new Integer(0);
		}
		String val = String.valueOf(value);
		if (StringUtils.isBlank(val)) {
			return new Integer(0);
		}
		return Integer.valueOf(val);
	}

}
