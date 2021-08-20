package com.mini.core.jdbc.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

public class MiniMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.findTableInfo(metaObject).getFieldList().forEach(fieldInfo -> {
            if (fieldInfo.getField().getType().isAssignableFrom(Date.class)) {
                strictInsertFill(metaObject, fieldInfo.getField().getName(),
                        Date::new, Date.class);
            }
        });
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.findTableInfo(metaObject).getFieldList().forEach(fieldInfo -> {
            if (fieldInfo.getField().getType().isAssignableFrom(Date.class)) {
                strictUpdateFill(metaObject, fieldInfo.getField().getName(),
                        Date::new, Date.class);
            }
        });
    }
}
