/**
 * Created the com.cfinal.util.cast.CFFloatCast.java
 * @created 2017年2月22日 下午4:08:15
 * @version 1.0.0
 */
package com.cfinal.util.cast;

import org.apache.commons.lang.StringUtils;

/**
 * com.cfinal.util.cast.CFFloatCast.java
 * @author XChao
 */
public class CFFloatCast implements CFCast {

	@Override
	public Object cast(Object value) {
		if(value == null) {
			return new Float(0.0F);
		}
		String val = String.valueOf(value);
		if(StringUtils.isBlank(val)) {
			return new Float(0.0F);
		}
		return Float.valueOf(val);
	}
}
