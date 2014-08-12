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

	private static final String DRIVER = "com.mysql.jdbc.Driver"; // 定义驱动字符串
	private static final String URL = "jdbc:mysql://localhost:3306/frameworktest_db"; // 定义URL
	private static final String NAME = "root"; // 定义用户名
	private static final String PASSWORD = "qdhy199148"; // 定义密码

	// 无参的构造函数
	public BasicDao() {
	}

	/*
	 * 取得数据库连接
	 */
	public Connection getCon() {
		try {
			Class.forName(DRIVER).newInstance();
			con = (Connection) DriverManager.getConnection(URL, NAME, PASSWORD); // 创建连接
			System.out.println("成功创建链接"+con);
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
	 * 执行数据库查询并返回查询结果
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
	 * 执行数据库更新01
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
	 * 执行数据库更新02
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
	 * 关闭数据库连接
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
			System.out.println("数据库连接已关闭");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
