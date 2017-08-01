/**   
 * Created the com.cfinal.db.mapper.cell.CFBlobCell.java
 * @created 2016年10月9日 下午5:52:02 
 * @version 1.0.0 
 */
package com.cfinal.db.mapper.cell;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.cfinal.db.CFDB;

/**   
 * com.cfinal.db.mapper.cell.CFBlobCell.java 
 * @author XChao  
 */
public class CFBlobCell implements CFCell<Blob>{

	@Override
	public Blob getCell(CFDB db, ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getBlob(columnLabel);
	}

}
