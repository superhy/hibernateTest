package org.superhy.test.spring;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.superhy.model.UserInfo;
import org.superhy.service.UserInfoService;

public class UserInfoServiceTest {

	@Test
	public void testAdd() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"applicationContext.xml");

		UserInfoService serviceTest = (UserInfoService) appContext
				.getBean("userInfoService");

		UserInfo user = new UserInfo();
		user.setName("YangPeng");
		user.setPass(Integer.toString((int) (Math.random() * 999999))); // 随机生成一个六位数字符串
		user.setWeight(57.5);
		user.setBirth(Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-mm-dd hh:mm:ss").format(new Date())));

		serviceTest.add(user);
	}
}
