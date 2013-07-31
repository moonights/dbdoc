package com.dbdoc.test;

import com.dbdoc.db.*;
import com.dbdoc.db.datasource.DataSourceProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectTest {

	/**
	 * 驱动程序名
	 */
	private static final String driver = "com.mysql.jdbc.Driver";

	/**
	 * URL指向要访问的数据库名
	 */
	private static final String url = "jdbc:mysql://localhost:3306/classloader_test?useUnicode=true&characterEncoding=UTF-8";

	/**
	 * 用户名
	 */
	private static final String user = "root";

	/**
	 * 密码
	 */
	private static final String password = "141421";

	public static void main(String args[]) {
		if (DataSourceProvider.getConnection() != null) {
			System.out.println("- <<<<<<链接成功！>>>>>>");
		} else {
			System.out.println("-<<<<<<链接失败！>>>>>>");
		}
	}

	public static Connection getConnnection() {

		Connection conn = null;
		try {
			Class.forName(driver);
			// 连接数据库
			conn = DriverManager.getConnection(url, user, password);
			if (!conn.isClosed())
				System.out.println("数据库连接成功...");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				if (null != conn)
					conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeConnection(Connection conn) {
		try {
			if (null != conn) {
				conn.close();
				System.out.println("数据库连接关闭...");
			} else {
				System.out.println("数据库连接不存在...");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

}
