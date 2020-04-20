package com.mini.web.test.dao.base;

import com.mini.core.jdbc.JdbcInterface;
import com.mini.core.jdbc.builder.SQLBuilder;
import com.mini.core.jdbc.mapper.BeanMapper;
import com.mini.core.jdbc.model.Paging;
import com.mini.web.test.entity.User;

import java.util.List;

/**
 * UserBaseDao.java
 * @author xchao
 */
public interface UserBaseDao extends JdbcInterface {
	/**
	 * 删除实体信息
	 * @param id 用户ID
	 * @return 执行结果
	 */
	default int deleteById(long id) {
		return execute(new SQLBuilder() {{
			delete().from(User.TABLE);
			// 用户ID
			where("%s = ?", User.USER_ID);
			args(id);
		}});
	}
	
	/**
	 * 根据ID查询实体信息
	 * @param id 用户ID
	 * @return 实体信息
	 */
	default User queryById(long id) {
		return queryObject(new SQLBuilder(User.class) {{
			// 用户ID
			where("%s = ?", User.USER_ID);
			args(id);
		}}, BeanMapper.create(User.class));
	}
	
	/**
	 * 查询所有实体信息
	 * @return 实体信息列表
	 */
	default List<User> queryAll() {
		return queryList(new SQLBuilder(User.class) {{
			//
		}}, BeanMapper.create(User.class));
	}
	
	/**
	 * 查询所有实体信息
	 * @param page  分页-页码数
	 * @param limit 分页- 每页条数
	 * @return 实体信息列表
	 */
	default Paging<User> queryAll(int page, int limit) {
		return queryPaging(page, limit, new SQLBuilder(User.class) {{
			//
		}}, BeanMapper.create(User.class));
	}
}
