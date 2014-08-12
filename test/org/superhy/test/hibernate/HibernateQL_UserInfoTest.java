package org.superhy.test.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.superhy.model.BookInfo;
import org.superhy.model.UserInfo;
import org.superhy.util.HibernateUtil;

public class HibernateQL_UserInfoTest {

	/**
	 * �����ѯ��sql:select * from user_info��
	 */
	@Test
	public void testHQL_query() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		// ����HQL���
		Query q = session.createQuery("from UserInfo");

		// ȡ����ѯ�������Ҫǿ��ת������
		List<UserInfo> users = (List<UserInfo>) q.list();
		for (UserInfo user : users) {
			System.out.println(user.getName());
		}

		session.getTransaction().commit();
	}

	/**
	 * ����������ѯ��sql:select * from user_info where name=?��
	 */
	@Test
	public void testHQL_query_where() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		Query q = session.createQuery("from UserInfo where name = 'DaiDan'");

		List<UserInfo> users = (List<UserInfo>) q.list();
		for (UserInfo user : users) {
			System.out.println(user.getBirth());
		}

		session.getTransaction().commit();
	}

	/**
	 * �������ѯ��sql:select * from user_info order by name desc��
	 */
	@Test
	public void testHQL_query_orderBy() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		Query q = session.createQuery("from UserInfo u order by u.id desc");

		List<UserInfo> users = (List<UserInfo>) q.list();
		for (UserInfo user : users) {
			System.out.println(user.getName());
		}

		session.getTransaction().commit();
	}

	/**
	 * �����ظ������ѯ��sql:select distinct name from user_info��
	 */
	@Test
	public void testHQL_query_distinct() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		/*
		 * ��ʾȥ���ظ����ֶΣ��������ȫ���ֶβ�ѯ�Ļ����൱��distinct id���к�û�ж���һ�������
		 */
		Query q = session.createQuery("select distinct u.name from UserInfo u");

		List<String> usersName = (List<String>) q.list();
		for (String userName : usersName) {
			System.out.println(userName);
		}

		session.getTransaction().commit();
	}

	/**
	 * ����С�ڷ������Ƶ����ѯ��sql:select * from user_info where id > ? and id <= ?��
	 */
	@Test
	public void testHQL_query_max_and_min() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		/*
		 * ��ʽ����������ֵ����Сֵ��ע�⣺��Ҫ����ռλ��
		 */
		Query q = session.createQuery(
				"from UserInfo u where u.id > :min and u.id <= :max")
				.setInteger("min", 1).setInteger("max", 3);

		List<UserInfo> users = (List<UserInfo>) q.list();
		for (UserInfo user : users) {
			System.out.println(user.getId() + ":" + user.getName());
		}

		session.getTransaction().commit();
	}

	/**
	 * ��ѯʵ�ַ�ҳ��mysql:limit��
	 */
	@Test
	public void testHQL_query_page() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		// ���ÿ�ʼ�ֶκͷ�ҳ��ȡ�õ��ֶ�������0��ʼ�ǵ�һ���ֶ�
		Query q = session.createQuery("from UserInfo").setFirstResult(0)
				.setMaxResults(2);

		List<UserInfo> users = (List<UserInfo>) q.list();
		for (UserInfo user : users) {
			System.out.println(user.getId() + ":" + user.getName());
		}

		session.getTransaction().commit();
	}

	/**
	 * �����ѯ�����ֶΣ�sql:select name, weight from user_info��
	 */
	@Test
	public void testHQL_partquery() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		Query q = session
				.createQuery("select u.name, u.weight from UserInfo u");

		List<Object[]> usersPart = (List<Object[]>) q.list();
		for (Object[] userPart : usersPart) {
			System.out.println(userPart[0] + ":" + userPart[1]);
		}

		session.getTransaction().commit();
	}

	/**
	 * ������ѯ�� fetch type�趨Ϊlazy�����趨Ϊ�ӳټ���
	 */
	@Test
	public void testHQL_joinquery() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		Query q = session
				.createQuery("from BookInfo b where b.userInfo.name = 'DaiDan'");
		List<BookInfo> books = (List<BookInfo>) q.list();
		for (BookInfo book : books) {
			System.out.println(book.getName() + ", " + book.getPublish() + ", "
					+ book.getPublishtime());
		}

		session.getTransaction().commit();
	}

	/**
	 * ���������ֶβ�ѯ��������ѯ��mysql:inner join��
	 */
	@Test
	public void testHQL_joinquery_inner_part() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		Query q = session
				.createQuery("select u.name, b.name from UserInfo u join u.bookInfos b");

		List<Object[]> booksPart = (List<Object[]>) q.list();
		for (Object[] bookPart : booksPart) {
			System.out.println(bookPart[0] + ", " + bookPart[1]);
		}

		session.getTransaction().commit();
	}

	/**
	 * ֻ���ص�����һ�����������Ҫ����list
	 */
	@Test
	public void testHQL_query_uniqueResult() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		Query q = session
				.createQuery("from UserInfo u where u = :UserToSearch");

		// ����ͨ�����Ӧ��Ҫ��ѯ��ʵ��
		UserInfo user = new UserInfo();
		user.setId(1);
		q.setParameter("UserToSearch", user);

		UserInfo userRes = (UserInfo) q.uniqueResult();
		System.out.println(userRes.getName());

		session.getTransaction().commit();
	}

	/**
	 * �ֶ�����ѯ��sql:select count(*) from user_info��
	 */
	@Test
	public void testHQL_query_count() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		Query q = session.createQuery("select count(*) from UserInfo");

		// �����ǳ�����
		long count = (Long) q.uniqueResult();
		System.out.println(count);

		session.getTransaction().commit();
	}

	/**
	 * ��ѯ���ֶ����ֵ����Сֵ��ƽ��ֵ����
	 */
	@Test
	public void testHQL_query_max_min_avg_sum() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		Query q = session
				.createQuery("select max(u.weight), min(u.weight), avg(u.weight), sum(u.weight) from UserInfo u");

		Object[] res = (Object[]) q.uniqueResult();
		System.out.println(res[0] + ", " + res[1] + ", " + res[2] + ", "
				+ res[3]);

		session.getTransaction().commit();
	}

	/**
	 * ��ѯ��ĳһ��Χ�ڵ��ֶ�
	 */
	@Test
	public void testHQL_query_between() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		Query q = session
				.createQuery("select u.name from UserInfo u where u.id between 1 and 3");

		List<String> usersName = (List<String>) q.list();
		for (String userName : usersName) {
			System.out.println(userName);
		}

		session.getTransaction().commit();
	}

	/**
	 * ����ֵ���ϲ�ѯ
	 */
	@Test
	public void testHQL_query_in() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		Query q = session.createQuery("from UserInfo u where u.id in (1, 2)");

		List<UserInfo> users = (List<UserInfo>) q.list();
		for (UserInfo user : users) {
			System.out.println(user.getName());
		}

		session.getTransaction().commit();
	}

	/**
	 * ��ѯĳֵ��Ϊ�յ������ֶ�
	 */
	@Test
	public void testHQL_query_isnotnull() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		Query q = session
				.createQuery("from UserInfo u where u.weight is not null");

		List<UserInfo> users = (List<UserInfo>) q.list();
		for (UserInfo user : users) {
			System.out.println(user.getName());
		}

		session.getTransaction().commit();
	}
}
