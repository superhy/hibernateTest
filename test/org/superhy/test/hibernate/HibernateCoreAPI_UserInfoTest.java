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
	 * ���Է���������testΪ��ͷ
	 */
	@Test
	public void testAdd() {
		// ����ʵ�������󣬲��趨������
		UserInfo user = new UserInfo();
		user.setName("DaiDan");
		user.setPass("123456");
		user.setWeight(48.5);
		user.setBirth(Timestamp.valueOf("1992-06-16 00:00:00"));

		/*
		 * �������ļ���ȡ�����ݿ���Ϣ������session����������session������
		 * ע�⣺�����ע��ʽ����Ҫ����AnnotationConfiguration���췽��
		 * �������ʹ��xml�����ļ�����ʹ��Configuration���췽���� session�����Ӹ�������HibernateUtil�л�ȡ.
		 */
		SessionFactory sf = HibernateUtil.getSessionFactory();

		/*
		 * ÿ�λ�ȡ����session������cfg.xml�м���current_session_context_class���ã�
		 * tomcatʹ������ֵthread �����ʹ��jboss��weblogic�Ⱦ�ʹ������ֵjta��
		 */
		Session session = sf.getCurrentSession();

		// ��������̣��������ݣ��ύ����
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();

		// �رո������ӣ���getCurrentSession�����Ļ��Ͳ��ùر������ˣ����Զ��ر�
		/*
		 * session.close(); sf.close();
		 */
	}

	@Test
	public void testDelete() {
		UserInfo user = new UserInfo();
		user.setId(2);

		/*
		 * �����������õ�IDɾ����Ӧ���ֶΣ�����delete��䣬�����Ĳ��ֻ���һ����
		 */
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.getCurrentSession();

		/*
		 * ��ע�ⲿ��ɾ��nullable���ֶΣ�����ֻ���ö�ӦID�޷�����ɾ������������ֻ��ɾ��Hibernate���ֶηǿյ����ƣ�
		 * ������ı����ݿ��ԭ�����á�
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
		 * �����ݿ��м���һ��ָ�����ֶε�ʵ������С�
		 */
		session.beginTransaction();
		UserInfo user = (UserInfo) session.load(UserInfo.class, 1);

		/*
		 * ���������session.load����ֻ���ڹر�session֮ǰ������Խ����load���ص�ֻ�Ǵ������
		 * ������Ҫ�õ�sql�����ʱ��Żᷢ��sql��䣬load��ִ����������ʱ�򷢳�sql���.
		 */
		System.out.println(user.getName());

		session.getTransaction().commit();
	}

	/**
	 * session.get������session.load����������Ҫ����������session.
	 * load������session�ر�֮���ڴ��е�ʵ�����������
	 * ����session.get������session����ر�֮��ʵ�������Ȼ���ڡ�load����Ҫ����sql����ʱ��Żᷢ��
	 * ����ӡʱ������get�ڸ�ֵ��ʱ��ͷ���sql����ˡ�
	 */
	@Test
	public void testGet() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.getCurrentSession();

		/*
		 * �����ݿ��л�ȡһ��ָ�����ֶε�ʵ������С�
		 */
		session.beginTransaction();
		/* ������һ������������ͬ��ֻ�ǰ�load�ĳ���get��getֱ���ڱ��䷢��sql��䣬���������loadһ�����ӳ١� */
		UserInfo user = (UserInfo) session.get(UserInfo.class, 1);

		session.getTransaction().commit();

		// �������
		System.out.println(user.getName());
	}

	/**
	 * ���µ����ָ������ڼ���ԭ�򶼱������ȫ�����ֶΡ�
	 */
	@Test
	public void testUpdateAll_01() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.getCurrentSession();

		/*
		 * ��ȡ��Ҫ���µ��ֶ�.
		 */
		session.beginTransaction();
		UserInfo user = (UserInfo) session.get(UserInfo.class, 1);

		session.getTransaction().commit();

		/*
		 * ������Ҫ���µļ�ֵ,Ȼ��ִ�и������.
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
		 * �Լ��ֶ��趨��Ҫ�����ֶε�IDҲ���ԣ��ܶ���֮�������ݿ��б����ж�ӦID���ֶ�, �������õ�ʱ��ǿյ��ֶβ���Ϊ�գ������ֶ�Ϊ�����úá�
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
		 * ����鵽ĳ���ֶε����ݱ��Ķ�ʱ�����Զ�����update��䣬��ǰ����ͬ����Ҫ�Ȼ�ȡ��Ҫ�޸ĵ��ֶΣ�
		 * ���Ǹ��µ�ʱ���ڲ����ǻ�ִ�����м�ֵһ����µĲ�����Ч�ʲ��ߡ�
		 */
		sessionUpdateAll.beginTransaction();
		UserInfo user = (UserInfo) sessionUpdateAll.get(UserInfo.class, 1);
		user.setName("HuShaBi");

		sessionUpdateAll.getTransaction().commit();
	}

	/**
	 * �����Ƕ��ڲ����ֶεĵ������£��������ȫ�����ֶΡ������¼��ַ�ʽ��
	 * 1.��ע���xml�ļ��������ֶε�update��updatable����ֵΪfalse��ʹ�䲻�ܹ������£��������ַ�������
	 * 2.��xml�����ļ�class�������������dynamic-update(��̬����)����������Ϊtrue��������ע��ģʽ�²�ͨ�á�
	 * 3.ʹ��HQL(EJBQL)��䣬�������������������ӡ�
	 */
	@Test
	public void testUpdate_01() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session sessionUpdate = sf.getCurrentSession();

		sessionUpdate.beginTransaction();

		/*
		 * ����һ��Query����������ʹ��Session��������HQL��䣬Ȼ��ʹ��Query����ִ�и���䡣ע�⣺
		 * ��������ݿ��������ʹ��hibernateʵ��������������������ݿ��еı�����Session������Զ�ӳ�䵽���ݿ�Ķ�Ӧ����ȥ��
		 */
		Query q = sessionUpdate
				.createQuery("update UserInfo user set user.name = 'HuDaSha' where user.id = 1");
		q.executeUpdate();
		sessionUpdate.getTransaction().commit();
	}

	/**
	 * ��˼�����е�ʱ��ִ��save�е�ʱ��ִ��update
	 */
	@Test
	public void testSaveOrUpdate() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session sessionSaveOrUpdate_01 = sf.getCurrentSession();

		UserInfo user = new UserInfo();
		user.setName("YinEr");
		user.setPass("000000");
		user.setWeight(4.2);

		// �����Ҫȡ�õ�ǰ��ʱ�䣬����ת���ɱ�׼��ʱ���ʽ��[yyyy-mm-dd hh:mm:ss]
		user.setBirth(Timestamp.valueOf(new SimpleDateFormat(
				"yyyy-mm-dd hh:mm:ss").format(new Date())));

		/*
		 * ����������£�û��ID���϶�ִ�е���save��
		 */
		sessionSaveOrUpdate_01.beginTransaction();
		sessionSaveOrUpdate_01.saveOrUpdate(user);
		sessionSaveOrUpdate_01.getTransaction().commit();

		user.setName("DaBaoBei");

		/*
		 * �����ʵ������ڣ�������ʱִ�е���update������
		 */
		Session sessionSaveOrUpdate_02 = sf.getCurrentSession();

		sessionSaveOrUpdate_02.beginTransaction();
		sessionSaveOrUpdate_02.saveOrUpdate(user);
		sessionSaveOrUpdate_02.getTransaction().commit();
	}

	/**
	 * �˷���������ջ����е�Sessionִ�н��
	 */
	@Test
	public void testClear() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.getCurrentSession();

		session.beginTransaction();

		UserInfo user_01 = (UserInfo) session.load(UserInfo.class, 1);
		System.out.println(user_01.getName());

		/*
		 * ��ջ����е�Sessionִ�н��������Ļ����ٴβ�ѯֱ��ȡ�������еĽ���������ٴν��в�ѯ�����֮�������ִ��sql��䡣
		 */
		session.clear();

		UserInfo user_02 = (UserInfo) session.load(UserInfo.class, 1);
		System.out.println(user_02.getName());
		session.getTransaction().commit();
	}

	/**
	 * �˷�������ǿ�������ݿ�������ͬ��
	 */
	@Test
	public void testFlush() {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.getCurrentSession();

		session.beginTransaction();

		UserInfo user = (UserInfo) session.load(UserInfo.class, 1);
		user.setName("shabi01");

		/*
		 * ǿ��ʹ��������������ݿ��������ͬ����Ĭ��ֻ���ύ���º��ͬ������commit()��ʱ��Ĭ�Ͻ���flush()�����ڵ������ܵ�ʱ��ʹ�á�
		 */
		session.flush();

		user.setName("shabi02");

		session.getTransaction().commit();
	}

	/*
	 * find()�����Ѿ���ʱ
	 */

	/**
	 * �ֶ������ݿ��и���Hibernate���ý���
	 */
	@Test
	public void testSchemaExport() {
		new SchemaExport(new AnnotationConfiguration().configure()).create(
				true, false);
	}
}
