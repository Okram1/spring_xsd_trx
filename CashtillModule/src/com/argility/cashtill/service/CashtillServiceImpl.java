package com.argility.cashtill.service;

import com.argility.cashtill.trx.CreateCashtillTrx;
import com.argility.cashtill.trx.PettyCashDrawRevTrx;
import com.argility.cashtill.trx.PettyCashDrawTrx;
import com.argility.master.service.AbstractService;
import com.argility.master.trxengine.iface.exception.TransactionException;

public class CashtillServiceImpl extends AbstractService implements
		CashtillService {

	@Override
	public CreateCashtillTrx createCashtillTrx(String trxXml) throws TransactionException {
		CreateCashtillTrx trx = null;
		
		try {
			trx = (CreateCashtillTrx)getXmlParser().fromXml(trxXml);
			getTransactionService().executeTransaction(trx);
			
			// OR using xml, so no need to cast back, simply pass to the transaction service
			// getTransactionService().executeTransaction(trxXml);
		} catch (Exception e) {
			throw new TransactionException(e.getMessage());
		}
		
		return trx;
	}

	@Override
	public PettyCashDrawTrx pettyCashDrawTrx(String trxXml) throws TransactionException {
		PettyCashDrawTrx trx = null;
		
		try {
			trx = (PettyCashDrawTrx)getXmlParser().fromXml(trxXml);
			getTransactionService().executeTransaction(trx);
		} catch (Exception e) {
			throw new TransactionException(e.getMessage());
		}
		
		return trx;
	}

	@Override
	public PettyCashDrawRevTrx pettyCashDrawRevTrx(String trxXml)
			throws TransactionException {
		PettyCashDrawRevTrx trx = null;
		
		try {
			trx = (PettyCashDrawRevTrx)getXmlParser().fromXml(trxXml);
			getTransactionService().executeTransaction(trx);
		} catch (Exception e) {
			throw new TransactionException(e.getMessage());
		}
		
		return trx;
	}

}
