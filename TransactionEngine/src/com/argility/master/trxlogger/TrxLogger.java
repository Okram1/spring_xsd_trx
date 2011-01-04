package com.argility.master.trxlogger;

import com.argility.master.trxengine.iface.TransactionInterface;

/**
 * 
 * Logs transaction information to the database
 *
 */
public interface TrxLogger {

	public void logTransactionError(String type, Throwable e, TransactionInterface trx);
	
}
