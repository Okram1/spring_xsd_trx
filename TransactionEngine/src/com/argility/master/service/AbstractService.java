package com.argility.master.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.annotation.Transactional;

import com.argility.master.branch.BranchInfo;
import com.argility.master.context.MasterCtxFactory;
import com.argility.master.trxengine.iface.TransactionService;
import com.argility.master.trxengine.iface.XmlParserIface;
import com.argility.master.trxlogger.TrxLogger;

/**
 * This is the base service that ALL other service should
 * extend, it encapsulates common logic and allows us to easily add
 * common functionality to all our services
 * @author marko.salic 
 */
@Transactional
public abstract class AbstractService {

	protected transient Logger log = Logger
			.getLogger(this.getClass().getName());

	private XmlParserIface xmlParser;
	private TransactionService transactionService;
	private DataSource dataSource;
	private TrxLogger trxLogger;

	/**
	 * If you need a connection DO NOT USE THIS datasource's getConnection method, use the getConnection
	 * method on this same class 
	 * @return
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 
	 * @return The method will return a connection using it's datasource, if we
	 *         are currently part of a transaction block the method retrieves
	 *         the current transactions connection object
	 */
	protected Connection getConnection() {
		Connection conn = null;

		conn = DataSourceUtils.getConnection(getDataSource());

		return conn;
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

	public TrxLogger getTrxLogger() {
		return trxLogger;
	}

	public void setTrxLogger(TrxLogger trxLogger) {
		this.trxLogger = trxLogger;
	}
	
	/**
	 * Returns own branch profile
	 */
	public BranchInfo getOwnBranchInfo() throws SQLException {
		return MasterCtxFactory.getInstance().getBranchInfoService()
				.getOwnBranchProfile();
	}

	/**
	 * Returns other branch profile
	 */
	public BranchInfo getOtherBranchInfo(String brCde) throws SQLException {
		return MasterCtxFactory.getInstance().getBranchInfoService()
				.getOtherBranchProfile(brCde);
	}

}
