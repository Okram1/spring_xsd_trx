package com.argility.cashtill.trx.main;

import java.math.BigInteger;

import com.argility.cashtill.context.CashtillCtxFactory;
import com.argility.cashtill.service.CashtillService;
import com.argility.cashtill.trx.CreateCashtillTrx;
import com.argility.cashtill.trx.PettyCashDrawTrx;
import com.argility.master.context.MasterCtxFactory;
import com.argility.master.trxengine.header.ActH01;
import com.argility.master.trxengine.header.UiD01;
import com.argility.schema.cashtill.generated.PettyCashDraw;
import com.argility.schema.cashtill.generated.TenderData01;

public class TestCashtillTrx {

	public TestCashtillTrx() {
	}
	
	public CreateCashtillTrx testCreateCashtillTrx() {
		ActH01 actH01 = new ActH01();
		UiD01 uid = new UiD01();
		uid.setUserId("MERCH_MAIN");
		uid.setDeviceId("localhost");
		
		actH01.setUiD01(uid);
		
		CreateCashtillTrx trx = new CreateCashtillTrx();
		trx.setClientActH01(actH01);
		
		String trxXml = MasterCtxFactory.getInstance().getXmlParser().toXml(trx);
		
		CashtillService cashtillService = CashtillCtxFactory.getInstance().getCashtillService();
		cashtillService.createCashtillTrx(trxXml);
		
		//TransactionService service = MasterCtxFactory.getInstance().getTransactionService();
		//service.executeTransaction(trxXml);
		
		return trx;
	}
	
	public PettyCashDrawTrx testPettyCashDrawTrx() {
		ActH01 actH01 = new ActH01();
		UiD01 uid = new UiD01();
		uid.setUserId("MERCH_MAIN");
		uid.setDeviceId("localhost");
		
		actH01.setUiD01(uid);
		
		PettyCashDrawTrx trx = new PettyCashDrawTrx();
		trx.setClientActH01(actH01);
		
		PettyCashDraw draw = new PettyCashDraw();
		TenderData01 tend = new TenderData01();
		tend.setTenderType(BigInteger.ONE);
		tend.setTenderAmt(20);
		
		draw.setGivenTo("marko");
		draw.setTenderData01(tend);
		
		trx.setPettyCashDraw(draw);
		
		String trxXml = MasterCtxFactory.getInstance().getXmlParser().toXml(trx);
		
		// We can use either the cashtill service or transaction service
		CashtillService cashtillService = CashtillCtxFactory.getInstance().getCashtillService();
		cashtillService.pettyCashDrawTrx(trxXml);
		
		//TransactionService service = MasterCtxFactory.getInstance().getTransactionService();
		//service.executeTransaction(trxXml);
		
		return trx;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestCashtillTrx tt = new TestCashtillTrx();
		//tt.testCreateCashtillTrx();
		tt.testPettyCashDrawTrx();

	}

}
