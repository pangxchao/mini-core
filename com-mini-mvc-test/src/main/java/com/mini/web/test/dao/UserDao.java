package com.mini.web.test.dao;

import com.google.inject.ImplementedBy;
import com.mini.jdbc.SQLBuilder;
import com.mini.jdbc.util.Paging;
import com.mini.util.StringUtil;
import com.mini.web.test.dao.base.BaseUserDao;
import com.mini.web.test.dao.impl.UserDaoImpl;
import com.mini.web.test.entity.Region;
import com.mini.web.test.entity.extend.UserExt;
import com.mini.web.test.entity.extend.UserExt.UserExtendBuilder;

import java.time.LocalDate;
import java.util.List;

import static com.mini.util.DateUtil.formatDate;
import static com.mini.web.test.entity.User.*;

/**
 * UserDao.java
 * @author xchao
 */
@ImplementedBy(UserDaoImpl.class)
public interface UserDao extends BaseUserDao {

    default List<UserExt> search(Paging paging, String search, int sortType, int phoneAuto, int emailAuto, String regionIdUri,
            LocalDate startTime, LocalDate endTime) {
        return query(paging, new UserExtendBuilder() {
            {
                // 搜索关键字条件
                if (!StringUtil.isBlank(search)) {
                    where("(%s LIKE ? OR %s LIKE ? OR %s LIKE ?)", NAME, FULL_NAME, PHONE);
                    params(search + "%", search + "%", search + "%");
                }

                // 手机号已认证
                if (phoneAuto == 1) {
                    where("%s = ?", PHONE_AUTH);
                    params(phoneAuto);
                }

                // 油箱已认证
                if (emailAuto == 1) {
                    where("%s = ?", EMAIL_AUTH);
                    params(emailAuto);
                }

                // 地区条件
                if (!StringUtil.isBlank(regionIdUri)) {
                    where("%s LIKE ?", Region.ID_URI);
                    params(regionIdUri + "%");
                }

                // 开始时间
                if (startTime != null) {
                    where("%s >= ? ", CREATE_TIME);
                    params(formatDate(startTime));
                }

                // 结束时间
                if (endTime != null) {
                    where("%s < ? ", CREATE_TIME);
                    params(formatDate(endTime.plusDays(1)));
                }
                // 排序
                order_by("%s %s", CREATE_TIME, (sortType == 1 ? "DESC" : "ASC"));
            }
        }, UserExt::mapper);
    }

    default int delete(long[] idList) {
        return execute(new SQLBuilder() {
            {
                delete().from(TABLE);
                for (long id : idList) {
                    or();
                    where("%s = ?", ID);
                    params(id);
                }
            }
        });
    }
}
