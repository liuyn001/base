package lyn.ssh.service;

import java.util.List;

import lyn.ssh.pageModel.Menu;

public interface MenuServiceI {

	public List<Menu> getTreeNode(String id);

	public List<Menu> getAllTreeNode();
}
