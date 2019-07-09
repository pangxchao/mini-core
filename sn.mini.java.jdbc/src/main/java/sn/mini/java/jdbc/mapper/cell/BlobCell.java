package sn.mini.java.jdbc.mapper.cell;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * sn.mini.jdbc.mapper.cell.BlobCell.java
 * @author XChao
 */
public class BlobCell implements ICell<Blob> {

	@Override
	public Blob getCell(ResultSet resultSet, String columnLabel) throws SQLException {
		return resultSet.getBlob(columnLabel);
	}

}
