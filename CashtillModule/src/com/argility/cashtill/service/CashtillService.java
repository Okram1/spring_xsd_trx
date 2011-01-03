package com.argility.cashtill.service;

import com.argility.cashtill.trx.CreateCashtillTrx;
import com.argility.cashtill.trx.PettyCashDrawRevTrx;
import com.argility.cashtill.trx.PettyCashDrawTrx;
import com.argility.master.trxengine.iface.exception.TransactionException;

public interface CashtillService {

	public CreateCashtillTrx createCashtillTrx(String trxXml) throws TransactionException;
	
	public PettyCashDrawTrx pettyCashDrawTrx(String trxXml) throws TransactionException;
	
	public PettyCashDrawRevTrx pettyCashDrawRevTrx(String trxXml) throws TransactionException;
}
