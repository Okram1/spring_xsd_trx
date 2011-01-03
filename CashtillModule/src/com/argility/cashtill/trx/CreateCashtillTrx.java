package com.argility.cashtill.trx;

import com.argility.dao.CashTillDAO;
import com.argility.dao.entity.CashTillEntity;
import com.argility.master.trxengine.iface.TransactionInterface;
import com.argility.master.trxengine.iface.exception.TransactionException;

public class CreateCashtillTrx extends BasicCashtillTrx {

	public static int ACTION_TYP = 106000; 
	
	@Override
	public int getActionType() {
		return ACTION_TYP;
	}

	@Override
	public TransactionInterface executeWrite()
			throws TransactionException {
		
		CashTillDAO cashtillDao = getCashTillDAO();
		
		CashTillEntity entity = new CashTillEntity(getActH01().getUiD01().getUserId());
		
		//cashtillDao.deleteEntity(getActH01(), entity);
		cashtillDao.insertEntity(getActH01(), entity, true);
		
		return this;
	}

}
