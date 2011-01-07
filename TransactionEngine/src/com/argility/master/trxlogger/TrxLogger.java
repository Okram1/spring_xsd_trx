package com.argility.master.trxlogger;

import com.argility.master.trxengine.iface.TransactionInterface;

/**
 * 
 * Logs transaction information to the database
 *
 */
public interface TrxLogger {

	/**
	 * The mthod will log errors into the tran_exception_log table
	 * 
	 * @param type
	 * 		Type of error
	 * @param e
	 * 		The exception that caused the error
	 * @param trx
	 * 		The TransactionInterface you were trying to execute
	 */
	public void logTransactionError(String type, Throwable e, TransactionInterface trx);
	
}
