package org.superhy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BasicDao {
	private Connection con = null;
	private Statement stat = null;
	private PreparedStatement pstat = null;
	private ResultSet rs = null;

	private static final String DRIVER = "com.mysql.jdbc.Driver"; // ���������ַ���
	private static final String URL = "jdbc:mysql://localhost:3306/frameworktest_db"; // ����URL
	private static final String NAME = "root"; // �����û���
	private static final String PASSWORD = "qdhy199148"; // ��������

	// �޲εĹ��캯��
	public BasicDao() {
	}

	/*
	 * ȡ�����ݿ�����
	 */
	public Connection getCon() {
		try {
			Class.forName(DRIVER).newInstance();
			con = (Connection) DriverManager.getConnection(URL, NAME, PASSWORD); // ��������
			System.out.println("�ɹ���������"+con);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	/*
	 * ִ�����ݿ��ѯ�����ز�ѯ���
	 */
	public ResultSet query(String sql) {
		try {
			con = getCon();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return rs;
	}

	/*
	 * ִ�����ݿ����01
	 */
	public void update(String sql) {
		try {
			con = getCon();
			stat = con.createStatement();
			stat.executeUpdate(sql);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	/*
	 * ִ�����ݿ����02
	 */
	public void update(String sql, String[] args) {
		try {
			con = getCon();
			pstat = con.prepareStatement(sql);
			for (int i = 0; i < args.length; i++) {
				pstat.setString(i + 1, args[i]);
			}
			pstat.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	/*
	 * �ر����ݿ�����
	 */
	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (stat != null)
				stat.close();
			if (pstat != null)
				pstat.close();
			if (con != null)
				con.close();
			System.out.println("���ݿ������ѹر�");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
