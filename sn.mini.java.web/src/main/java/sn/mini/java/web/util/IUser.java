/**
 * Created the com.cfinal.web.entity.CFUser.java
 *
 * @created 2016年9月29日 上午10:56:39
 * @version 1.0.0
 */
package sn.mini.java.web.util;

import sn.mini.java.util.PKGenerator;

/**
 * com.xcc.web.entity.IUser.java
 *
 * @author XChao
 */
public interface IUser {
	String USER_KEY = "SN_USER_SESSION_KEY";

	/**
	 * @return the id
	 */
	long getId();

	/**
	 * @param id the id to set
	 */
	void setId(long id);

	/**
	 * 重新创建用户token
	 *
	 * @param uid
	 * @return
	 */
	static String newToken(long uid) {
		return PKGenerator.genseed(17) + Long.toHexString(uid).toUpperCase();
	}

	/**
	 * *解码token 获取用户 uid
	 *
	 * @param token
	 * @return
	 */
	static long decodeToken(String token) {
		try {
			return Long.valueOf(token.substring(17), 16);
		} catch (Exception e) {}
		return 0;
	}
}
