package lyn.ssh.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ModelDriven;

import lyn.ssh.pageModel.Json;
import lyn.ssh.pageModel.User;
import lyn.ssh.service.UserServiceI;

/* 老方法注解加xml配置
 @ParentPackage("basePackage")
 @Namespace("/")
 public class UserAction {

 private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
 @Action(value="userTestAction")
 public void test(){
 logger.info("进入action");
 }

 @Action(value="userTest2Action")
 public void test2(){
 logger.info("进入action");
 }
 }
 */

/*
 * 动态方法注解 访问 userAction!test.action
 */

/**
 * 
 * @ClassName: UserAction 
 * @Description: 用户相关操作 
 * @author A18ccms a18ccms_gmail_com 
 * @date 2016-8-10 下午4:49:30
 */
@Namespace("/")
@Action(value = "userAction")
public class UserAction extends BaseAction implements ModelDriven<User>, ServletRequestAware {

	private static final Logger logger = Logger.getLogger(UserAction.class);
	@Autowired
	private UserServiceI userService;

	private User user = new User();

	HttpServletRequest request;
	
	
	@Override
	public User getModel() {
		return user;
	}

	/**
	 * 
	 * @Title: reg 
	 * @Description: 注册方法 
	 * @param  设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	public void reg() {
		Json j = new Json();
		try {
			userService.save(user);
			j.setSuccess(true);
			j.setMsg("注册成功！");

		} catch (Exception e) {
			e.printStackTrace();
			j.setMsg(e.getMessage());
			logger.error(e.getMessage());
		}

		super.writeJson(j);
	}

	/**
	 * 
	 * @Title: login 
	 * @Description: 登录方法
	 * @param  设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	
	public void login() {
		Json j = new Json();
		User u = userService.login(user);
		if (u != null) {
			HttpSession session = ServletActionContext.getRequest().getSession();
			session.setAttribute("username", u.getName());
			j.setSuccess(true);
			j.setMsg("登录成功!");
		} else {
			j.setMsg("登录失败,用户名或密码错误!");
		}
		super.writeJson(j);
	}
	
	/**
	 * 
	 * @Title: logout 
	 * @Description: TODO 退出操作
	 * @param  参数 
	 * @return void 返回类型 
	 * @author lyn 
	 * @date 2017年6月11日 下午1:47:58 
	 * @throws
	 */
	public void logout(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.invalidate();
		Json j = new Json();
		j.setSuccess(true);
		j.setMsg("退成成功!");
		super.writeJson(j);
	}
	
	/**
	 * 
	 * @Title: getAllUser 
	 * @Description: TODO 获取所有的用户
	 * @param  设定文件 
	 * @return void 返回类型 
	 * @author lyn 
	 * @date 2017年6月9日 下午2:19:54 
	 * @throws
	 */
	public void getAllUser(){
		super.writeJson(userService.getUsersForList());
		
	}
	
	/**
	 * 
	 * @Title: editUser 
	 * @Description: TODO 修改用户操作
	 * @param  参数 
	 * @return void 返回类型 
	 * @author lyn 
	 * @date 2017年6月9日 下午2:22:07 
	 * @throws
	 */
	public void editUser(){
		Json j = new Json();
		String id = request.getParameter("userid");
		if(null!=id && !"".equals(id)){
			userService.updateUser(id, user);
			j.setSuccess(true);
			j.setMsg("修改成功!");
		} else {
			j.setSuccess(true);
			j.setMsg("修改失败!");
		}
		super.writeJson(j);
	}

	/**
	 * 
	 * @Title: deleteUser 
	 * @Description: TODO 删除用户
	 * @param  参数 
	 * @return void 返回类型 
	 * @author lyn 
	 * @date 2017年6月9日 下午5:38:20 
	 * @throws
	 */
	public void deleteUser(){
		Json j = new Json();
		String id = request.getParameter("userid");
		if(null!=id && !"".equals(id)){
			userService.deleteUser(id);
			j.setSuccess(true);
			j.setMsg("删除成功!");
		} else {
			j.setSuccess(true);
			j.setMsg("删除失败!");
		}
		super.writeJson(j);
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}
