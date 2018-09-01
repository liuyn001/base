package lyn.ssh.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lyn.redis.CacheUtil;
import lyn.redis.impl.RedisOperate;
import lyn.ssh.dao.UserDaoI;
import lyn.ssh.model.Tuser;
import lyn.ssh.pageModel.User;
import lyn.ssh.service.UserServiceI;
import lyn.ssh.service.lawyee.SynLawyerXsfbService;
import lyn.util.Encrypt;

/**
 * 
 * @ClassName: UserServiceImpl 
 * @Description: 用户相关的service 
 * @author A18ccms a18ccms_gmail_com 
 * @date 2016-8-10 下午4:50:25
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserServiceI {

	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private UserDaoI userDao;
	
	@Resource
    private RedisOperate redisOperate;
	
	@Autowired
	private CacheUtil cacheUtil;
	
	@Autowired
	private SynLawyerXsfbService synLawyerXsfbService;
	
	@Override
	public void save(User user) {
		try {
			Tuser t = new Tuser();
			/*
			 * t.setName(user.getName()); t.setPassword(user.getPassword());
			 */
			// BeanUtils.copyProperties(user, t, new String[]{"id","createtime"});字符串数组传入刨除的copy的字段
			BeanUtils.copyProperties(user, t, new String[] { "password" });
			t.setPassword(Encrypt.e(user.getPassword()));
			t.setId(UUID.randomUUID().toString().replaceAll("-", ""));
			t.setCreatetime(new Date());
			userDao.save(t);
			logger.info("将要保存的人员信息:" + t.getName());
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public User login(User user) {
		// Tuser t = userDao.get("from Tuser t where t.name = '"+user.getName()+"' and t.password = '"+Encrypt.e(user.getPassword())+"'");
		// Tuser t = userDao.get("from Tuser t where t.name = ? and t.password = ?", new Object[]{user.getName(),Encrypt.e(user.getPassword())});
		try {
			
			/*Object registerLawyer = synLawyerXsfbService.registerLawyer();
			System.out.println("registerLawyer:"+registerLawyer);
			
			redisOperate.set("ee", "3333", 0);
			String string1 = redisOperate.get("ee");
			cacheUtil.put("zzz", "aaaa");
			String string = cacheUtil.get("zzz");
			System.out.println(string+string1);*/
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("name", user.getName());
			params.put("password", Encrypt.e(user.getPassword()));
			Tuser t = userDao.get("from Tuser t where t.name = :name and t.password = :password", params);
			if (t != null) {
				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> getUsersForList() {
		List<User> ul = new ArrayList<User>();
		try {
			List<Tuser> tuser = userDao.find("from Tuser t ");
			for(Tuser t : tuser){
				User u = new User();
				BeanUtils.copyProperties(t, u);
				ul.add(u);
			}
		} catch (BeansException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ul;
	}

	@Override
	public void updateUser(String id,User user) {
		
		if(null != user){
			try {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("id", id);
				Tuser t = userDao.get("from Tuser t where t.id = :id", params);
				//BeanUtils.copyProperties(user, t, new String[] { "createtime","password" });//创建时间不变，密码需要加密，所以这两个字段不copy
				if (t != null) {
					t.setPassword(Encrypt.e(user.getPassword()));
					t.setName(user.getName());
					t.setUpdatetime(new Date());
					logger.info("将要更新的人员信息:" + t.getName());
					userDao.update(t);
				}
			} catch (BeansException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public void deleteUser(String id) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			Tuser t = userDao.get("from Tuser t where t.id = :id", params);
			if(null!=t){
				logger.info("将要删除的人员信息:" + t.getName());
				userDao.delete(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
