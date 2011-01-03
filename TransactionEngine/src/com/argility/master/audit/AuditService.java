package com.argility.master.audit;

import java.sql.SQLException;

import com.argility.master.trxengine.iface.TransactionInterface;

/**
 * Auditing service
 * @author marko.salic
 *
 */
public interface AuditService {

	/**
	 * Used to create a new audit entry
	 * @param trx
	 * 		Transaction we are auditing
	 * @throws SQLException
	 * 
	 */
	public void insertAudit(TransactionInterface trx) throws SQLException;
	
	/**
	 * Used to update an audits xml and replication data
	 * @param trx
	 * 		Transaction we are auditing
	 * @throws SQLException
	 * 
	 */
	public void updateAuditRepicationAndXml(String xml, String replication, int audId) throws SQLException;
	
	/**
	 * Used to create a new audit entry
	 * @param trx
	 * 		Obo transaction
	 * @throws SQLException
	 * 
	 */
	public void insertOboHistEntry(TransactionInterface trx) throws SQLException;
	
}
