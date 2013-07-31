package com.dbdoc.db.model;

import com.dbdoc.db.utils.DatabaseDataTypesUtils;

/***
 * 表中的列模型
 * @author moonights
 *
 */
public class Column {

	private String _sqlName;		//列名称
	private String _sqlTypeName;	//列类型名称
	private String _sqlTypeOuter;	//列类型输出的字符串
	private String _remarks;		//注释
	private String _defaultValue;	//默认值
	private int _sqlType;			//列类型
	private int _size;				//大小
	private int _decimalDigits;		//
	private boolean _isPk;			//是否为主键
	private boolean _isFk;			//是否为外键
	private boolean _isNullable;	//是否可以为空
	private boolean _isIndexed;		//是否为索引
	private boolean _isUnique;		//是否Unique
	
	public int get_decimalDigits() {
		return _decimalDigits;
	}
	public void set_decimalDigits(int digits) {
		_decimalDigits = digits;
	}
	public String get_defaultValue() {
		return _defaultValue;
	}
	public void set_defaultValue(String value) {
		_defaultValue = value;
	}
	public boolean is_isFk() {
		return _isFk;
	}
	public void set_isFk(boolean fk) {
		_isFk = fk;
	}
	public boolean is_isIndexed() {
		return _isIndexed;
	}
	public void set_isIndexed(boolean indexed) {
		_isIndexed = indexed;
	}
	public boolean is_isNullable() {
		return _isNullable;
	}
	public void set_isNullable(boolean nullable) {
		_isNullable = nullable;
	}
	public boolean is_isPk() {
		return _isPk;
	}
	public void set_isPk(boolean pk) {
		_isPk = pk;
	}
	public boolean is_isUnique() {
		return _isUnique;
	}
	public void set_isUnique(boolean unique) {
		_isUnique = unique;
	}
	public String get_remarks() {
		return _remarks;
	}
	public void set_remarks(String _remarks) {
		this._remarks = _remarks;
	}
	public int get_size() {
		return _size;
	}
	public void set_size(int _size) {
		this._size = _size;
	}
	public String get_sqlName() {
		return _sqlName;
	}
	public void set_sqlName(String name) {
		_sqlName = name;
	}
	public int get_sqlType() {
		return _sqlType;
	}
	public void set_sqlType(int type) {
		_sqlType = type;
	}
	public String get_sqlTypeName() {
		return _sqlTypeName;
	}
	public void set_sqlTypeName(String typeName) {
		_sqlTypeName = typeName;
	}
	public Column(int sqlType, String sqlTypeName,
			String sqlName, int size, int decimalDigits, boolean isPk,
			boolean isNullable, boolean isIndexed, boolean isUnique,
			String defaultValue,String remarks) {
		_sqlType = sqlType;
		_sqlName = sqlName;
		_sqlTypeName = sqlTypeName;
		_size = size;
		_decimalDigits = decimalDigits;
		_isPk = isPk;
		_isNullable = isNullable;
		_isIndexed = isIndexed;
		_isUnique = isUnique;
		_defaultValue = defaultValue;
		_remarks = remarks;

	}
	
	/***组织字段类型的输出格式**/
	public String get_sqlTypeOuter() {
		if(DatabaseDataTypesUtils.isDate(_sqlTypeName)){
			return _sqlTypeName;
		}
		return _sqlTypeName+"("+_size+")";
	}
	public void set_sqlTypeOuter(String typeOuter) {
		_sqlTypeOuter = typeOuter;
	}
	
}
