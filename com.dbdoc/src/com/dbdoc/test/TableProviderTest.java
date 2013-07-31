package com.dbdoc.test;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.dbdoc.db.model.Column;
import com.dbdoc.db.model.Table;
import com.dbdoc.db.model.provider.TableProvider;

/**
 * 
 * @author moonights
 *
 * @date 2011-11-23
 */
public class TableProviderTest {
	public static void main(String args[]) throws SQLException{
		List tables = TableProvider.getInstance().getAllTables();
		Logger.global.info("------------------打印所选库表名 BEGIN------------------");
		for(int i = 0; i < tables.size(); i++ ) {
			Table table=(Table)tables.get(i);
			String tableName = table.getName();
			System.out.println("表"+i+":\""+tableName+"\":");
			System.out.println("表类型"+i+":\""+table.getType()+"\":");
//			if(null!=table.getColumns()){
//				System.out.print("其中字段分别为:(");
//				for (Iterator k = table.getColumns().iterator(); k.hasNext();) {
//					Column column = (Column) k.next();
//					System.out.print("\""+column.get_sqlName()+"-"+column.get_sqlTypeName()+"("+column.get_size()+")"+"\"、");
//				}
//				System.out.print(")\n");
//			}
		}
		Logger.global.info("--------------------打印所选库表名  END----------------------");
	}
}
