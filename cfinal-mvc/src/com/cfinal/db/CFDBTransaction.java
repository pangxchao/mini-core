/**   
 * Created the com.cfinal.db.CFDBTransaction.java
 * @created 2016年10月22日 下午3:05:39 
 * @version 1.0.0 
 */
package com.cfinal.db;

import java.sql.SQLException;

/**   
 * com.cfinal.db.CFDBTransaction.java 
 * @author XChao  
 */
@FunctionalInterface
public interface CFDBTransaction {
	public boolean transaction() throws SQLException;
}
