/**
 * Created com.cfinal.web.entity.CFRoleMenu.java
 * @created 2017年2月3日 上午10:53:47
 * @version 1.0.0
 */
package sn.mini.java.web.util;

/**
 * -- 角色权限表 <br/>
 * @author XChao
 */
public interface IRoleMenu {

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
	 * @return the menuId
	 */
	public int getMenuId();

	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(int menuId);

	/**
	 * @return the value
	 */
	public int getValue();

	/**
	 * @param value the value to set
	 */
	public void setValue(int value);
}
