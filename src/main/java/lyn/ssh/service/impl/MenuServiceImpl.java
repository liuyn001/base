package lyn.ssh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lyn.ssh.dao.MenuDaoI;
import lyn.ssh.model.Tmenu;
import lyn.ssh.pageModel.Menu;
import lyn.ssh.service.MenuServiceI;

@Service("menuService")
public class MenuServiceImpl implements MenuServiceI {

	@Autowired
	private MenuDaoI menuDao;

	@Override
	public List<Menu> getTreeNode(String id) {
		List<Menu> nl = new ArrayList<Menu>();
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "";
		if (id == null || "".equals(id)) {
			hql = "from Tmenu t where t.tmenu is null";
		} else {
			hql = "from Tmenu t where t.tmenu.id = :id";
			params.put("id", id);
		}
		try {
			List<Tmenu> l = menuDao.find(hql, params);
			for (Tmenu t : l) {
				Menu m = new Menu();
				BeanUtils.copyProperties(t, m);
				if (t.getTmenus() != null && !t.getTmenus().isEmpty()) {
					m.setState("closed");// 节点以文件夹形式提现
				} else {
					m.setState("open");// 节点以文件形式提现
				}
				nl.add(m);
			}
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nl;
	}

	@Override
	public List<Menu> getAllTreeNode() {
		List<Menu> nl = new ArrayList<Menu>();
		String hql = "from Tmenu t";
		try {
			List<Tmenu> l = menuDao.find(hql);
			for (Tmenu t : l) {
				Menu m = new Menu();
				BeanUtils.copyProperties(t, m);
				Map<String, Object> attributes = new HashMap<String, Object>();
				attributes.put("url", t.getUrl());
				m.setAttributes(attributes);
				Tmenu tm = t.getTmenu();// 获取父节点对象
				if (tm != null) {
					m.setPid(tm.getId());
				}
				nl.add(m);
			}
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nl;
	}

}
