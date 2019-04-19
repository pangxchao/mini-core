package com.mini.util.dao.row;

import com.mini.util.dao.IRow;

import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class CharacterStreamRow implements IRow<Reader> {
    public static final CharacterStreamRow INSTANCE = new CharacterStreamRow();

    private CharacterStreamRow() {}

    @Override
    public Reader execute(ResultSet rs, int index) throws SQLException {
        return rs.getCharacterStream(index);
    }
}
