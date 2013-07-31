package com.dbdoc.db.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

/****
 * 该类实现了DataSource接口，参考DataSource
 * @author moonights
 *
 */
public class DriverManagerDataSource implements DataSource {
	private String url;
	private String username;
	private String password;
	private String driverClass;
	
	private static void loadJdbcDriver(String driver) {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("not found jdbc driver class:["+driver+"]",e);
		}
	}
	
	public DriverManagerDataSource(String url, String username,String password, String driverClass) {
		this.url = url;
		this.username = username;
		this.password = password;
		this.driverClass = driverClass;
		loadJdbcDriver(driverClass);
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url,username,password);
	}

	public Connection getConnection(String username, String password) throws SQLException {
		return DriverManager.getConnection(url,username,password);
	}

	public PrintWriter getLogWriter() throws SQLException {
		throw new UnsupportedOperationException("getLogWriter");
	}

	public int getLoginTimeout() throws SQLException {
		throw new UnsupportedOperationException("getLoginTimeout");
	}

	public void setLogWriter(PrintWriter out) throws SQLException {
		throw new UnsupportedOperationException("setLogWriter");
	}

	public void setLoginTimeout(int seconds) throws SQLException {
		throw new UnsupportedOperationException("setLoginTimeout");
	}

	public <T> T  unwrap(Class<T> iface) throws SQLException {
		if(iface == null) throw new IllegalArgumentException("Interface argument must not be null");
		if (!DataSource.class.equals(iface)) {
			throw new SQLException("DataSource of type [" + getClass().getName() +"] can only be unwrapped as [javax.sql.DataSource], " +
					"not as [" + iface.getName());
		}
		return (T) this;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return DataSource.class.equals(iface);
	}

}