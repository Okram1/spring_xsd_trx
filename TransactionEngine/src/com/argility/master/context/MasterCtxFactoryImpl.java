package com.argility.master.context;

import com.argility.master.audit.AuditService;
import com.argility.master.branch.BranchInfoService;
import com.argility.master.trxengine.iface.TransactionService;
import com.argility.master.trxengine.iface.XmlParserIface;
import com.argility.master.trxlogger.TrxLogger;

public class MasterCtxFactoryImpl extends MasterCtxFactory {

	private TransactionService transactionService;
	private BranchInfoService branchInfoService;
	private AuditService auditService;
	private TrxLogger trxLogger;
	private XmlParserIface xmlParser;
	
	@Override
	public BranchInfoService getBranchInfoService() {
		return branchInfoService;
	}
	
	public void setBranchInfoService(BranchInfoService branchInfoService) {
		this.branchInfoService = branchInfoService;
	}

	public TrxLogger getTrxLogger() {
		return trxLogger;
	}

	public void setTrxLogger(TrxLogger trxLogger) {
		this.trxLogger = trxLogger;
	}

	public TransactionService getTransactionService() {
		return transactionService;
	}

	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	public XmlParserIface getXmlParser() {
		return xmlParser;
	}

	public void setXmlParser(XmlParserIface xmlParser) {
		this.xmlParser = xmlParser;
	}

	public AuditService getAuditService() {
		return auditService;
	}

	public void setAuditService(AuditService auditService) {
		this.auditService = auditService;
	}

	
}
