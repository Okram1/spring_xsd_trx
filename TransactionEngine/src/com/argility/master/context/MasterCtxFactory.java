package com.argility.master.context;

import org.apache.log4j.Logger;

import com.argility.master.audit.AuditService;
import com.argility.master.branch.BranchInfoService;
import com.argility.master.trxengine.iface.TransactionService;
import com.argility.master.trxengine.iface.XmlParserIface;
import com.argility.master.trxlogger.TrxLogger;

/**
 * This factory is used to retrieve any master objects from the application context
 * @author marko.salic
 *
 */
public abstract class MasterCtxFactory {
	static Logger log = Logger.getLogger(MasterCtxFactory.class.getName());

	private static String CONTEXT_NAME = "masterCtxFactory";
	private static MasterCtxFactory instance;

	public static MasterCtxFactory getInstance() {
		if (instance == null) {
			log.info("Creating a new instance of MasterCtxFactory");
			instance = SpringContextFactory.getApplicationContext().getBean(
					CONTEXT_NAME, MasterCtxFactory.class);
		}

		return instance;
	}
	
	public abstract TransactionService getTransactionService();
	
	public abstract BranchInfoService getBranchInfoService();
	
	public abstract AuditService getAuditService();
	
	public abstract TrxLogger getTrxLogger();
	
	public abstract XmlParserIface getXmlParser();
}
