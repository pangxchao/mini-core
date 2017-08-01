/**
 * Created the com.cfinal.util.cast.ByteCast.java 
 * @created 2017年2月22日 下午4:07:18
 * @version 1.0.0
 */
package com.cfinal.util.cast;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * com.cfinal.util.cast.ByteCast.java 
 * @author XChao
 */
public class CFByteCast implements CFCast {

	@Override
	public Object cast(Object value) {
		if(value == null) {
			return new Byte((byte) 0);
		}
		String val = String.valueOf(value);
		if(StringUtils.isBlank(val)) {
			return new Byte((byte) 0);
		}
		return Byte.valueOf(val);
	}

}
