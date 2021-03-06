package org.superhy.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.superhy.dao.UserInfoDao;
import org.superhy.model.UserInfo;

@Service("userInfoService")
public class UserInfoService {

	private UserInfoDao userInfoDao;

	public UserInfoDao getUserInfoDao() {
		return userInfoDao;
	}

	@Resource(name = "userInfoHibernateDao")
	public void setUserInfoDao(UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	public void add(UserInfo user) {
		userInfoDao.saveUser(user);
	}
}
