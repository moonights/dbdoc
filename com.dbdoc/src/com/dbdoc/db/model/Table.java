package com.dbdoc.db.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 
 * @author moonights
 *
 * @date 2011-11-23
 */
public class Table {
	private String name;			//表名
	private String remarks;			//注释
	private String className;		//类名
	private String type;			//表类型
	
	private Set<Column> columns=new LinkedHashSet();//列（s）
	private List<Column> primaryKeyColumns = new ArrayList();
	
	/** the name of the owner of the synonym if this table is a synonym */
	private String ownerSynonymName = null;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public Set<Column> getColumns() {
		return columns;
	}
	public void setColumns(Set<Column> columns) {
		this.columns = columns;
	}
	public void addColumn(Column column) {
		columns.add(column);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getOwnerSynonymName() {
		return ownerSynonymName;
	}
	public void setOwnerSynonymName(String ownerSynonymName) {
		this.ownerSynonymName = ownerSynonymName;
	}
	public List<Column> getPrimaryKeyColumns() {
		return primaryKeyColumns;
	}
	public void setPrimaryKeyColumns(List<Column> primaryKeyColumns) {
		this.primaryKeyColumns = primaryKeyColumns;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void initPrimaryKeyColumns(){
		if(this.columns.size()>0){
			for (Iterator i = columns.iterator(); i.hasNext();) {
				Column column = (Column) i.next();
				if(column.is_isPk()){
					primaryKeyColumns.add(column);
				}
			}
		}
	}
	
}
