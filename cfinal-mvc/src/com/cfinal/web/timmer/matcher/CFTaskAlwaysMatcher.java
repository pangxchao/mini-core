/**   
 * Created the com.cfinal.web.scheduler.matcher.CFTaskAlwaysMatcher.java
 * @created 2016年10月17日 下午4:52:18 
 * @version 1.0.0 
 */
package com.cfinal.web.timmer.matcher;

/**   
 * com.cfinal.web.scheduler.matcher.CFTaskAlwaysMatcher.java 
 * @author XChao  
 */
public class CFTaskAlwaysMatcher implements CFTaskMatcher{
	public boolean match(int value) {
		return true;
	}
}
