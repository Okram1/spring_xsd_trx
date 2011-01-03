package com.argility.master.trxengine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * This class is used as a proxy for preparedStatements, the proxy would 
 * store all executed statements that need to form part of the audit replication  
 * <p>
 */
public class JDBCReplicationSession {

	protected Logger log = Logger.getLogger(this.getClass().getName());
	
	private Connection connection;
	public List<String> replicationSQL = new ArrayList<String>();

	public JDBCReplicationSession(Connection conn) {
		setConnection(conn);
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection conn) {
		this.connection = conn;
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		//log.debug("prepareStatement(" + sql + ")");
		if (getConnection() == null || getConnection().isClosed()) {
			throw new SQLException("Connection not opened.");
		}

		return getConnection().prepareStatement(sql);
	}

	public boolean execute(PreparedStatement ps) throws SQLException {
		log.debug("execute(" + ps.toString() + ")");
		addStatementToTransaction(ps);
		return ps.execute();
	}

	public ResultSet executeQuery(PreparedStatement ps) throws SQLException {
		log.debug("executeQuery(" + ps.toString() + ")");
		addStatementToTransaction(ps);
		return ps.executeQuery();
	}

	public int executeUpdate(PreparedStatement ps) throws SQLException {
		log.debug("executeUpdate(" + ps.toString() + ")");
		addStatementToTransaction(ps);
		int cnt = ps.executeUpdate();
		if (cnt <= 0) {
			throw new SQLException("Update returned 0 rows.");
		}
		return cnt;
	}

	public void addStatementToTransaction(PreparedStatement ps)
			throws SQLException {
		String sql = ps.toString();
		getReplicationSQL().add(sql);
	}

	public List<String> getReplicationSQL() {
		return replicationSQL;
	}

	public void setReplicationSQL(List<String> replicationSQL) {
		this.replicationSQL = replicationSQL;
	}

	public String getReplicationXml() {
		String xml = "<replication>";
		for (Iterator<String> iterator = getReplicationSQL().iterator(); iterator
				.hasNext();) {
			String data = (String) iterator.next();
			xml += "<PS>" + data + "</PS>";
		}
		xml += "</replication>";
		return xml;
	}
}
