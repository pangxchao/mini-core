/**
 * Created the com.cfinal.web.entity.CFUser.java
 * @created 2016年9月29日 上午10:56:39
 * @version 1.0.0
 */
package com.cfinal.web.entity;

import java.util.Date;
import java.util.List;

import com.cfinal.util.CFPKGenerator;
import com.cfinal.util.digest.CFBase64;

/**
 * com.xcc.web.entity.IUser.java
 * @author XChao
 */
public interface CFUser {

	public static final String USER_KEY = "CF_USER_SESSION_KEY";

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
	 * @return the seed
	 */
	public String getSeed();

	/**
	 * @param seed the seed to set
	 */
	public void setSeed(String seed);

	/**
	 * @return the head
	 */
	public String getHead();

	/**
	 * @param head the head to set
	 */
	public void setHead(String head);

	/**
	 * @return the email
	 */
	public String getEmail();

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email);

	/**
	 * @return the phone
	 */
	public String getPhone();

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone);

	/**
	 * @return the idcard
	 */
	public String getIdcard();

	/**
	 * @param idcard the idcard to set
	 */
	public void setIdcard(String idcard);

	/**
	 * @return the gender
	 */
	public int getGender();

	/**
	 * @param gender the gender to set
	 */
	public void setGender(int gender);

	/**
	 * @return the birthday
	 */
	public Date getBirthday();

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday);

	/**
	 * @return the address
	 */
	public String getAddress();

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address);

	/**
	 * @return the signature
	 */
	public String getSignature();

	/**
	 * @param signature the signature to set
	 */
	public void setSignature(String signature);

	/**
	 * @return the lng
	 */
	public double getLng();

	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng);

	/**
	 * @return the lat
	 */
	public double getLat();

	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat);

	/**
	 * @return the status
	 */
	public int getStatus();

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status);

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
	public List<CFRole> getRoles();

	/**
	 * 添加一个用户角色
	 * @param role
	 * @return
	 */
	public void addRole(CFRole role);

	/**
	 * 邮箱是否认证
	 * @return
	 */
	default boolean isAuthEmail() {
		return (this.getStatus() & 1) == 1;
	}

	/**
	 * 电话是否认证
	 * @return
	 */
	default boolean isAuthPhone() {
		return (this.getStatus() & 2) == 2;
	}

	/**
	 * 证件号是否认证(是否实名认证)
	 * @return
	 */
	default boolean isAuthIdcard() {
		return (this.getStatus() & 4) == 4;
	}

	/**
	 * 用户是否为禁用状态
	 * @return
	 */
	default boolean isDisable() {
		return (this.getStatus() & 16) == 16;
	}

	/**
	 * 用户是否为删除状态
	 * @return
	 */
	default boolean isDelete() {
		return (this.getStatus() & 32) == 32;
	}

	/**
	 * 重新创建用户token
	 * @param uid
	 * @return
	 */
	public static String newToken(long uid) {
		return CFBase64.encode(CFPKGenerator.genseed(6) + uid);
	}

	/**
	 * 解码token 获取用户uid
	 * @param token
	 * @return
	 */
	public static long decodeToken(String token) {
		return Long.valueOf(CFBase64.decode(token).substring(6));
	}
}
