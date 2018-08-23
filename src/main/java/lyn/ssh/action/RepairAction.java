package lyn.ssh.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import lyn.ssh.service.RepairServiceI;

@Namespace("/")
@Action("repairAction")
public class RepairAction extends BaseAction {
	
	@Autowired
	private RepairServiceI repairService;

	public void init(){
		repairService.repair();
	}
	
}
