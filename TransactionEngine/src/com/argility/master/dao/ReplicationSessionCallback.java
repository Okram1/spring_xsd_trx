package com.argility.master.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.argility.master.dao.ReplicationSession;

public class ReplicationSessionCallback<T> implements PreparedStatementCallback<ReplicationSession> {

	protected transient Logger log = Logger.getLogger(ReplicationSessionCallback.class);
	
	private ReplicationSession session;
	
	public ReplicationSessionCallback(ReplicationSession session) {
		super();
		this.session = session;
	}
	
	@Override
	public ReplicationSession doInPreparedStatement(PreparedStatement ps) throws SQLException,
			DataAccessException {
		String sql = ps.toString();
		log.info("EXECUTING SQL: " + sql);
		
		int cnt = 0;
		try {
			cnt = ps.executeUpdate();
		} catch (Exception e) {
			SQLException ex = new SQLException(e.getMessage() + " ## TRIED TO EXECUTE SQL :" + sql);
			throw ex;
		}
		if (cnt < 1) {
			throw new SQLException(sql + " : Statement updated 0 rows.");
		}
		log.info("SQL UPDATED");
		
		session.addSqlStatement(ps.toString());
		return session;
	}
	
}
