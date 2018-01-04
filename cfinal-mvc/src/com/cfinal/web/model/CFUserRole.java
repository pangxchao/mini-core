/**
 * Created the com.cfinal.web.entity.CFUserRole.java
 * @created 2017年2月3日 上午10:53:58
 * @version 1.0.0
 */
package com.cfinal.web.model;

/**
 * -- 用户角色关系表 <br/>
 * @author XChao
 */
public interface CFUserRole{

	/**
	 * @return the id
	 */
	public long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(long id);

	/**
	 * @return the roleId
	 */
	public long getRoleId();

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(long roleId);

	/**
	 * @return the userId
	 */
	public long getUserId();

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId);

}
