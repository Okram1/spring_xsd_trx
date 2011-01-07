package com.argility.cashtill.service;

import com.argility.cashtill.trx.CreateCashtillTrx;
import com.argility.cashtill.trx.PettyCashDrawRevTrx;
import com.argility.cashtill.trx.PettyCashDrawTrx;
import com.argility.master.trxengine.iface.exception.TransactionException;

public interface CashtillService {

	/**
	 * Use this method to create a new cashtill for a user
	 * 
	 * @param trxXml
	 * 		The xml representation of the CreateCashtillTrx
	 * @return
	 * 		Executed CreateCashtillTrx
	 * @throws TransactionException
	 */
	public CreateCashtillTrx createCashtillTrx(String trxXml) throws TransactionException;
	
	/**
	 * Used when a user does a petty cash withdrawal
	 * 
	 * @param trxXml
	 * 		The xml representation of the PettyCashDrawTrx
	 * @return
	 * 		Executed PettyCashDrawTrx
	 * @throws TransactionException
	 */
	public PettyCashDrawTrx pettyCashDrawTrx(String trxXml) throws TransactionException;
	
	/**
	 * Used when a petty cash draw is reversed for a user
	 * 
	 * @param trxXml
	 * 		The xml representation of the PettyCashDrawRevTrx
	 * @return
	 * 		Executed PettyCashDrawRevTrx
	 * @throws TransactionException
	 */
	public PettyCashDrawRevTrx pettyCashDrawRevTrx(String trxXml) throws TransactionException;
}
