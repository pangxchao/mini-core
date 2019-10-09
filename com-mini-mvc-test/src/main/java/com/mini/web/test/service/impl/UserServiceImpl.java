package com.mini.web.test.service.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.mini.web.test.dao.UserDao;
import com.mini.web.test.service.UserService;

/**
 * UserServiceImpl.java
 * @author xchao
 */
@Singleton
@Named("userService")
public class UserServiceImpl implements UserService {
	private UserDao userDao;

	@Inject
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDao getUserDao() {
		return userDao;
	}
}
