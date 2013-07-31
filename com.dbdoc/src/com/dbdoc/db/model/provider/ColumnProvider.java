package com.dbdoc.db.model.provider;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.dbdoc.db.model.Column;
import com.dbdoc.db.model.Table;
import com.dbdoc.db.utils.DatabaseDataTypesUtils;


/*******************************************************************************
 * 数据库表中的列模型提供类
 * 
 * @author moonights
 * 
 * @date 2011-11-23
 ****************************************************************************/
public class ColumnProvider extends DatabaseProvider {
	
private static ColumnProvider instance = null;
	
	
	public synchronized static ColumnProvider getInstance() {
		if(instance == null) instance = new ColumnProvider();
		return instance;
	}
	
	/***************************************************************************
	 * createTableColumnsModel
	 * @param table
	 * @throws SQLException
	 ***************************************************************************/
	public void createTableColumnsModel(Table table) throws SQLException {
		List primaryKeys = getTablePrimaryKeys(table);
		List indices = new LinkedList();
		Map uniqueIndices = new HashMap();
		Map uniqueColumns = new HashMap();
		ResultSet indexRs = null;

		try {
			if (table.getOwnerSynonymName() != null) {
				indexRs = getMetaData().getIndexInfo(getCatalog(),table.getOwnerSynonymName(), table.getName(), false,true);
			} else {
				indexRs = getMetaData().getIndexInfo(getCatalog(), getSchema(),table.getName(), false, true);
			}
			while (indexRs.next()) {
				String columnName = indexRs.getString("COLUMN_NAME");
				if (columnName != null) {
//					System.out.println("index:" + columnName);
					indices.add(columnName);
				}
				//获取unique columns
				String indexName = indexRs.getString("INDEX_NAME");
				boolean nonUnique = indexRs.getBoolean("NON_UNIQUE");
				if (!nonUnique && columnName != null && indexName != null) {
					List l = (List) uniqueColumns.get(indexName);
					if (l == null) {
						l = new ArrayList();
						uniqueColumns.put(indexName, l);
					}
					l.add(columnName);
					uniqueIndices.put(columnName, indexName);
				}
			}
		} catch (Throwable t) {
		} finally {
			if (indexRs != null) {
				indexRs.close();
			}
		}
		List columns = getTableColumns(table, primaryKeys, indices,uniqueIndices, uniqueColumns);
		for (Iterator i = columns.iterator(); i.hasNext();) {
			Column column = (Column) i.next();
			table.addColumn(column);
			
		}
		//如果存在主键
		if (primaryKeys.size() > 0) {
			
		}
	}
	
	/***************************************************************************
	 * 获取表中所有的Columns
	 * @param table
	 * @throws SQLException
	 ***************************************************************************/
	private List getTableColumns(Table table, List primaryKeys, List indices, Map uniqueIndices, Map uniqueColumns) throws SQLException {
		// columns
		List columns = new LinkedList();
		ResultSet columnRs = getColumnsResultSet(table);

		while (columnRs.next()) {
			int sqlType = columnRs.getInt("DATA_TYPE");
			String sqlTypeName = columnRs.getString("TYPE_NAME");
			String columnName = columnRs.getString("COLUMN_NAME");
			String columnDefaultValue = columnRs.getString("COLUMN_DEF");

			String remarks = columnRs.getString("REMARKS");
			if (remarks == null && isOracleDataBase()) {
				remarks = getOracleColumnComments(table.getName(), columnName);
			}
			boolean isNullable = (DatabaseMetaData.columnNullable == columnRs.getInt("NULLABLE"));
			int size = columnRs.getInt("COLUMN_SIZE");
			int decimalDigits = columnRs.getInt("DECIMAL_DIGITS");

			boolean isPk = primaryKeys.contains(columnName);
			boolean isIndexed = indices.contains(columnName);
			String uniqueIndex = (String) uniqueIndices.get(columnName);
			List columnsInUniqueIndex = null;
			if (uniqueIndex != null) {
				columnsInUniqueIndex = (List) uniqueColumns.get(uniqueIndex);
			}

			boolean isUnique = columnsInUniqueIndex != null&& columnsInUniqueIndex.size() == 1;
			if (isUnique) {
				//System.out.println("unique column:" + columnName);
			}
			Column column = new Column(sqlType, sqlTypeName, columnName, size,
					decimalDigits, isPk, isNullable, isIndexed, isUnique,
					columnDefaultValue, remarks);
			columns.add(column);
		}
		columnRs.close();
		return columns;
	}
	
	/**
	 * 获取PrimaryKeys
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	private List<String> getTablePrimaryKeys(Table table) throws SQLException {		
		List primaryKeys = new LinkedList();
		ResultSet primaryKeyRs = null;
		if (table.getOwnerSynonymName() != null) {
			primaryKeyRs = getMetaData().getPrimaryKeys(getCatalog(),table.getOwnerSynonymName(), table.getName());
		} else {
			primaryKeyRs = getMetaData().getPrimaryKeys(getCatalog(), getSchema(), table.getName());
		}
		while (primaryKeyRs.next()) {
			String columnName = primaryKeyRs.getString("COLUMN_NAME");
			System.out.println("primary key:" + columnName);
			primaryKeys.add(columnName);
		}
		primaryKeyRs.close();
		return primaryKeys;
	}
	
	/**
	 * 
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	private ResultSet getColumnsResultSet(Table table) throws SQLException {
		ResultSet columnRs = null;
		if (table.getOwnerSynonymName() != null) {
			columnRs = getMetaData().getColumns(getCatalog(),table.getOwnerSynonymName(), table.getName(), null);
		} else {
			columnRs = getMetaData().getColumns(getCatalog(), getSchema(),table.getName(), null);
		}
		return columnRs;
	}
}
