package com.mini.core.web.test.dao;

import com.google.inject.ImplementedBy;
import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.model.Paging;
import com.mini.core.util.DateFormatUtil;
import com.mini.core.web.test.dao.base.UserBaseDao;
import com.mini.core.web.test.dao.impl.UserDaoImpl;
import com.mini.core.web.test.entity.Region;
import com.mini.core.web.test.entity.extend.UserExt;
import com.mini.core.web.test.entity.extend.UserExt.UserExtendBuilder;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static com.mini.core.web.test.entity.User.*;

/**
 * UserDao.java
 * @author xchao
 */
@ImplementedBy(UserDaoImpl.class)
public interface UserDao extends UserBaseDao {

	default List<UserExt> search(Paging paging, String search, int sortType, int phoneAuto, int emailAuto, String regionIdUri,
		LocalDate startTime, LocalDate endTime) {
		return queryList(paging, new UserExtendBuilder() {{
			// 搜索关键字条件
			if (!StringUtils.isBlank(search)) {
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
			if (!StringUtils.isBlank(regionIdUri)) {
				where("%s LIKE ?", Region.ID_URI);
				params(regionIdUri + "%");
			}

			// 开始时间
			if (startTime != null) {
				where("%s >= ? ", CREATE_TIME);
				params(DateFormatUtil.formatDate(startTime));
			}

			// 结束时间
			if (endTime != null) {
				where("%s < ? ", CREATE_TIME);
				params(DateFormatUtil.formatDate(endTime.plusDays(1)));
			}
			// 排序
			orderBy("%s %s", CREATE_TIME, (sortType == 1 ? "DESC" : "ASC"));
		}}, UserExt::mapper);
	}

	default int delete(long[] idList) {
		return execute(new SQLBuilder() {{
			delete().from(TABLE);
			for (long id : idList) {
				or();
				where("%s = ?", ID);
				params(id);
			}
		}});
	}
}
