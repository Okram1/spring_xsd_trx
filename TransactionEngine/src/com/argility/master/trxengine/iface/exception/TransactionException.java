package com.argility.master.trxengine.iface.exception;

import com.argility.master.context.MasterCtxFactory;
import com.argility.master.trxengine.iface.TransactionInterface;

/**
 * 
 * @author marko.salic
 * Transaction exception allows logging of any errors that are thrown from the transactionService
 */
public class TransactionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 121212L;
	
	public TransactionException() {
		super();
	}

	public TransactionException(String message) {
		super(message);
	}
	
	public TransactionException(Throwable e) {
		super(e);
	}
	
	public TransactionException(String type, Throwable e, TransactionInterface trx) {
		super(e.getMessage());
		
		MasterCtxFactory.getInstance().getTrxLogger().logTransactionError(type, e, trx);
	}
}
