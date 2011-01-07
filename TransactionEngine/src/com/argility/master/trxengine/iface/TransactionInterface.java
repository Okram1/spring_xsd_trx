package com.argility.master.trxengine.iface;

import com.argility.master.trxengine.header.ActH01;
import com.argility.master.trxengine.iface.exception.TransactionException;

/**
 * 	Main transaction interface, ALL transactions must
 *  implement this interface so they can be used with the
 *  TransactionService
 * @author marko.salic 
 */
public interface TransactionInterface {

	/**
	 * Get metadata about the transaction, this is used when the transaction is generated on the server
	 * @return 
	 */
	public ActH01 getActH01();
	
	public void setActH01(ActH01 actH01);
	
	/**
	 * 
	 * @return Get metadata about the transaction, this comes from the client
	 */
	public ActH01 getClientActH01();
	
	public void setClientActH01(ActH01 actH01);
	
	/**
	 * Get this transactions audit id
	 * @return
	 */
	public int getAuditId();
	
	/**
	 * Get this transactions action type
	 * @return
	 */
	public int getActionType();
	
	/**
	 * Get this transactions xml representation
	 * @return
	 */
	public String getAuditXml();

	public void setAuditXml(String xml);

	/**
	 * Get this transactions replication, so get all the SQL that were executed by this transaction
	 * @return
	 */
	public String getReplicationXml();

	public void setReplicationXml(String xml);
	
	/**
	 * Classpath location for the schema/xsd this transaction must be validated against
	 * if this is not set or invalid you WILL NOT be able to execute the transaction 
	 * @return
	 */
	public String getSchemaLocation();

	/**
	 * 
	 * @param session
	 * @return
	 * @throws TransactionException
	 *             Execute the transaction, amongst other logic the transactions
	 *             executeWrite logic will be executed
	 */
	public TransactionInterface executeWrite() throws TransactionException;

}
