package com.argility.master.daobuilder;

public class JdbcColumnInfo {
	
	public String columnName;
	public String typeName;
	public String columnDef;
	public boolean nullable = true;
	public boolean autoIncrement;
	public boolean primaryKey;
	public boolean incrementOnUpdate;
	public boolean ofNumericType;
	public boolean ofTextType;
	
	public JdbcColumnInfo() {
		
	}

	public String getColumnName() {
		return columnName;
	}

	public String getTypeName() {
		return typeName;
	}

	public String getColumnDef() {
		return columnDef;
	}

	public boolean isNullable() {
		return nullable;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public boolean isIncrementOnUpdate() {
		return incrementOnUpdate;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public void setColumnDef(String columnDef) {
		this.columnDef = columnDef;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public void setIncrementOnUpdate(boolean incrementOnUpdate) {
		this.incrementOnUpdate = incrementOnUpdate;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public boolean isOfNumericType() {
		return ofNumericType;
	}

	public boolean isOfTextType() {
		return ofTextType;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public void setOfNumericType(boolean ofNumericType) {
		this.ofNumericType = ofNumericType;
	}

	public void setOfTextType(boolean ofTextType) {
		this.ofTextType = ofTextType;
	}

	
}
