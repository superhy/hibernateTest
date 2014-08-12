package org.superhy.dao.impl;

import org.superhy.dao.UserInfoDao;
import org.superhy.model.UserInfo;
import org.superhy.util.BasicDao;

public class UserInfoJdbcDaoImpl implements UserInfoDao {

	static BasicDao dbObj = new BasicDao();

	public void saveUser(UserInfo user) {
		String sqlInsert = "insert into user_info(name, pass, weight, birth) values('"
				+ user.getName()
				+ "', '"
				+ user.getPass()
				+ "', "
				+ Double.toString(user.getWeight())
				+ ", '"
				+ user.getBirth()
				+ "')";

		System.out.println(sqlInsert);

		dbObj.update(sqlInsert);

		// 关闭数据库连接
		dbObj.close();
	}
}
