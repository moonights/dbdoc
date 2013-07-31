package com.dbdoc.db.model.provider;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import com.dbdoc.db.datasource.DataSourceProvider;
import com.dbdoc.db.model.Table;

/*******************************************************************************
 * 数据库模型提供类
 * 将数据库通用的行为（状态）抽取为一个抽象类
 * @author moonights
 * 
 * @date 2011-11-23
 */
public abstract class DatabaseProvider {
	public Connection connection;

	public DatabaseProvider() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = DataSourceProvider.getConnection();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DataSourceProvider.getConnection();
		}
		return connection;
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return getConnection().getMetaData();
	}

	public String getCatalog() {
		return null;
	}

	public String getSchema() {
		return null;
	}

	/**
	 * 执行一个Sql语句 返回查询的结果
	 * 
	 * @param sql
	 * @return
	 */
	public String queryForString(String sql) {
		Statement s = null;
		ResultSet rs = null;
		try {
			s = getConnection().createStatement();
			rs = s.executeQuery(sql);
			if (rs.next()) {
				return rs.getString(1);
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (s != null)
					s.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
			}
		}
	}
	
	/**
	 * 获取数据库结构信息
	 * @return String
	 */
	public String getDatabaseStructureInfo() {
		ResultSet schemaRs = null;
		ResultSet catalogRs = null;
		String nl = System.getProperty("line.separator");
		StringBuffer sb = new StringBuffer(nl);
		sb.append("Configured schema:").append(getSchema()).append(nl);
		sb.append("Configured catalog:").append(getCatalog()).append(nl);
		try {
			schemaRs = getMetaData().getSchemas();
			sb.append("Available schemas:").append(nl);
			while (schemaRs.next()) {
				sb.append("  ").append(schemaRs.getString("TABLE_SCHEM"))
						.append(nl);
			}
		} catch (SQLException e2) {
			sb.append("获取schemas出现错误...").append(nl);
		} finally {
			try {
				schemaRs.close();
			} catch (Exception ignore) {
			}
		}
		try {
			catalogRs = getMetaData().getCatalogs();
			sb.append("Available catalogs:").append(nl);
			while (catalogRs.next()) {
				sb.append("  ").append(catalogRs.getString("TABLE_CAT"))
						.append(nl);
			}
		} catch (SQLException e2) {
			sb.append("获取catalogs出现错误...").append(nl);
		} finally {
			try {
				catalogRs.close();
			} catch (Exception ignore) {
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param synonymName 同义词
	 * @return
	 */
	public String getSynonymOwner(String synonymName) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String ret = null;
		try {
			ps = getConnection().prepareStatement("select table_owner from sys.all_synonyms where table_name=? and owner=?");
			ps.setString(1, synonymName);
			ps.setString(2, getSchema());
			rs = ps.executeQuery();
			if (rs.next()) {
				ret = rs.getString(1);
			} else {
				String databaseStructure = getDatabaseStructureInfo();
				throw new RuntimeException("synonymName： " + synonymName
						+ " not found." + databaseStructure);
			}
		} catch (SQLException e) {
			String databaseStructure = getDatabaseStructureInfo();
			throw new RuntimeException("Exception in getting synonym owner "	+ databaseStructure);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (Exception e) {
				}
			}
		}
		return ret;
	}

	/***
	 * 是否为Oracle
	 * @return
	 */
	public boolean isOracleDataBase() {
		boolean ret = false;
		try {
			ret = (getMetaData().getDatabaseProductName().toLowerCase()
					.indexOf("oracle") != -1);
		} catch (Exception ignore) {
		}
		return ret;
	}

	/**
	 * 获取Oracle 库中的某一表名
	 * @param table
	 * @return
	 */
	public String getOracleTableComments(String table) {
		String sql = "SELECT comments FROM user_tab_comments WHERE table_name='"
				+ table + "'";
		return queryForString(sql);
	}

	/**
	 * 获取Oracle库中某一表中的某一列名
	 * @param table
	 * @param column
	 * @return
	 */
	public String getOracleColumnComments(String table, String column) {
		String sql = "SELECT comments FROM user_col_comments WHERE table_name='"
				+ table + "' AND column_name = '" + column + "'";
		return queryForString(sql);
	}
}
