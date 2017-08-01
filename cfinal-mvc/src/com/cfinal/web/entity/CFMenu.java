/**
 * Created the com.cfinal.web.entity.CFMenu.java
 * @created 2017年2月3日 下午4:03:02
 * @version 1.0.0
 */
package com.cfinal.web.entity;

import java.util.Map;

import com.cfinal.web.annotaion.CFAction;
import com.cfinal.web.central.CFBasics;

/**
 * com.cfinal.web.entity.CFMenu.java
 * @author XChao
 */
public interface CFMenu extends CFBasics {

	/**
	 * @return the id
	 */
	public int getId();

	/**
	 * @param id the id to set
	 */
	public void setId(int id);

	/**
	 * @return the name
	 */
	public String getName();

	/**
	 * @param name the name to set
	 */
	public void setName(String name);

	/**
	 * @return the url
	 */
	public String getUrl();

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url);

	/**
	 * @return the type
	 */
	public int getType();

	/**
	 * @param type the type to set
	 */
	public void setType(int type);

	/**
	 * @return the value
	 */
	public int getValue();

	/**
	 * @param value the value to set
	 */
	public void setValue(int value);

	/**
	 * @return the parentId
	 */
	public int getParentId();

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(int parentId);

	/**
	 * @return the parentName
	 */
	public String getParentName();

	/**
	 * @param parentName the parentName to set
	 */
	public void setParentName(String parentName);

	/**
	 * @return the childs
	 */
	public Map<Integer, String> getChilds();

	/**
	 * 添加一个功能点
	 * @param value
	 * @param remaker
	 */
	public CFMenu addChild(Integer value, String remaker);

	/**
	 * 从Action注解中获取菜单属性值
	 * @param action
	 * @return
	 */
	default CFMenu valueOf(CFAction action) {
		this.setId(action.menuId());
		this.setName(action.menuName());
		this.setType(action.menuType());
		this.setValue(action.permiss());
		this.setParentId(action.menuParentId());
		this.setParentName(action.menuParentName());
		return this;
	}
}
