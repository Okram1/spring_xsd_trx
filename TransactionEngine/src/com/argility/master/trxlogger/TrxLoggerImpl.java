package com.argility.master.trxlogger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.argility.master.service.AbstractService;
import com.argility.master.trxengine.iface.TransactionInterface;

public class TrxLoggerImpl extends AbstractService implements TrxLogger{

	protected transient Logger log = Logger
		.getLogger(this.getClass().getName());
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void logTransactionError(String type, Throwable e, TransactionInterface trx) {
		log.info("logTransactionError("+type+"," + e.getMessage() + ")");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		String sql = "INSERT INTO tran_exception_log (class, " +
				"error_type," +
				"act_typ, " +
				"message, " +
				"error_time, " +
				"aud_xml, " +
				"aud_replication, " +
				"build, " +
				"stackTrace) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		
		try {
			
			String cl = trx.getClass().getName();
			int actTyp = trx.getActionType();
			String message = null;
			String xml = trx.getAuditXml();
			String replication = trx.getReplicationXml();
			String build = null;
			String stack = getStackTraceString(e);
			
			if (trx.getActH01() != null && trx.getActH01().getTransData01() != null) {
				build = trx.getActH01().getTransData01().getBuildNumber();
			}
			
			if (e != null) {
				message = e.getMessage();
			}
			
			jdbcTemplate.update(sql, cl, type, actTyp, message, new Date(), xml, replication, build, stack);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private String getStackTraceString(Throwable e) {
		if (e == null) {
			return null;
		}
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		
		return writer.toString();

	}

}
