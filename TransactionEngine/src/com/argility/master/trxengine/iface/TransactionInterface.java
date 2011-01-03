package com.argility.master.trxengine.iface;

import com.argility.master.trxengine.header.ActH01;
import com.argility.master.trxengine.iface.exception.TransactionException;

/**
 * 
 * @author marko.salic Main transaction interface, ALL transactions must
 *         implement this interface so they can be used with the
 *         TransactionService
 */
public interface TransactionInterface {

	/**
	 * 
	 * @return Get metadata about the transaction, this is used when the transaction is generated
	 */
	public ActH01 getActH01();
	
	public void setActH01(ActH01 actH01);
	
	/**
	 * 
	 * @return Get metadata about the transaction, this comes from the client
	 */
	public ActH01 getClientActH01();
	
	public void setClientActH01(ActH01 actH01);
	
	public int getAuditId();
	
	public int getActionType();
	
	public String getAuditXml();

	public void setAuditXml(String xml);

	public String getReplicationXml();

	public void setReplicationXml(String xml);
	
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
