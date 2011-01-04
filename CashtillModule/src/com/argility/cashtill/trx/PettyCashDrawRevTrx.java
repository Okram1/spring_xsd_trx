package com.argility.cashtill.trx;

import com.argility.cashtill.trx.basic.BasicCashtillTrx;
import com.argility.dao.entity.CashTillEntity;
import com.argility.dao.entity.CashTranEntity;
import com.argility.dao.entity.CupTendEntity;
import com.argility.dao.entity.PettyDrawEntity;
import com.argility.master.trxengine.iface.TransactionInterface;
import com.argility.master.trxengine.iface.exception.TransactionException;
import com.argility.schema.cashtill.generated.PettyCashDrawRev;
import com.argility.schema.cashtill.generated.TenderData01;

/**
 * Used to reverse a petty cash withdrawal
 * @author marko.salic
 *
 */
public class PettyCashDrawRevTrx extends BasicCashtillTrx {

	public static int ACTION_TYPE = 4321;
	
	// Object holding data required for this transaction
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
			// Commons variables
			TenderData01 tender = getPettyCashDrawRev().getTenderData01();
			String usrId = getActH01().getUiD01().getUserId();
			int refAudit = getActH01().getRefToInfo01().getRefToId();
			
			// Build the entities we wish to persist
			CashTranEntity ctranEnt = getCashTillEntity(usrId, tender.getTenderAmt(), 0.0, false);
			CashTillEntity ctillEnt = getUpdateCashTillEntity(usrId, tender.getTenderAmt());
			CupTendEntity cupTendEnt = getCupTendEntity(tender);
			
			PettyDrawEntity pettyDraw = new PettyDrawEntity(usrId, refAudit);
			
			// Execute the table updates
			getCashTranDAO().insertEntity(getActH01(), ctranEnt, true);
			getCashTillDAO().updateEntity(getActH01(), ctillEnt, true);
			getCupTendDAO().insertEntity(getActH01(), cupTendEnt, true);
			
			// This table has no primary key, so we must specify the columns to be used in the WHERE condition
			getPettyDrawDAO().deleteEntity(getActH01(), pettyDraw, new String[] {"usr_id","aud_id"}); 
			
			//if (true) throw new TransactionException("Stopped draw rev");

		} catch (Exception e) {
			e.printStackTrace();
			throw new TransactionException(e.getMessage());
		}
		
		return null;
	}

}
