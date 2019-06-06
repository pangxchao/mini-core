package com.mini.util.dao;

import com.mini.util.lang.MiniMap;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Map;

public final class SQLValue extends MiniMap<String> implements Serializable {
    private static final long serialVersionUID = 971259046379455553L;

    public SQLValue() {
    }

    public SQLValue(int initialCapacity) {
        super(initialCapacity);
    }

    public SQLValue(Map<? extends String, ?> m) {
        super(m);
    }

    public SQLValue(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public byte[] getAsByteArray(String key) {
        return getAs(key, byte[].class);
    }

    public InputStream getAsInputStream(String key) {
        return getAs(key, InputStream.class);
    }

    public BigDecimal getAsBigDecimal(String key) {
        return getAs(key, BigDecimal.class);
    }

    public Blob getAsBlob(String key) {
        return getAs(key, Blob.class);
    }

    public Reader getAsReader(String key) {
        return getAs(key, Reader.class);
    }

    public Clob getAsClob(String key) {
        return getAs(key, Clob.class);
    }

    public Date getAsDate(String key) {
        return getAs(key, Date.class);
    }

    public Time getAsTime(String key) {
        return getAs(key, Time.class);
    }

    public Timestamp getAsTimestamp(String key) {
        return getAs(key, Timestamp.class);
    }

    public Ref getAsRef(String key) {
        return getAs(key, Ref.class);
    }

    public RowId getAsRowId(String key) {
        return getAs(key, RowId.class);
    }

    public SQLXML getAsSQLXml(String key) {
        return getAs(key, SQLXML.class);
    }

    public URL getAsURL(String key) {
        return getAs(key, URL.class);
    }

}
