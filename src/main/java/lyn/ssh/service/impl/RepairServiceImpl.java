package lyn.ssh.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lyn.ssh.dao.MenuDaoI;
import lyn.ssh.dao.UserDaoI;
import lyn.ssh.model.Tmenu;
import lyn.ssh.model.Tuser;
import lyn.ssh.service.RepairServiceI;
import lyn.util.Encrypt;

@Service("repairService")
public class RepairServiceImpl implements RepairServiceI {

	@Autowired
	private MenuDaoI menuDao;
	@Autowired
	private UserDaoI userDao;

	@Override
	public void repair() {
		repairUser();
		repairMenu();
	}

	private void repairMenu() {
		try {
			Tmenu root = new Tmenu();
			root.setId("0");
			root.setText("首页");
			root.setUrl("");
			menuDao.saveOrUpdate(root);
			
			Tmenu xtgl = new Tmenu();
			xtgl.setId("xtgl");
			xtgl.setTmenu(root);
			xtgl.setText("系统管理");
			xtgl.setUrl("");
			menuDao.saveOrUpdate(xtgl);
			
			Tmenu yhgl = new Tmenu();
			yhgl.setId("yhgl");
			yhgl.setTmenu(xtgl);
			yhgl.setText("用户管理");
			yhgl.setUrl("/admin/yhgl.jsp");
			menuDao.saveOrUpdate(yhgl);
			
			Tmenu jsgl = new Tmenu();
			jsgl.setId("jsgl");
			jsgl.setTmenu(xtgl);
			jsgl.setText("角色管理");
			jsgl.setUrl("");
			menuDao.saveOrUpdate(jsgl);
			
			Tmenu qxgl = new Tmenu();
			qxgl.setId("qxgl");
			qxgl.setTmenu(xtgl);
			qxgl.setText("权限管理");
			qxgl.setUrl("");
			menuDao.saveOrUpdate(qxgl);
			
			Tmenu cdgl = new Tmenu();
			cdgl.setId("cdgl");
			cdgl.setTmenu(xtgl);
			cdgl.setText("菜单管理");
			cdgl.setUrl("");
			menuDao.saveOrUpdate(cdgl);
			
			Tmenu buggl = new Tmenu();
			buggl.setId("buggl");
			buggl.setTmenu(xtgl);
			buggl.setText("BUG管理");
			buggl.setUrl("");
			menuDao.saveOrUpdate(buggl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void repairUser() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", "admin");
			Tuser t = userDao.get("from Tuser t where t.name = :name and t.id !='0'", map);
			if(t!=null){
				t.setName(UUID.randomUUID().toString().replaceAll("-", ""));
			}
			Tuser admin = new Tuser();
			admin.setId("0");
			admin.setName("admin");
			admin.setPassword(Encrypt.e("admin"));
			admin.setUpdatetime(new Date());
			userDao.saveOrUpdate(admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
