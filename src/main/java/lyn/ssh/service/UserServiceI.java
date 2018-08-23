package lyn.ssh.service;

import java.util.List;

import lyn.ssh.pageModel.User;

public interface UserServiceI {

	public void save(User user);

	public User login(User user);
	
	public List<User> getUsersForList();
	
	public void updateUser(String id,User user);

	public void deleteUser(String id);
}
