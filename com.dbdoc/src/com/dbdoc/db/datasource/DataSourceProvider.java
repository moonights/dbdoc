package com.dbdoc.db.datasource;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.dbdoc.Constants;
import com.dbdoc.utils.PropertiesProvider;

/**
 * 读取数据源类
 * @author moonights
 *
 */
public class DataSourceProvider {
	private static Connection connection;
	private static DataSource dataSource;

	public synchronized static Connection getNewConnection() {
		try {
			return getDataSource().getConnection();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public synchronized static Connection getConnection() {
		try {
			if(connection == null || connection.isClosed()) {
				connection = getDataSource().getConnection();
			}
			return connection;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void setDataSource(DataSource dataSource) {
		DataSourceProvider.dataSource = dataSource;
	}

	public synchronized static DataSource getDataSource() {
		if(dataSource == null) {
			dataSource = new DriverManagerDataSource(PropertiesProvider.getRequiredProperty(Constants.JDBC_URL), 
					PropertiesProvider.getRequiredProperty(Constants.JDBC_USERNAME), 
					PropertiesProvider.getRequiredProperty(Constants.JDBC_PASSWORD), 
					PropertiesProvider.getRequiredProperty(Constants.JDBC_DRIVER));
		}
		return dataSource;
	}
}
