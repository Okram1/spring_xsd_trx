package com.argility.master.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;

import com.argility.master.dao.ReplicationSession;

/**
 * This callback will be called just before every prepared statement is executed, allowing us to add any custom
 * logic and execute the prepared statement
 *
 * @param <T>
 * 		An object implementing ReplicationSession where we will write the replication SQL statements
 */
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
		// SQL we will be executing
		String sql = ps.toString();
		
		// Time the SQL runtime
		long startTime;
		long endTime;
		
		log.info("EXECUTING SQL: " + sql);
		
		// Execute the SQL, get the start and end time
		int cnt = 0;
		try {
			startTime = new Date().getTime();
			cnt = ps.executeUpdate();
			endTime = new Date().getTime();
		} catch (Exception e) {
			SQLException ex = new SQLException(e.getMessage() + " ## TRIED TO EXECUTE SQL :" + sql);
			throw ex;
		}
		
		// Make sure that the SQL affected a row in the database
		if (cnt < 1) {
			throw new SQLException(sql + " : Statement updated 0 rows.");
		}
		
		// How long did the sql run for
		log.info("RUN TIME: " + (endTime - startTime) + " ms");
		
		// Add the statements to the action header for replication
		session.addSqlStatement(ps.toString());
		
		return session;
	}
	
}
