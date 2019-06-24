package com.mini.dao.row;

import com.mini.dao.IRow;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class BlobRow implements IRow<Blob> {
    public static final BlobRow INSTANCE = new BlobRow();

    private BlobRow() {}

    @Override
    public Blob execute(ResultSet rs, int index) throws SQLException {
        return rs.getBlob(index);
    }
}
