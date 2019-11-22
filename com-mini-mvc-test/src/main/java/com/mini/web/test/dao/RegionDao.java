package com.mini.web.test.dao;

import com.google.inject.ImplementedBy;
import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.web.test.dao.base.RegionBaseDao;
import com.mini.web.test.dao.impl.RegionDaoImpl;
import com.mini.web.test.entity.Region;

import java.util.List;

import static com.mini.web.test.entity.Region.REGION_ID;

/**
 * RegionDao.java
 * @author xchao
 */
@ImplementedBy(RegionDaoImpl.class)
public interface RegionDao extends RegionBaseDao {
    default List<Region> queryByParent(long parentId) {
        return queryList(new SQLBuilder() {{
            select("*").from(Region.TABLE);
            where("%s = ?", REGION_ID);
            params(parentId);
        }}, Region::mapper);
    }
}
