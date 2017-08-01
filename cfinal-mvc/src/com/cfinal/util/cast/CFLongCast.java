/**
 * Created the com.cfinal.util.cast.CFLongCast.java
 * @created 2017年2月22日 下午4:08:06
 * @version 1.0.0
 */
package com.cfinal.util.cast;

import org.apache.commons.lang.StringUtils;

/**
 * com.cfinal.util.cast.CFLongCast.java
 * @author XChao
 */
public class CFLongCast implements CFCast {

	@Override
	public Object cast(Object value) {
		if(value == null) {
			return new Long(0L);
		}
		String val = String.valueOf(value);
		if(StringUtils.isBlank(val)) {
			return new Long(0L);
		}
		return Long.valueOf(val);
	}

}
