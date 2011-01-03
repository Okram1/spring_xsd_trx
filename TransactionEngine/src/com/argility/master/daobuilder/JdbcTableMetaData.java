package com.argility.master.daobuilder;

public class JdbcTableMetaData {

	private String tableName;
	private JdbcColumnInfo[] columns;
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setColumns(JdbcColumnInfo[] columns) {
		this.columns = columns;
	}

	public String getTableName() {
		return tableName;
	}

	public JdbcColumnInfo[] getColumns() {
		return columns;
	}

}
