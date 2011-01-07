package com.argility.cashtill.trx.main;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.argility.cashtill.context.CashtillCtxFactory;
import com.argility.cashtill.service.CashtillService;
import com.argility.cashtill.trx.CreateCashtillTrx;
import com.argility.cashtill.trx.PettyCashDrawRevTrx;
import com.argility.cashtill.trx.PettyCashDrawTrx;
import com.argility.master.context.MasterCtxFactory;
import com.argility.master.trxengine.header.ActH01;
import com.argility.master.trxengine.header.RefToInfo01;
import com.argility.master.trxengine.header.UiD01;
import com.argility.master.trxengine.iface.XmlParserIface;
import com.argility.schema.cashtill.generated.PettyCashDraw;
import com.argility.schema.cashtill.generated.PettyCashDrawRev;
import com.argility.schema.cashtill.generated.TenderData01;

public class TestCashtillTrx {

	protected static Logger log = Logger
	.getLogger(TestCashtillTrx.class);
	
	public TestCashtillTrx() {
	}
	
	public CreateCashtillTrx getCreateCashtillTrx() {
		ActH01 actH01 = new ActH01();
		UiD01 uid = new UiD01();
		uid.setUserId("MERCH_MAIN");
		uid.setDeviceId("localhost");
		
		actH01.setUiD01(uid);
		
		CreateCashtillTrx trx = new CreateCashtillTrx();
		trx.setClientActH01(actH01);
		
		return trx;
	}
	
	public PettyCashDrawTrx getPettyCashDrawTrx() {
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
		tend.setTenderAmt(-20);
		
		draw.setGivenTo("marko");
		draw.setTenderData01(tend);
		
		trx.setPettyCashDraw(draw);
		
		return trx;
	}
	
	public PettyCashDrawRevTrx getPettyCashDrawRevTrx() {
		ActH01 actH01 = new ActH01();
		UiD01 uid = new UiD01();
		uid.setUserId("MERCH_MAIN");
		uid.setDeviceId("localhost");
		
		actH01.setUiD01(uid);
		
		PettyCashDrawRevTrx trx = new PettyCashDrawRevTrx();
		trx.setClientActH01(actH01);
		
		PettyCashDrawRev draw = new PettyCashDrawRev();
		TenderData01 tend = new TenderData01();
		tend.setTenderType(BigInteger.ONE);
		tend.setTenderAmt(20);
		
		draw.setTenderData01(tend);
		RefToInfo01 ref = new RefToInfo01();
		ref.setRefToId(1234);
		
		trx.getClientActH01().setRefToInfo01(ref);
		
		trx.setPettyCashDrawRev(draw);
		
		return trx;
	}
	
	public CreateCashtillTrx testCreateCashtillTrx() {
		
		CreateCashtillTrx trx = getCreateCashtillTrx();
		
		String trxXml = MasterCtxFactory.getInstance().getXmlParser().toXml(trx);
		
		CashtillService cashtillService = CashtillCtxFactory.getInstance().getCashtillService();
		cashtillService.createCashtillTrx(trxXml);
		
		//TransactionService service = MasterCtxFactory.getInstance().getTransactionService();
		//service.executeTransaction(trxXml);
		
		return trx;
	}
	
	public PettyCashDrawTrx testPettyCashDrawTrx() {
		
		PettyCashDrawTrx trx = getPettyCashDrawTrx();
		
		String trxXml = MasterCtxFactory.getInstance().getXmlParser().toXml(trx);
		
		log.info("TRX XML IS: \n" + trxXml);
		
		// We can use either the cashtill service or transaction service
		CashtillService cashtillService = CashtillCtxFactory.getInstance().getCashtillService();
		trx = cashtillService.pettyCashDrawTrx(trxXml);
		
		//TransactionService service = MasterCtxFactory.getInstance().getTransactionService();
		//service.executeTransaction(trxXml);
		
		return trx;
	}
	
	public PettyCashDrawTrx stressTestPettyCashDrawTrx() throws SQLException {
		
		MasterCtxFactory.getInstance().getBranchInfoService().getOwnBranchProfile();
		PettyCashDrawTrx trx = getPettyCashDrawTrx();
		
		String trxXml = MasterCtxFactory.getInstance().getXmlParser().toXml(trx);
		
		// We can use either the cashtill service or transaction service
		CashtillService cashtillService = CashtillCtxFactory.getInstance().getCashtillService();
		
		int loopCnt = 100;
		
		Date startDate = new Date();
		log.warn("STARTED: " + startDate);
		for (int x = 0; x< loopCnt; x++) {
			Date st = new Date();
			trx = cashtillService.pettyCashDrawTrx(trxXml);
			Date end = new Date();
			log.warn("Loop: " + x + " took " + (end.getTime() - st.getTime()) + " ms");
		}
		
		Date endDate = new Date();
		long diff = endDate.getTime() - startDate.getTime();
		
		log.warn("STARTED: " + startDate);
		log.warn("ENDED: " + endDate);
		log.warn("Time taken to process " + loopCnt + " transactions is : " + diff + " miliseconds");
		
		//TransactionService service = MasterCtxFactory.getInstance().getTransactionService();
		//service.executeTransaction(trxXml);
		
		return trx;
	}
	
	public PettyCashDrawRevTrx testPettyCashDrawRevTrx() {
		
		PettyCashDrawRevTrx trx = getPettyCashDrawRevTrx();
		trx.getClientActH01().getRefToInfo01().setRefToId(2874093);
		
		String trxXml = MasterCtxFactory.getInstance().getXmlParser().toXml(trx);
		
		// We can use either the cashtill service or transaction service
		CashtillService cashtillService = CashtillCtxFactory.getInstance().getCashtillService();
		trx = cashtillService.pettyCashDrawRevTrx(trxXml);
		
		//TransactionService service = MasterCtxFactory.getInstance().getTransactionService();
		//service.executeTransaction(trxXml);
		
		return trx;
	}


	public void createDrawAndReversal() {
		CashtillService cashtillService = CashtillCtxFactory.getInstance().getCashtillService();
		
		// First do a petty cash draw
		PettyCashDrawTrx trxDraw = getPettyCashDrawTrx();
		String drawXml = MasterCtxFactory.getInstance().getXmlParser().toXml(trxDraw);
		
		trxDraw = cashtillService.pettyCashDrawTrx(drawXml);
		
		// Then reverse it
		PettyCashDrawRevTrx trxRev = getPettyCashDrawRevTrx();
		trxRev.getClientActH01().getRefToInfo01().setRefToId(trxDraw.getAuditId());
		String revXml = MasterCtxFactory.getInstance().getXmlParser().toXml(trxRev);
		
		trxRev = cashtillService.pettyCashDrawRevTrx(revXml);
	}

	public void stressTestPettyCash() throws Exception {
		CashtillService cashtillService = CashtillCtxFactory.getInstance().getCashtillService();
		XmlParserIface xmlParser = MasterCtxFactory.getInstance().getXmlParser();
		MasterCtxFactory.getInstance().getBranchInfoService().getOwnBranchProfile();
		
		PettyCashDrawTrx trxDraw = null;
		PettyCashDrawRevTrx trxRev = null;
		
		String drawXml = null;
		String revXml = null;
		
		int loopCnt = 50;
		
		Date startDate = new Date();
		log.info("STARTED: " + startDate);
		for (int x = 0; x< loopCnt; x++) {
			trxDraw = getPettyCashDrawTrx();
			drawXml = xmlParser.toXml(trxDraw);
			trxDraw = cashtillService.pettyCashDrawTrx(drawXml);
			
			trxRev = getPettyCashDrawRevTrx();
			trxRev.getClientActH01().getRefToInfo01().setRefToId(trxDraw.getAuditId());
			revXml = xmlParser.toXml(trxRev);
			trxRev = cashtillService.pettyCashDrawRevTrx(revXml);
		}
		
		Date endDate = new Date();
		long diff = endDate.getTime() - startDate.getTime();
		
		log.info("STARTED: " + startDate);
		log.info("ENDED: " + endDate);
		log.info("Time taken to process " + (loopCnt * 2) + " transactions is : " + diff + " miliseconds");
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestCashtillTrx tt = new TestCashtillTrx();
		
		try {
			// init context and branch profile
			MasterCtxFactory.getInstance().getBranchInfoService().getOwnBranchProfile();
			
			//tt.testCreateCashtillTrx();
			tt.testPettyCashDrawTrx();
			//tt.testPettyCashDrawRevTrx();
			//tt.createDrawAndReversal();
			//tt.stressTestPettyCash();
			//tt.stressTestPettyCashDrawTrx();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Unhash to use the jconsole tool
		/*try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}

}
