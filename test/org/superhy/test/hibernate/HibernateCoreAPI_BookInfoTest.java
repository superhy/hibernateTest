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
		 * ����ʵ�������󣬲��趨�����ݣ�ע�⣺����anthor_id���������Ҫ�ȸ��ݹ�������ֶ��趨��������Ϣ
		 */
		BookInfo book = new BookInfo();
		UserInfo user = new UserInfo();
		user.setId(2);

		book.setName("Luceneʵս");
		book.setUserInfo(user);
		book.setPublish("���ӹ�ҵ������");
		book.setPublishtime(Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-mm-dd hh:mm:ss").format(new Date())));

		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();

		session.beginTransaction();
		session.save(book);
		session.getTransaction().commit();
	}
}
