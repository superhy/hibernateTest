package org.superhy.test.hibernate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;
import org.superhy.model.UserInfo;
import org.superhy.util.HibernateUtil;

import junit.framework.TestCase;

public class HibernateCoreAPI_UserInfoTest extends TestCase {

	/**
	 * 测试方法必须以test为开头
	 */
	@Test
	public void testAdd() {
		// 定义实例化对象，并设定好数据
		UserInfo user = new UserInfo();
		user.setName("DaiDan");
		user.setPass("123456");
		user.setWeight(48.5);
		user.setBirth(Timestamp.valueOf("1992-06-16 00:00:00"));

		/*
		 * 从配置文件中取出数据库信息，建立session工厂，并打开session工厂，
		 * 注意：如果是注解式的需要调用AnnotationConfiguration构造方法
		 * ，如果是使用xml配置文件，则使用Configuration构造方法， session工厂从辅助方法HibernateUtil中获取.
		 */
		SessionFactory sf = HibernateUtil.getSessionFactory();

		/*
		 * 每次获取已有session，需在cfg.xml中加上current_session_context_class配置，
		 * tomcat使用属性值thread ，如果使用jboss，weblogic等就使用属性值jta。
		 */
		Session session = sf.getCurrentSession();

		// 打开事务进程，存入数据，提交操作
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();

		// 关闭各种连接，用getCurrentSession方法的话就不用关闭连接了，会自动关闭
		/*
		 * session.close(); sf.close();
		 */
	}

	@Test
	public void testDelete() {
		UserInfo user = new UserInfo();
		user.setId(2);

		/*
		 * 根据上面设置的ID删除对应的字段，除了delete语句，其他的部分基本一样。
		 */
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.getCurrentSession();

		/*
		 * 在注解部分删除nullable的字段，否则只设置对应ID无法正常删除，这样操作只是删除Hibernate对字段非空的限制，
		 * 并不会改变数据库的原有配置。
		 */
		session.beginTransaction();
		session.delete(user);
		session.getTransaction().commit();
	}

	@Test
	public void testLoad() {

		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.getCurrentSession();

		/*
		 * 从数据库中加载一个指定的字段到实体变量中。
		 */
		session.beginTransaction();
		UserInfo user = (UserInfo) session.load(UserInfo.class, 1);

		/*
		 * 测试输出，session.load方法只能在关闭session之前输出测试结果，load返回的只是代理对象，
		 * 真正需要用到sql请求的时候才会发出sql语句，load在执行这条语句的时候发出sql语句.
		 */
		System.out.println(user.getName());

		session.getTransaction().commit();
	}

	/**
	 * session.get方法与session.load方法的最重要的区别在于session.
	 * load方法在session关闭之后内存中的实体变量会销毁
	 * ，而session.get方法在session对象关闭之后，实体变量任然存在。load在需要发出sql语句的时候才会发出
	 * （打印时），而get在赋值的时候就发出sql语句了。
	 */
	@Test
	public void testGet() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.getCurrentSession();

		/*
		 * 从数据库中获取一个指定的字段到实体变量中。
		 */
		session.beginTransaction();
		/* 与上面一个方法基本相同，只是把load改成了get，get直接在本句发出sql语句，不会产生像load一样的延迟。 */
		UserInfo user = (UserInfo) session.get(UserInfo.class, 1);

		session.getTransaction().commit();

		// 测试输出
		System.out.println(user.getName());
	}

	/**
	 * 以下的三种更新由于技术原因都必须更新全部的字段。
	 */
	@Test
	public void testUpdateAll_01() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.getCurrentSession();

		/*
		 * 先取得要更新的字段.
		 */
		session.beginTransaction();
		UserInfo user = (UserInfo) session.get(UserInfo.class, 1);

		session.getTransaction().commit();

		/*
		 * 再设置要更新的键值,然后执行更新语句.
		 */
		user.setName("HuYang");

		Session sessionUpdateAll = sf.getCurrentSession();
		sessionUpdateAll.beginTransaction();
		sessionUpdateAll.update(user);

		sessionUpdateAll.getTransaction().commit();
	}

	@Test
	public void testUpdateAll_02() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session sessionUpdateAll = sf.getCurrentSession();

		UserInfo user = new UserInfo();

		/*
		 * 自己手动设定需要更新字段的ID也可以，总而言之就是数据库中必须有对应ID的字段, 而且设置的时候非空的字段不能为空，都得手动为其设置好。
		 */
		user.setId(1);
		user.setName("ZhouChao");
		user.setPass("888888");
		user.setWeight(65.0);
		user.setBirth(Timestamp.valueOf("1989-09-30 00:00:00"));

		sessionUpdateAll.beginTransaction();
		sessionUpdateAll.update(user);
		sessionUpdateAll.getTransaction().commit();
	}

	@Test
	public void testUpdateAll_03() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session sessionUpdateAll = sf.getCurrentSession();

		/*
		 * 当检查到某个字段的内容被改动时，会自动触发update语句，与前面相同，需要先获取需要修改的字段，
		 * 但是更新的时候内部还是会执行所有键值一起更新的操作，效率不高。
		 */
		sessionUpdateAll.beginTransaction();
		UserInfo user = (UserInfo) sessionUpdateAll.get(UserInfo.class, 1);
		user.setName("HuShaBi");

		sessionUpdateAll.getTransaction().commit();
	}

	/**
	 * 下面是对于部分字段的单独更新，不会更新全部的字段。有以下几种方式：
	 * 1.在注解或xml文件中设置字段的update和updatable属性值为false，使其不能够被更新，但是这种方法不灵活。
	 * 2.在xml配置文件class设置中添加属性dynamic-update(动态更新)，将其设置为true，但是在注解模式下不通用。
	 * 3.使用HQL(EJBQL)语句，下面就是这个方法的例子。
	 */
	@Test
	public void testUpdate_01() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session sessionUpdate = sf.getCurrentSession();

		sessionUpdate.beginTransaction();

		/*
		 * 建立一个Query对象，在里面使用Session对象引入HQL语句，然后使用Query对象执行该语句。注意：
		 * 这里的数据库表名必须使用hibernate实体类的类名，而不是数据库中的表名，Session对象会自动映射到数据库的对应表中去。
		 */
		Query q = sessionUpdate
				.createQuery("update UserInfo user set user.name = 'HuDaSha' where user.id = 1");
		q.executeUpdate();
		sessionUpdate.getTransaction().commit();
	}

	/**
	 * 意思就是有的时候执行save有的时候执行update
	 */
	@Test
	public void testSaveOrUpdate() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session sessionSaveOrUpdate_01 = sf.getCurrentSession();

		UserInfo user = new UserInfo();
		user.setName("YinEr");
		user.setPass("000000");
		user.setWeight(4.2);

		// 如果需要取得当前的时间，必须转换成标准的时间格式：[yyyy-mm-dd hh:mm:ss]
		user.setBirth(Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-mm-dd hh:mm:ss").format(new Date())));

		/*
		 * 在这种情况下，没有ID，肯定执行的是save。
		 */
		sessionSaveOrUpdate_01.beginTransaction();
		sessionSaveOrUpdate_01.saveOrUpdate(user);
		sessionSaveOrUpdate_01.getTransaction().commit();

		user.setName("DaBaoBei");

		/*
		 * 上面的实体对象还在，所以这时执行的是update操作。
		 */
		Session sessionSaveOrUpdate_02 = sf.getCurrentSession();

		sessionSaveOrUpdate_02.beginTransaction();
		sessionSaveOrUpdate_02.saveOrUpdate(user);
		sessionSaveOrUpdate_02.getTransaction().commit();
	}

	/**
	 * 此方法用于清空缓存中的Session执行结果
	 */
	@Test
	public void testClear() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.getCurrentSession();

		session.beginTransaction();

		UserInfo user_01 = (UserInfo) session.load(UserInfo.class, 1);
		System.out.println(user_01.getName());

		/*
		 * 清空缓存中的Session执行结果，否则的话，再次查询直接取出缓存中的结果，不用再次进行查询，清空之后会重新执行sql语句。
		 */
		session.clear();

		UserInfo user_02 = (UserInfo) session.load(UserInfo.class, 1);
		System.out.println(user_02.getName());
		session.getTransaction().commit();
	}

	/**
	 * 此方法用于强制与数据库内容做同步
	 */
	@Test
	public void testFlush() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.getCurrentSession();

		session.beginTransaction();

		UserInfo user = (UserInfo) session.load(UserInfo.class, 1);
		user.setName("shabi01");

		/*
		 * 强制使缓存的内容与数据库的内容做同步，默认只在提交的事后才同步，在commit()的时候，默认进行flush()，用于调节性能的时候使用。
		 */
		session.flush();

		user.setName("shabi02");

		session.getTransaction().commit();
	}

	/*
	 * find()方法已经过时
	 */

	/**
	 * 手动在数据库中根据Hibernate配置建表
	 */
	@Test
	public void testSchemaExport() {
		new SchemaExport(new AnnotationConfiguration().configure()).create(
				true, false);
	}
}
