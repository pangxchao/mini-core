/**
 * Created the com.cfinal.web.preprocessor.CFMenuPreprocessor.java
 * @created 2017年2月3日 上午10:04:50
 * @version 1.0.0
 */
package com.cfinal.web.preprocessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.cfinal.web.control.CFActionPorxy;
import com.cfinal.web.entity.CFMenu;
import com.cfinal.web.entity.CFRole;
import com.cfinal.web.entity.CFRoleMenu;

/**
 * com.cfinal.web.preprocessor.CFMenuPreprocessor.java
 * @author XChao
 */
public abstract class CFMenuPreprocessor extends CFDBPreprocessor {
	// 存放所有菜单信息
	private final Map<Integer, CFMenu> menuInfo = new HashMap<Integer, CFMenu>();
	// 存放所有角色与菜单信息的权限值
	private final Map<String, Integer> roleMenuValue = new HashMap<String, Integer>();

	/**
	 * 获取菜单信息
	 * @return
	 */
	public Map<Integer, CFMenu> getMenuInfo() {
		return this.menuInfo;
	}

	/**
	 * 根据菜单ID获取菜单信息
	 * @param menuId
	 * @return
	 */
	public CFMenu getMenuInfo(int menuId) {
		return this.menuInfo.get(menuId);
	}

	/**
	 * 在缓存中添加一个菜单信息
	 * @param menu
	 * @return
	 */
	public CFMenu addMenuInfo(CFMenu menu) {
		return this.menuInfo.put(menu.getId(), menu);
	}

	/**
	 * 根据菜单ID删除缓存中的菜单信息
	 * @param menuId
	 * @return
	 */
	public CFMenu removeMenuInfo(int menuId) {
		return this.menuInfo.remove(menuId);
	}

	/**
	 * 初始化菜单信息，
	 */
	public void initMenuInfo() {
		for (String actionPorxyName : getContext().getActionMap().keySet()) {
			CFActionPorxy actionPorxy = getContext().getAction(actionPorxyName);
			if(actionPorxy.getAction().menuId() > 0 && actionPorxy.getAction().permiss() > 0) {
				if(this.getMenuInfo(actionPorxy.getAction().menuId()) == null) {
					CFMenu menu = this.createMenu();
					menu.setId(actionPorxy.getAction().menuId());
					this.addMenuInfo(menu);
				}
				if(actionPorxy.getAction().isMenu()) {
					this.getMenuInfo(actionPorxy.getAction().menuId()).valueOf(actionPorxy.getAction());
					this.getMenuInfo(actionPorxy.getAction().menuId())
						.setUrl(actionPorxyName.replaceFirst(getContext().getContextPath() + "/", ""));
				} else if(StringUtils.isNotBlank(actionPorxy.getAction().menuName())) {
					Map<Integer, String> childs = this.getMenuInfo(actionPorxy.getAction().menuId()).getChilds();
					if(StringUtils.isNotBlank(childs.get(actionPorxy.getAction().permiss()))) {
						this.getMenuInfo(actionPorxy.getAction().menuId()).addChild(actionPorxy.getAction().permiss(),
							childs.get(actionPorxy.getAction().permiss()) + ", " + actionPorxy.getAction().menuName());
					} else {
						this.getMenuInfo(actionPorxy.getAction().menuId()).addChild(actionPorxy.getAction().permiss(),
							actionPorxy.getAction().menuName());
					}
				}
			}
		}
	}

	/**
	 * 入口方法
	 */
	public void dbProcess() {
		// 初始化菜单
		this.initMenuInfo();
		// 设置所有的角色菜单权限值
		this.setAllRoleMenuValue();
		// 调用初始化程序
		this.menuProcess();
	}

	/**
	 * 设置所有的角色菜单权限值
	 * @param dao
	 */
	public void setAllRoleMenuValue() {
		synchronized (this.roleMenuValue) {
			this.roleMenuValue.clear();
			List<CFRoleMenu> roleMenuList = this.findRoleMenu();
			for (CFRoleMenu roleMenu : roleMenuList) {
				StringBuilder key = new StringBuilder(String.valueOf(roleMenu.getRoleId()));
				key.append("_").append(roleMenu.getMenuId());
				this.roleMenuValue.put(key.toString(), roleMenu.getValue());
			}
		}
	}

	/**
	 * 根据角色ID和菜单ID获取该角色操作该菜单和权限码
	 * @param roles 角色列表
	 * @param menuId 菜单ID
	 */
	public Integer getRoleMenuValue(List<CFRole> roles, int menuId) {
		synchronized (this.roleMenuValue) {
			int result = 0;
			for (CFRole role : roles) {
				Integer value = this.roleMenuValue.get(role.getId() + "_" + menuId);
				if(value != null && value > 0) {
					result = result | value;
				}
			}
			return result;
		}
	}

	/**
	 * 查询当前系统的所有菜单
	 * @return
	 */
	public List<CFMenu> findMenu() {
		List<CFMenu> menuList = new ArrayList<>();
		for (Integer menuId : this.menuInfo.keySet()) {
			menuList.add(this.menuInfo.get(menuId));
		}
		menuList.sort((menu1, menu2) -> {
			return menu1.getId() - menu2.getId();
		});
		return menuList;
	}

	/**
	 * 根据用户的所有角色和菜单分类， 查询该分类下的所有菜单
	 * @param roles
	 * @param type
	 * @return
	 */
	public List<CFMenu> findMenu(List<CFRole> roles, int type) {
		List<CFMenu> menuList = new ArrayList<>();
		for (CFMenu menu : this.findMenu()) {
			Integer value = this.getRoleMenuValue(roles, menu.getId());
			if(value != null && value != 0 && menu.getType() == type) {
				menuList.add(menu);
			}
		}
		return menuList;
	}

	/**
	 * 根据用户的所有角色查询该用户所有可操作的菜单
	 * @param roles
	 * @param type
	 * @return
	 */
	public List<CFMenu> findMenu(List<CFRole> roles) {
		List<CFMenu> menuList = new ArrayList<>();
		for (CFMenu menu : this.findMenu()) {
			Integer value = this.getRoleMenuValue(roles, menu.getId());
			if(value != null && value != 0) {
				menuList.add(menu);
			}
		}
		return menuList;
	}

	/**
	 * 查询所有角色与菜单关系
	 * @return
	 */
	public abstract List<CFRoleMenu> findRoleMenu();

	/**
	 * 创建菜单实例
	 * @return
	 */
	protected abstract CFMenu createMenu();

	/**
	 * 初始化执行方法
	 */
	protected abstract void menuProcess();

}
