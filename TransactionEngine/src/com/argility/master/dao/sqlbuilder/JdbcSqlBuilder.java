package com.argility.master.dao.sqlbuilder;

/**
 * Used to build the SQL statements for persisting entities
 * 
 * @author marko.salic
 *
 */
public interface JdbcSqlBuilder {

	public abstract String buildInsertSql(String[] columns, String table);

	public abstract String buildUpdateSql(String[] updateColumns,
			String[] whereColumns, String[] primaryKeys,
			String[] incrementOnUpdateColumns, String table,
			boolean ignoreIncOnUpdate);

	public abstract String buildDeleteSql(String[] whereColumns, String table,
			boolean orOperationForWhereCondition);

	public abstract String getWhereCondition(String[] columns)
			throws SqlBuilderException;

	public abstract String getWhereCondition(String[] columns,
			boolean orOperationForWhereCondition) throws SqlBuilderException;

}