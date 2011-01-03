package com.argility.master.trxlogger;

import com.argility.master.trxengine.iface.TransactionInterface;

public interface TrxLogger {

	public void logTransactionError(String type, Throwable e, TransactionInterface trx);
	
}
