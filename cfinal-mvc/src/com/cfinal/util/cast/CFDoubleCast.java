/**
 * Created the com.cfinal.util.castCFDoubleCast.java
 * @created 2017年2月22日 下午4:08:29
 * @version 1.0.0
 */
package com.cfinal.util.cast;

import org.apache.commons.lang.StringUtils;

/**
 * com.cfinal.util.cast.CFDoubleCast.java
 * @author XChao
 */
public class CFDoubleCast implements CFCast {

	@Override
	public Object cast(Object value) {
		if(value == null) {
			return new Double(0.0D);
		}
		String val = String.valueOf(value);
		if(StringUtils.isBlank(val)) {
			return new Double(0.0D);
		}
		return Double.valueOf(val);
	}

}
