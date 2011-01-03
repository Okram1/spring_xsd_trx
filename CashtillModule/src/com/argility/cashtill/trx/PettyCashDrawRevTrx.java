package com.argility.cashtill.trx;

import com.argility.dao.entity.CashTillEntity;
import com.argility.dao.entity.CashTranEntity;
import com.argility.dao.entity.CupTendEntity;
import com.argility.dao.entity.PettyDrawEntity;
import com.argility.master.trxengine.iface.TransactionInterface;
import com.argility.master.trxengine.iface.exception.TransactionException;
import com.argility.schema.cashtill.generated.PettyCashDrawRev;
import com.argility.schema.cashtill.generated.TenderData01;

public class PettyCashDrawRevTrx extends BasicCashtillTrx {

	public static int ACTION_TYPE = 4321;
	
	private PettyCashDrawRev pettyCashDrawRev;
	
	public PettyCashDrawRev getPettyCashDrawRev() {
		return pettyCashDrawRev;
	}

	public void setPettyCashDrawRev(PettyCashDrawRev pettyCashDrawRev) {
		this.pettyCashDrawRev = pettyCashDrawRev;
	}

	@Override
	public int getActionType() {
		return ACTION_TYPE;
	}

	@Override
	public TransactionInterface executeWrite() throws TransactionException {
		
		try {
			TenderData01 tender = getPettyCashDrawRev().getTenderData01();
			String usrId = getActH01().getUiD01().getUserId();
			int refAudit = getActH01().getRefToInfo01().getRefToId();
			
			CashTranEntity ctranEnt = getCashTillEntity(usrId, tender.getTenderAmt(), 0.0, false);
			CashTillEntity ctillEnt = getUpdateCashTillEntity(usrId, tender.getTenderAmt());
			CupTendEntity cupTendEnt = getCupTendEntity(tender);
			
			PettyDrawEntity pettyDraw = new PettyDrawEntity(usrId, refAudit);
			
			// Execute the table updates
			getCashTranDAO().insertEntity(getActH01(), ctranEnt, true);
			getCashTillDAO().updateEntity(getActH01(), ctillEnt, true);
			getCupTendDAO().insertEntity(getActH01(), cupTendEnt, true);
			getPettyDrawDAO().deleteEntity(getActH01(), pettyDraw, new String[] {"usr_id","aud_id"});
			
			//if (true) throw new TransactionException("Stopped draw rev");

		} catch (Exception e) {
			e.printStackTrace();
			throw new TransactionException(e.getMessage());
		}
		
		return null;
	}

}
