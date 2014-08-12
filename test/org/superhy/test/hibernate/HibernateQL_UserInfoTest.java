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
	 * 单表查询（sql:select * from user_info）
	 */
	@Test
	public void testHQL_query() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		// 创建HQL语句
		Query q = session.createQuery("from UserInfo");

		// 取出查询结果，需要强制转换类型
		List<UserInfo> users = (List<UserInfo>) q.list();
		for (UserInfo user : users) {
			System.out.println(user.getName());
		}

		session.getTransaction().commit();
	}

	/**
	 * 单表条件查询（sql:select * from user_info where name=?）
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
	 * 单表降序查询（sql:select * from user_info order by name desc）
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
	 * 限制重复单表查询（sql:select distinct name from user_info）
	 */
	@Test
	public void testHQL_query_distinct() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		/*
		 * 表示去掉重复的字段，但是如果全部字段查询的话，相当于distinct id，有和没有都是一个结果。
		 */
		Query q = session.createQuery("select distinct u.name from UserInfo u");

		List<String> usersName = (List<String>) q.list();
		for (String userName : usersName) {
			System.out.println(userName);
		}

		session.getTransaction().commit();
	}

	/**
	 * 大于小于符号限制单表查询（sql:select * from user_info where id > ? and id <= ?）
	 */
	@Test
	public void testHQL_query_max_and_min() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		/*
		 * 链式编程设置最大值和最小值，注意：不要忘了占位符
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
	 * 查询实现分页（mysql:limit）
	 */
	@Test
	public void testHQL_query_page() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		// 设置开始字段和分页所取得的字段数，从0开始是第一个字段
		Query q = session.createQuery("from UserInfo").setFirstResult(0)
				.setMaxResults(2);

		List<UserInfo> users = (List<UserInfo>) q.list();
		for (UserInfo user : users) {
			System.out.println(user.getId() + ":" + user.getName());
		}

		session.getTransaction().commit();
	}

	/**
	 * 单表查询部分字段（sql:select name, weight from user_info）
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
	 * 关联查询， fetch type设定为lazy将会设定为延迟加载
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
	 * 关联部分字段查询，内联查询（mysql:inner join）
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
	 * 只返回单独的一个结果，不需要遍历list
	 */
	@Test
	public void testHQL_query_uniqueResult() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		Query q = session
				.createQuery("from UserInfo u where u = :UserToSearch");

		// 设置通配符对应的要查询的实体
		UserInfo user = new UserInfo();
		user.setId(1);
		q.setParameter("UserToSearch", user);

		UserInfo userRes = (UserInfo) q.uniqueResult();
		System.out.println(userRes.getName());

		session.getTransaction().commit();
	}

	/**
	 * 字段数查询（sql:select count(*) from user_info）
	 */
	@Test
	public void testHQL_query_count() {
		SessionFactory sf = HibernateUtil.getSessionFactory();

		Session session = sf.getCurrentSession();
		session.beginTransaction();

		Query q = session.createQuery("select count(*) from UserInfo");

		// 必须是长整型
		long count = (Long) q.uniqueResult();
		System.out.println(count);

		session.getTransaction().commit();
	}

	/**
	 * 查询求字段最大值，最小值，平均值，和
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
	 * 查询在某一范围内的字段
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
	 * 给定值集合查询
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
	 * 查询某值不为空的所有字段
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
