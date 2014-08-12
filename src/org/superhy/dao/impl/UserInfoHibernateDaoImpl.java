package org.superhy.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.mapping.PrimaryKey;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;
import org.superhy.dao.UserInfoDao;
import org.superhy.model.UserInfo;

import com.mysql.jdbc.Connection;

@Component("userInfoHibernateDao")
public class UserInfoHibernateDaoImpl implements UserInfoDao {

	/*private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Resource(name = "sessionFactory")
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}*/
	
	// 使用hibernate模版去执行操作
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHierHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource(name = "hibernateTemplate")
	public void setHierHibernateTemplate(HibernateTemplate hierHibernateTemplate) {
		this.hibernateTemplate = hierHibernateTemplate;
	}

	public void saveUser(UserInfo user) {

		hibernateTemplate.save(user);
	}

}
