package com.argility.cashtill.trx;

import com.argility.dao.entity.CashTillEntity;
import com.argility.dao.entity.CashTranEntity;
import com.argility.dao.entity.CupTendEntity;
import com.argility.dao.entity.PettyDrawEntity;
import com.argility.master.trxengine.iface.TransactionInterface;
import com.argility.master.trxengine.iface.exception.TransactionException;
import com.argility.schema.cashtill.generated.PettyCashDraw;
import com.argility.schema.cashtill.generated.TenderData01;

/**
 * Transaction is executed when a petty cash withdrawal is done
 * @author Mare
 *
 */
public class PettyCashDrawTrx extends BasicCashtillTrx {

	public static int ACTION_TYP = 1235;
	
	private PettyCashDraw pettyCashDraw;
	
	@Override
	public int getActionType() {
		return ACTION_TYP;
	}

	@Override
	public TransactionInterface executeWrite() throws TransactionException {
		
		try {
			
			TenderData01 tender = getPettyCashDraw().getTenderData01();
			String usrId = getActH01().getUiD01().getUserId();
			
			// Build entities
			CashTranEntity ctranEnt = getCashTillEntity(usrId, tender.getTenderAmt(), 0.0, false);
			CashTillEntity ctillEnt = getUpdateCashTillEntity(usrId, tender.getTenderAmt());
			CupTendEntity cupTendEnt = getCupTendEntity(tender);
			
			PettyDrawEntity pettyDraw = new PettyDrawEntity(usrId, getAuditId());
			
			// Execute the table updates
			getCashTranDAO().insertEntity(getActH01(), ctranEnt, true);
			getCashTillDAO().updateEntity(getActH01(), ctillEnt, true);
			getCupTendDAO().insertEntity(getActH01(), cupTendEnt, true);
			getPettyDrawDAO().insertEntity(getActH01(), pettyDraw);
			
			//if (true) throw new TransactionException("Stopped");
			
		} catch (Exception e) {
			throw new TransactionException(e.getMessage());
		}
		
		return this;
	}

	public PettyCashDraw getPettyCashDraw() {
		return pettyCashDraw;
	}

	public void setPettyCashDraw(PettyCashDraw pettyCashDraw) {
		this.pettyCashDraw = pettyCashDraw;
	}

}
