/**   
 * Created the com.cfinal.db.mapper.cell.CFIntegerCell.java
 * @created 2016年10月9日 下午5:37:56 
 * @version 1.0.0 
 */
package com.cfinal.db.mapper.cell;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfinal.db.CFDB;

/**   
 * com.cfinal.db.mapper.cell.CFIntegerCell.java 
 * @author XChao  
 */
public class CFIntegerCell implements CFCell<Integer>{

	@Override
	public Integer getCell(CFDB db, ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getInt(columnLabel);
	}

}
