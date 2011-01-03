package com.argility.master.daobuilder;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class MetaDataBuilder {

	protected transient Logger log = Logger
			.getLogger(this.getClass().getName());
	
	public static String SCHEMA = "public";
	public static String INCREMENT_ON_UPDATE_REMARK = "INCREMENT_ON_UPDATE";
	public static String[] TABLE_TYPES = new String[] {"TABLE"};

	private DataSource ds = null;
	private Connection connection = null;

	public MetaDataBuilder(DataSource ds) {
		this.ds = ds;
	}
	
	public MetaDataBuilder(Connection connection) {
		this.connection = connection;
	}
	
	public void printProductInformation(DatabaseMetaData databaseMetaData) throws SQLException {
		
		String databaseProductName = databaseMetaData.getDatabaseProductName();
		String databaseProductVersion = databaseMetaData
				.getDatabaseProductVersion();

		log.info("Database Product Name: " + databaseProductName);
		log.info("Database Product Version: " + databaseProductVersion);

	}
	
	public DatabaseMetaData getDatabaseMetaData() throws SQLException {
		log.info("getDatabaseMetaData()");
		DatabaseMetaData databaseMetaData = getConnection().getMetaData();
		printProductInformation(databaseMetaData);
		return databaseMetaData;
	}
	
	public JdbcTableMetaData[] getAllTableMetaDatas() throws SQLException {
		log.info("getAllTableMetaDatas()");
		List<JdbcTableMetaData> metaDataList = new ArrayList<JdbcTableMetaData>();
		JdbcTableMetaData metaData = null;
		
		log.info("Getting metadata");
		DatabaseMetaData databaseMetaData = getDatabaseMetaData();
		
		log.info("Gettting tables information");
		ResultSet tableRs = databaseMetaData.getTables(null, SCHEMA, null, TABLE_TYPES);
		while (tableRs.next()) {
			String tableName = tableRs.getString("TABLE_NAME");
			log.info("Getting metadata for table " + tableName);
			
			metaData = getTableMetaData(tableName, databaseMetaData);
			metaDataList.add(metaData);
			
		}
		
		return metaDataList.toArray(new JdbcTableMetaData[metaDataList.size()]);

	}

	public JdbcTableMetaData getTableMetaData(String name) throws SQLException {
		
		DatabaseMetaData databaseMetaData = getDatabaseMetaData();
		return getTableMetaData(name, databaseMetaData);
	}
	
	public JdbcTableMetaData getTableMetaData(String name,
			DatabaseMetaData databaseMetaData) throws SQLException {
		log.info("getTableMetaData(" + name + ", databaseMetaData)");
		JdbcTableMetaData table = new JdbcTableMetaData();
		table.setTableName(name);
		
		List<JdbcColumnInfo> columns = new ArrayList<JdbcColumnInfo>();
		List<String> pkList = new ArrayList<String>();
		
		JdbcColumnInfo col = null;
		
		ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(null, SCHEMA, name);
		while (primaryKeys.next()) {
			String primaryKeyColumn = primaryKeys.getString("COLUMN_NAME");
			pkList.add(primaryKeyColumn);
		}
		
		log.info("Gettting column information");
		ResultSet rs = databaseMetaData.getColumns(null, SCHEMA, name,
				null);
		while (rs.next()) {
			col = new JdbcColumnInfo();
			
			boolean nullable = true;
			String remark = null;
			
			// If a comment contains INCREMENT_ON_UPDATE_REMARK then this is an auto incrementing column
			remark = rs.getString("REMARKS");
			if (remark != null && remark.indexOf(INCREMENT_ON_UPDATE_REMARK) != -1) {
				col.setIncrementOnUpdate(true);
			}
			
			col.setColumnName(rs.getString("COLUMN_NAME"));
			col.setTypeName(rs.getString("TYPE_NAME"));
			
			// Is this field nullable
			if (rs.getString("IS_NULLABLE").equalsIgnoreCase("NO")) {
				nullable = false;
			}
			col.setNullable(nullable);
			
			// Is this part of the primary key
			if (pkList.contains(col.getColumnName())) {
				col.setPrimaryKey(true);
			}
			
			log.info("Adding column " + col.getColumnName() + " to table " + name + " meta data");
			columns.add(col);
		}
		
		table.setColumns(columns.toArray(new JdbcColumnInfo[columns.size()]));
		
		return table;
		
	}
	
	private Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			if (ds == null) {
				throw new SQLException("Connection is null or closed and DataSource is not set");
			}
			connection = ds.getConnection();
		}
		
		return connection;
	}

}
