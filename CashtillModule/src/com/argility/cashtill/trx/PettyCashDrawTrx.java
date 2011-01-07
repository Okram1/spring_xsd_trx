package com.argility.cashtill.trx;

import com.argility.cashtill.trx.basic.BasicCashtillTrx;
import com.argility.dao.entity.CashTillEntity;
import com.argility.dao.entity.CashTranEntity;
import com.argility.dao.entity.CupTendEntity;
import com.argility.dao.entity.PettyDrawEntity;
import com.argility.master.trxengine.header.ActH01;
import com.argility.master.trxengine.iface.TransactionInterface;
import com.argility.master.trxengine.iface.exception.TransactionException;
import com.argility.schema.cashtill.generated.PettyCashDraw;
import com.argility.schema.cashtill.generated.TenderData01;

/**
 * Transaction is executed when a petty cash withdrawal is done
 * @author marko.salic
 *
 */
public class PettyCashDrawTrx extends BasicCashtillTrx {

	public static int ACTION_TYP = 1235;
	
	// Object holding data required for this transaction
	private PettyCashDraw pettyCashDraw;
	
	@Override
	public int getActionType() {
		return ACTION_TYP;
	}

	@Override
	public TransactionInterface executeWrite() throws TransactionException {
		
		try {
			ActH01 actH01 = getActH01();
			TenderData01 tender = getPettyCashDraw().getTenderData01();
			String usrId = actH01.getUiD01().getUserId();
			
			if (tender.getTenderAmt() > 0) {
				tender.setTenderAmt(tender.getTenderAmt() * -1);
			}
			
			// Build entities we want to persist to the database
			// Write these values into cash_tran
			CashTranEntity ctranEnt = getCashTillEntity(usrId, tender.getTenderAmt(), 0.0, false);
			// Write these values into cash_till
			CashTillEntity ctillEnt = getUpdateCashTillEntity(usrId, tender.getTenderAmt());
			// Write these values into cup_tend
			CupTendEntity cupTendEnt = getCupTendEntity(tender);
			// Write these values into petty_draw
			PettyDrawEntity pettyDraw = new PettyDrawEntity(usrId, getAuditId());
			
			// Execute the table updates
			getCashTranDAO().insertEntity(actH01, ctranEnt, true);
			getCashTillDAO().updateEntity(actH01, ctillEnt, true);
			getCupTendDAO().insertEntity(actH01, cupTendEnt, true);
			getPettyDrawDAO().insertEntity(actH01, pettyDraw);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new TransactionException(e);
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
