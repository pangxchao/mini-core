package com.mini.util.dao.mapper;

import com.mini.util.dao.IMapper;
import com.mini.util.dao.IRow;
import com.mini.util.dao.row.*;

import javax.annotation.Nullable;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractMapper<T> implements IMapper<T> {
    private static final Map<String, IRow<?>> rows = new ConcurrentHashMap<String, IRow<?>>() {
        private static final long serialVersionUID = 1L;

        {
            put(Object.class.getName(), ObjectRow.INSTANCE);
            put(Byte.class.getName(), ByteRow.INSTANCE);
            put(byte.class.getName(), ByteRow.INSTANCE);
            put(Short.class.getName(), ShortRow.INSTANCE);
            put(short.class.getName(), ShortRow.INSTANCE);
            put(Integer.class.getName(), IntRow.INSTANCE);
            put(int.class.getName(), IntRow.INSTANCE);
            put(Long.class.getName(), LongRow.INSTANCE);
            put(long.class.getName(), LongRow.INSTANCE);
            put(Float.class.getName(), FloatRow.INSTANCE);
            put(float.class.getName(), FloatRow.INSTANCE);
            put(Double.class.getName(), DoubleRow.INSTANCE);
            put(double.class.getName(), DoubleRow.INSTANCE);
            put(String.class.getName(), StringRow.INSTANCE);
            // put(String.class.getName(),  NStringRow.INSTANCE);
            put(Boolean.class.getName(), BooleanRow.INSTANCE);
            put(BigDecimal.class.getName(), BigDecimalRow.INSTANCE);
            put(byte[].class.getName(), BytesRow.INSTANCE);
            put(java.sql.Date.class.getName(), DateRow.INSTANCE);
            put(java.sql.Time.class.getName(), TimeRow.INSTANCE);
            put(java.sql.Timestamp.class.getName(), TimestampRow.INSTANCE);
            put(java.util.Date.class.getName(), TimestampRow.INSTANCE);
            put(InputStream.class.getName(), AsciiStreamRow.INSTANCE);
            // put(InputStream.class.getName(),  BinaryStreamRow.INSTANCE);
            put(Reader.class.getName(), CharacterStreamRow.INSTANCE);
            // put(Reader.class.getName(),  NCharacterStreamRow.INSTANCE);
            put(Ref.class.getName(), RefRow.INSTANCE);
            put(Blob.class.getName(), BlobRow.INSTANCE);
            put(Clob.class.getName(), ClobRow.INSTANCE);
            put(NClob.class.getName(), NClobRow.INSTANCE);
            put(Array.class.getName(), ArrayRow.INSTANCE);
            put(URL.class.getName(), URLRow.INSTANCE);
            put(RowId.class.getName(), RowIdRow.INSTANCE);
            put(SQLXML.class.getName(), SQLXmlRow.INSTANCE);

        }
    };


    @Nullable
    protected final IRow<?> getRow(Class<?> clazz) {
        return rows.get(clazz.getName());
    }

    @Nullable
    protected final IRow<?> getRow(String clazz) {
        return rows.get(clazz);
    }
}
