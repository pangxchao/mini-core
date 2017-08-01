/**   
 * Created the com.cfinal.util.cast.CFDefaultCast.java 
 * @created 2017年2月25日 上午1:03:02 
 * @version 1.0.0 
 */
package com.cfinal.util.cast;

/**
 * 
 * com.cfinal.util.cast.CFDefaultCast.java 
 * @author XChao
 */
public class CFDefaultCast implements CFCast{

	@Override
	public Object cast(Object value) {
		return value;
	}

}
