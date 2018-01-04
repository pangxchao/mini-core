/**
 * Created the sn.mini.web.listener.MenuServletContextListener.java
 * @created 2017年11月17日 下午3:34:59
 * @version 1.0.0
 */
package sn.mini.web.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.servlet.ServletContextEvent;

import sn.mini.util.lang.MapUtil;
import sn.mini.util.lang.StringUtil;
import sn.mini.web.SNInitializer;
import sn.mini.web.annotaion.Menu;
import sn.mini.web.http.ActionProxy;
import sn.mini.web.util.IMenu;
import sn.mini.web.util.IRole;
import sn.mini.web.util.IRoleMenu;

/**
 * sn.mini.web.listener.MenuServletContextListener.java
 * @author XChao
 */
public abstract class MenuServletContextListener extends DaoServletContextListener {
	// 存放所有菜单信息
	private final Map<Integer, IMenu> menuInfo = new HashMap<Integer, IMenu>();
	// 存放所有角色与菜单信息的权限值
	private final Map<String, Integer> roleMenuValue = new HashMap<String, Integer>();

	/**
	 * 获取菜单信息
	 * @return
	 */
	public Map<Integer, IMenu> getMenuInfo() {
		return this.menuInfo;
	}

	/**
	 * 根据菜单ID获取菜单信息
	 * @param menuId
	 * @return
	 */
	public IMenu getMenuInfo(int menuId) {
		return this.menuInfo.get(menuId);
	}

	/**
	 * 在缓存中添加一个菜单信息
	 * @param menu
	 * @return
	 */
	public IMenu addMenuInfo(IMenu menu) {
		return this.menuInfo.put(menu.getId(), menu);
	}

	/**
	 * 根据菜单ID删除缓存中的菜单信息
	 * @param menuId
	 * @return
	 */
	public IMenu removeMenuInfo(int menuId) {
		return this.menuInfo.remove(menuId);
	}

	/**
	 * 初始化菜单信息，
	 */
	public void initMenuInfo() {
		for (Entry<String, ActionProxy> entry : SNInitializer.getActionProxys().entrySet()) {
			Optional.ofNullable(entry.getValue().getMethod().getAnnotation(Menu.class)).ifPresent(v -> {
				IMenu menu = MapUtil.getOrDefaultAndPut(this.menuInfo, v.id(), () -> {
					IMenu result = this.createMenu().valueOf(v);
					this.menuInfo.put(result.getId(), result);
					return result;
				});
				if(v.is()) {
					menu.valueOf(v).setUrl(entry.getKey());
				} else if(StringUtil.isNotBlank(v.name())) {
					Map<Integer, String> childs = menu.getChilds();
					if(StringUtil.isNotBlank(childs.get(v.value()))) {
						menu.addChild(v.value(), childs.get(v.value()) + ", " + v.name());
					} else {
						menu.addChild(v.value(), v.name());
					}
				}

			});
		}
	}

	/**
	 * 查询所有角色与菜单关系
	 * @return
	 */
	public abstract List<IRoleMenu> findRoleMenu();

	/**
	 * 创建菜单实例
	 * @return
	 */
	protected abstract IMenu createMenu();

	/**
	 * 初始化执行方法
	 */
	protected abstract void menuContextInitialized(ServletContextEvent event);

	@Override
	public final void dbContextInitialized(ServletContextEvent event) {
		this.initMenuInfo(); // 初始化菜单
		this.setAllRoleMenuValue(); // 设置所有的角色菜单权限值
		this.menuContextInitialized(event); // 调用初始化程序
	}

	/**
	 * 设置所有的角色菜单权限值
	 * @param dao
	 */
	public void setAllRoleMenuValue() {
		synchronized (this.roleMenuValue) {
			this.roleMenuValue.clear();
			List<IRoleMenu> roleMenuList = this.findRoleMenu();
			for (IRoleMenu roleMenu : roleMenuList) {
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
	public Integer getRoleMenuValue(List<IRole> roles, int menuId) {
		synchronized (this.roleMenuValue) {
			int result = 0;
			for (IRole role : roles) {
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
	public List<IMenu> findMenu() {
		List<IMenu> menuList = new ArrayList<>();
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
	public List<IMenu> findMenu(List<IRole> roles, int type) {
		List<IMenu> menuList = new ArrayList<>();
		for (IMenu menu : this.findMenu()) {
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
	public List<IMenu> findMenu(List<IRole> roles) {
		List<IMenu> menuList = new ArrayList<>();
		for (IMenu menu : this.findMenu()) {
			Integer value = this.getRoleMenuValue(roles, menu.getId());
			if(value != null && value != 0) {
				menuList.add(menu);
			}
		}
		return menuList;
	}

}
