package com.argility.master.dao.sqlbuilder;

import java.util.Arrays;
import java.util.List;

/**
 * Postgres implementation of the sql builder
 * 
 * @author marko.salic
 *
 */
public class JdbcSqlBuilderPostgresImpl implements JdbcSqlBuilder {

	public JdbcSqlBuilderPostgresImpl() {
		
	}
	
	/* (non-Javadoc)
	 * @see com.argility.master.dao.sqlbuilder.JdbcSqlBuilder#buildInsertSql(java.lang.String[], java.lang.String)
	 */
	@Override
	public String buildInsertSql(String[] columns, String table) {
		String insert = "INSERT INTO " + table;
		
		String headCol = " ( ";
		String valCol = " ( ";

		String ch = "";
		String columnName = null;
		String paramColName = null;

		for (int i = 0; i < columns.length; i++) {
			columnName = columns[i];
			paramColName = getParamName(columnName);
			headCol += ch + columnName;
			valCol += ch + ":" + paramColName;
			ch = " , ";
		}
		
		headCol += ")";
		valCol += ")";

		insert += headCol + " VALUES " + valCol;
		
		return insert;
	}
	
	/* (non-Javadoc)
	 * @see com.argility.master.dao.sqlbuilder.JdbcSqlBuilder#buildUpdateSql(java.lang.String[], java.lang.String[], java.lang.String[], java.lang.String[], java.lang.String, boolean)
	 */
	@Override
	public String buildUpdateSql(String[] updateColumns, 
			String[] whereColumns,
			String[] primaryKeys, 
			String[] incrementOnUpdateColumns,
			String table,
			boolean ignoreIncOnUpdate) {
		
		String update = "UPDATE " + table + " SET ";
		String ch = "";
		
		List<String> pkList = Arrays.asList(primaryKeys);
		List<String> incOnUpdateList = Arrays.asList(incrementOnUpdateColumns);
		
		String columnName = null;
		String paramColName = null;

		for (int i = 0; i < updateColumns.length; i++) {
			columnName = updateColumns[i];
			paramColName = getParamName(columnName);

			// Don't update primary key
			if (pkList.contains(columnName))
				continue;
			
			update += ch + columnName + " = ";
			
			if (!ignoreIncOnUpdate && incOnUpdateList.contains(columnName)) {
				update += "(" + columnName + " + "
						+ ":" + paramColName + "::NUMERIC)";
			} else {
				update += ":" + paramColName;
			}

			ch = ", ";
		}
		
		update += getWhereCondition(whereColumns);
		
		return update;
	}
	
	/* (non-Javadoc)
	 * @see com.argility.master.dao.sqlbuilder.JdbcSqlBuilder#buildDeleteSql(java.lang.String[], java.lang.String, boolean)
	 */
	@Override
	public String buildDeleteSql(String[] whereColumns, String table, boolean orOperationForWhereCondition) {
		return "DELETE FROM " + table
			+ getWhereCondition(whereColumns);
	}
	
	/* (non-Javadoc)
	 * @see com.argility.master.dao.sqlbuilder.JdbcSqlBuilder#getWhereCondition(java.lang.String[])
	 */
	@Override
	public String getWhereCondition(String[] columns) throws SqlBuilderException{
		return getWhereCondition(columns, false);
	}
	
	/* (non-Javadoc)
	 * @see com.argility.master.dao.sqlbuilder.JdbcSqlBuilder#getWhereCondition(java.lang.String[], boolean)
	 */
	@Override
	public String getWhereCondition(String[] columns, boolean orOperationForWhereCondition) throws SqlBuilderException{
		String where = " WHERE ";
		String oper = "";
		String operationChar = " AND ";
		if (orOperationForWhereCondition) {
			operationChar = " OR ";
		}
		
		String columnName = null;
		String paramColName = null;
		
		for (int i = 0; i < columns.length; i++) {
			columnName = columns[i];
			paramColName = getParamName(columnName);
			
			where += oper + columnName + " = :"
					+ paramColName;
			oper = operationChar;
		}

		return where;
	}
	
	private String getParamName(String columnName) {
		return toCamelCase(columnName);
	}
	
	private String toCamelCase(String s) {
		if (s == null) return null;
		
		String[] parts = s.split("_");
		if (parts.length == 0) return s;
		
		String camelCaseString = "";
		boolean first = true;
		for (String part : parts) {
			if (first) {
				camelCaseString += part;
			} else {
				camelCaseString = camelCaseString + toProperCase(part);
			}
			first = false;
		}
		return camelCaseString;
	}

	private String toProperCase(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}
}
