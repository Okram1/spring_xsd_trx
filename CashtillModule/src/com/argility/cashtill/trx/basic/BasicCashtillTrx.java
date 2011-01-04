package com.argility.cashtill.trx.basic;

import com.argility.dao.CashTillDAO;
import com.argility.dao.CashTranDAO;
import com.argility.dao.CupTendDAO;
import com.argility.dao.PettyDrawDAO;
import com.argility.dao.context.DaoCtxFactory;
import com.argility.dao.entity.CashTillEntity;
import com.argility.dao.entity.CashTranEntity;
import com.argility.dao.entity.CupTendEntity;
import com.argility.master.trxengine.AbstractTransaction;
import com.argility.schema.cashtill.generated.TenderData01;
import com.argility.schema.context.SchemaCtxFactory;

/**
 * Base cashtill transaction, should be used to add all common cashtill logic 
 * that can be shared by all cashtill transaction, such as getting the cashtill DAO's
 * and building common cashtill entities such as cash_tran... Schema locations.
 * 
 * @author marko.salic
 *
 */
public abstract class BasicCashtillTrx extends AbstractTransaction {

	@Override
	public String getSchemaLocation() {
		return SchemaCtxFactory.getInstance().getCashtillSchemaLocation();
	}
	
	protected CashTillDAO getCashTillDAO() {
		return (CashTillDAO)DaoCtxFactory.getInstance().getDao(CashTillDAO.class);
	}
	
	protected CashTranDAO getCashTranDAO() {
		return (CashTranDAO)DaoCtxFactory.getInstance().getDao(CashTranDAO.class);
	}
	
	protected CupTendDAO getCupTendDAO() {
		return (CupTendDAO)DaoCtxFactory.getInstance().getDao(CupTendDAO.class);
	}
	
	protected PettyDrawDAO getPettyDrawDAO() {
		return (PettyDrawDAO)DaoCtxFactory.getInstance().getDao(PettyDrawDAO.class);
	}
	
	protected CashTranEntity getCashTillEntity(String usrId, Double amount, Double tax, boolean isCashup) {
		CashTranEntity ctranEnt = new CashTranEntity(usrId, 
				getAuditId(), 
				null, 
				amount, 
				tax, 
				getActH01().getTransData01().isIsFinancial(), 
				usrId, 
				isCashup);
		
		return ctranEnt;

	}
	
	protected CashTillEntity getUpdateCashTillEntity(String usrId, Double amount) {
		CashTillEntity ctillEnt = new CashTillEntity(usrId);
		ctillEnt.setCtillAmt(amount);
		
		return ctillEnt;

	}
	
	protected CupTendEntity getCupTendEntity(TenderData01 tender) {
		CupTendEntity cupTendEnt = new CupTendEntity();
		cupTendEnt.setAudId(getAuditId());
		cupTendEnt.setTendTyp(tender.getTenderType().intValue());
		cupTendEnt.setCupTend(tender.getTenderAmt());
		
		return cupTendEnt;

	}


}
