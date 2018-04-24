/**
 * Created the com.cfinal.web.entity.CFRole.java
 * @created 2017年2月3日 上午10:51:10
 * @version 1.0.0
 */
package sn.mini.java.web.util;

/**
 * --系统角色信息 <br/>
 * @author XChao
 */
public interface IRole {

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
	 * @return the disc
	 */
	public String getDisc();

	/**
	 * @param disc the disc to set
	 */
	public void setDisc(String disc);

	/**
	 * @return the type
	 */
	public int getType();

	/**
	 * @param type the type to set
	 */
	public void setType(int type);

}
