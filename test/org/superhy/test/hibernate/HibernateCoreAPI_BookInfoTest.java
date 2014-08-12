package org.superhy.test.hibernate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.superhy.model.BookInfo;
import org.superhy.model.UserInfo;
import org.superhy.util.HibernateUtil;

public class HibernateCoreAPI_BookInfoTest {

	@Test
	public void testAdd() {
		/*
		 * 定义实例化对象，并设定好数据，注意：由于anthor_id是外键，需要先根据关联表的字段设定关联表信息
		 */
		BookInfo book = new BookInfo();
		UserInfo user = new UserInfo();
		user.setId(2);

		book.setName("Lucene实战");
		book.setUserInfo(user);
		book.setPublish("电子工业出版社");
		book.setPublishtime(Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-mm-dd hh:mm:ss").format(new Date())));

		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();

		session.beginTransaction();
		session.save(book);
		session.getTransaction().commit();
	}
}
