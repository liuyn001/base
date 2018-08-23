package lyn.ssh.dao.impl;


import org.springframework.stereotype.Repository;

import lyn.ssh.dao.UserDaoI;
import lyn.ssh.model.Tuser;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<Tuser> implements UserDaoI {



}
