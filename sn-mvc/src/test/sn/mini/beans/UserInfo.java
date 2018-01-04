/**
 * Created the com.mengyi.user.auth.beans.UserInfo.java
 * @created 2017-08-02 09:35:47
 */
package test.sn.mini.beans;

import java.sql.SQLException;
import java.util.List;

import sn.mini.dao.IDao;
import sn.mini.dao.Sql;
import sn.mini.dao.annotaion.Binding;
import sn.mini.dao.model.IDaoModel;
import sn.mini.web.util.IRole;
import sn.mini.web.util.IUser;
import test.sn.mini.beans.entity.UserInfoEntity;

/**
 * com.mengyi.user.auth.beans.UserInfo.java
 * @author XChao
 */
@Binding("users_user_info")
public class UserInfo extends UserInfoEntity implements IUser, IDaoModel<UserInfo> {
	private static final long serialVersionUID = 1L;

	@Override
	public int getState() {
		return 0;
	}

	@Override
	public void setState(int state) {

	}

	@Override
	public List<IRole> getRoles() {
		return null;
	}

	@Override
	public void addRole(IRole role) {

	}

	public static int insert(IDao dao, long id) throws SQLException {
		Sql sql = Sql.create().insert(TABLE_NAME).lb().col(USER_ID, USER_NAME).rb().values().lb().q().c().q().rb();
		return dao.execute(sql);
	}
}
