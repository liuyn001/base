package lyn.ssh.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import lyn.ssh.pageModel.Menu;
import lyn.ssh.service.MenuServiceI;

@Namespace("/")
@Action("menuAction")
public class MenuAction extends BaseAction implements ModelDriven<Menu> {

	@Autowired
	private MenuServiceI menuService;

	private Menu menu = new Menu();

	@Override
	public Menu getModel() {
		return menu;
	}

	/**
	 * 
	 * @Title: getTreeNode
	 * @Description: 异步获取树节点
	 * @param 无
	 * @return void 返回类型
	 * @throws
	 */
	public void getTreeNode() {
		super.writeJson(menuService.getTreeNode(menu.getId()));
	}

	/**
	 * 
	 * @Title: getAllTreeNode
	 * @Description: 同步树获取(一次性返回所有的树节点)
	 * @param 无
	 * @return void 返回类型
	 * @throws
	 */
	public void getAllTreeNode() {
		super.writeJson(menuService.getAllTreeNode());
	}
}
