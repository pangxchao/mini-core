package com.mini.spring.dao;

import com.mini.util.dao.sql.SQLInsert;

public interface BaseDao<T> {

    DaoTemplate getDaoTemplate();

    default int insert(SQLInsert sql){
        return getDaoTemplate().execute(sql);
    }

    T queryOne();
}
