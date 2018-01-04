/**
 * Created the com.cfinal.web.entity.CFUser.java
 * @created 2016年9月29日 上午10:56:39
 * @version 1.0.0
 */
package sn.mini.web.util;

import java.util.Date;
import java.util.List;

import sn.mini.util.PKGenerator;
import sn.mini.util.digest.Base64Util;

/**
 * com.xcc.web.entity.IUser.java
 * @author XChao
 */
public interface IUser {
	public static final String USER_KEY = "SN_USER_SESSION_KEY";

	/**
	 * @return the id
	 */
	public long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(long id);

	/**
	 * @return the name
	 */
	public String getName();

	/**
	 * @param name the name to set
	 */
	public void setName(String name);

	/**
	 * @return the nick
	 */
	public String getNick();

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick);

	/**
	 * @return the realName
	 */
	public String getRealName();

	/**
	 * @param realName the realName to set
	 */
	public void setRealName(String realName);

	/**
	 * @return the password
	 */
	public String getPassword();

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password);

	/**
	 * @return the phone
	 */
	public String getPhone();

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone);

	/**
	 * @return the email
	 */
	public String getEmail();

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email);

	/**
	 * @return the status
	 */
	public int getState();

	/**
	 * @param status the status to set
	 */
	public void setState(int state);

	/**
	 * @return the token
	 */
	public String getToken();

	/**
	 * @param token the token to set
	 */
	public void setToken(String token);

	/**
	 * @return the create
	 */
	public Date getCreate();

	/**
	 * @param create the create to set
	 */
	public void setCreate(Date create);

	/**
	 * @return the update
	 */
	public Date getUpdate();

	/**
	 * @param update the update to set
	 */
	public void setUpdate(Date update);

	/**
	 * @return the roles
	 */
	public List<IRole> getRoles();

	/**
	 * 添加一个用户角色
	 * @param role
	 * @return
	 */
	public void addRole(IRole role);

	/**
	 * 邮箱是否认证
	 * @return
	 */
	default boolean isAuthEmail() {
		return (this.getState() & 1) == 1;
	}

	/**
	 * 电话是否认证
	 * @return
	 */
	default boolean isAuthPhone() {
		return (this.getState() & 2) == 2;
	}

	/**
	 * 证件号是否认证(是否实名认证)
	 * @return
	 */
	default boolean isAuthIdcard() {
		return (this.getState() & 4) == 4;
	}

	/**
	 * 用户是否为禁用状态
	 * @return
	 */
	default boolean isDisable() {
		return (this.getState() & 16) == 16;
	}

	/**
	 * 用户是否为删除状态
	 * @return
	 */
	default boolean isDelete() {
		return (this.getState() & 32) == 32;
	}

	/**
	 * 重新创建用户token
	 * @param uid
	 * @return
	 */
	public static String newToken(long uid) {
		return Base64Util.encode(PKGenerator.genseed(6) + uid);
	}

	/**
	 * 解码token 获取用户uid
	 * @param token
	 * @return
	 */
	public static long decodeToken(String token) {
		return Long.valueOf(Base64Util.decode(token).substring(6));
	}
}
