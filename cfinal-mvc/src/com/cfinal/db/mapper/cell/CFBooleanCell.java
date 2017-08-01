/**   
 * Created the com.cfinal.db.mapper.cell.CFBooleanCell.java
 * @created 2016年10月9日 下午5:42:21 
 * @version 1.0.0 
 */
package com.cfinal.db.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfinal.db.CFDB;

/**   
 * com.cfinal.db.mapper.cell.CFBooleanCell.java 
 * @author XChao  
 */
public class CFBooleanCell implements CFCell<Boolean>{

	@Override
	public Boolean getCell(CFDB db, ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getBoolean(columnLabel);
	}

}
