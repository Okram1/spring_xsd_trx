package com.argility.master.trxengine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * 
 * @author marko.salic
 * Used to retrieve any and all sequences for use in transactions
 */
public class SequenceFinder {

	protected static transient Logger log = Logger
			.getLogger(SequenceFinder.class.getName());

	public static int getAuditSequence(Connection conn) throws SQLException {
		return getNextSequence(conn, "public.audit_aud_id_seq");
	}
	
	private static int getNextSequence(Connection conn, String sequence) throws SQLException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;

		ps = conn.prepareStatement(
				"SELECT nextval('"+sequence+"'::text)");
		rs = ps.executeQuery();
		if (rs.next()) {
			log.info("Next sequence for " + sequence + " is " + rs.getInt(1));
			return rs.getInt(1);
		}

		return 1;

	}
	
}
