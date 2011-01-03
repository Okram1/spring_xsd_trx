package com.argility.master.audit;

import java.sql.SQLException;

import com.argility.master.trxengine.iface.TransactionInterface;

public interface AuditService {

	/**
	 * 
	 * @param trx
	 * @throws SQLException
	 * Used to create a new audit entry
	 */
	public void insertAudit(TransactionInterface trx) throws SQLException;
	
	/**
	 * 
	 * @param trx
	 * @throws SQLException
	 * Used to update an audits xml and replication data
	 */
	public void updateAuditRepicationAndXml(String xml, String replication, int audId) throws SQLException;
	
	/**
	 * 
	 * @param trx
	 * @throws SQLException
	 * Used to create a new audit entry
	 */
	public void insertOboHistEntry(TransactionInterface trx) throws SQLException;
	
}
