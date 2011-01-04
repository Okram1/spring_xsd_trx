package com.argility.master.trxengine;

import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.argility.master.audit.AuditService;
import com.argility.master.branch.BranchInfo;
import com.argility.master.context.MasterCtxFactory;
import com.argility.master.service.AbstractService;
import com.argility.master.trxengine.header.ActH01;
import com.argility.master.trxengine.header.SeqD01;
import com.argility.master.trxengine.header.TransData01;
import com.argility.master.trxengine.iface.TransactionInterface;
import com.argility.master.trxengine.iface.TransactionService;
import com.argility.master.trxengine.iface.exception.TransactionException;
import com.argility.master.trxengine.iface.exception.ValidationFailedException;

/**
 * Transaction service allows us to execute transactions while encapsulating all
 * default behavior such as writing to audit, extracting xml and validating the
 * xml, setting sequences etc... Any behavior that needs to be common accross
 * all transactions can and must be added to this service.
 * <p>
 */
@Transactional
public class TransactionServiceImpl extends AbstractService implements
		TransactionService {

	public TransactionServiceImpl() {
	}

	public TransactionInterface executeTransaction(String xml)
			throws TransactionException, ValidationFailedException {
		TransactionInterface trx = (TransactionInterface) getXmlParser()
				.fromXml(xml);
		getXmlParser().validate(xml, trx.getSchemaLocation());
		return executeTransaction(trx, false);
	}
	
	public TransactionInterface executeTransaction(TransactionInterface trx)
		throws TransactionException, ValidationFailedException {
		return executeTransaction(trx, true);
	}


	private TransactionInterface executeTransaction(TransactionInterface trx, boolean validate)
			throws TransactionException, ValidationFailedException {

		String auditXml = null;
		AuditService auditService = null;
		
		try {

			// Make sure that we are receiving the client header and not the server
			if (trx.getClientActH01() == null) {
				throw new TransactionException("VALIDATION", new Exception("No ClientActH01 on the transaction...\n "), trx);
			}
			
			// Validate the client xml before execution, only if we haven't validated already
			if (validate) getXmlParser().validateTrx(trx);
			
			// Retrieve the auditing service
			auditService = MasterCtxFactory.getInstance().getAuditService();

			// Run conversion from client action header to a server header
			buildServerActH01(trx);
			
			// Insert the audit table entry
			auditService.insertAudit(trx);

			// Execute the transaction specific logic
			trx.executeWrite();

			// We could use the tran profile and header to set certain flags
			// and/or some tags that would trigger an automatic write to
			// relevant tables, such as if oboDetails exist then use them etc...
			executeTranDefaultUpdates(trx);

			// Get the audit xml, at the same time run validation of the server xml against an xsd
			auditXml = getXmlParser().toXml(trx, trx.getSchemaLocation());

			// Set the audit xml on the transaction
			trx.setAuditXml(auditXml);
			
			// Set replication xml on the transaction
			trx.setReplicationXml(getReplicationXml(trx));

			// Update the audit and set missing fields including aud_xml and
			// replication_xml
			auditService.updateAuditRepicationAndXml(trx.getAuditXml(),
					trx.getReplicationXml(), trx.getAuditId());

			// Print what the final transaction audxml/rep is
			log.info("REPLICATION IS: \n" + trx.getReplicationXml());
			log.info("AUDIT XML IS: \n" + trx.getAuditXml());

		} catch (SQLException e) {
			e.printStackTrace();
			throw new TransactionException("SQL" ,e, trx); // Will log to the tran_exception_log table
		} catch (ValidationFailedException e) {
			e.printStackTrace();
			throw new ValidationFailedException("VALIDATION", e, trx); // Will log to the tran_exception_log table
		} catch (TransactionException e) {
			e.printStackTrace();
			throw new TransactionException("TRANSACTION", e , trx); // Will log to the tran_exception_log table
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getCause() != null && e.getCause() instanceof SQLException) {
				throw new TransactionException("SQL" , e, trx); // Will log to the tran_exception_log table
			}
			throw new TransactionException("UNKNOWN", e , trx); // Will log to the tran_exception_log table
		} 

		return trx;

	}

	private void buildServerActH01(TransactionInterface trx) throws SQLException {
		ActH01 clientHeader = trx.getClientActH01();
		ActH01 serverHeader = new ActH01();
		
		// Copy common elements over to the server header
		serverHeader.setDocValueData01(clientHeader.getDocValueData01());
		serverHeader.setOboInData01(clientHeader.getOboInData01());
		serverHeader.setOboOutData01(clientHeader.getOboOutData01());
		serverHeader.setRefToInfo01(clientHeader.getRefToInfo01());
		serverHeader.setUiD01(clientHeader.getUiD01());
		
		// Set the server header
		trx.setActH01(serverHeader);
		
		// Populate sequence data such as audit id, fpp cde etc...
		serverHeader.setSeqD01(getSeqData01(trx));
		
		// Populate tran data such as act typ, doc no...
		serverHeader.setTransData01(getTransData01(trx));
		
		// Nullify the client header
		trx.setClientActH01(null);
		
	}
	
	private TransData01 getTransData01(TransactionInterface trx) {
		
		TransData01 clientData = trx.getClientActH01().getTransData01();
		TransData01 tranData = new TransData01();
		tranData.setActionType(trx.getActionType());
		tranData.setDocNo(trx.getAuditId()+"");
		
		// If client data is not empty, reuse the required fields
		if (clientData != null) {
			tranData.setDocNo(clientData.getDocNo());
			tranData.setDocDate(clientData.getDocDate());
			tranData.setRefDocNo(clientData.getRefDocNo());
			tranData.setIsCredit(clientData.isIsCredit());
			tranData.setIsFinancial(clientData.isIsFinancial());
		} else {
			tranData.setDocDate(new Date().getTime());
			tranData.setDocNo(trx.getAuditId()+"");
		}
		
		
		return tranData;
	}
	
	private SeqD01 getSeqData01(TransactionInterface trx) throws SQLException {
		SeqD01 seqData = new SeqD01();
		
		BranchInfo brInfo = MasterCtxFactory.getInstance().getBranchInfoService().getOwnBranchProfile();
		
		// Set an audit sequence on the transaction
		seqData.setAudit(SequenceFinder.getAuditSequence(getConnection()));
		seqData.setBranchCode(brInfo.getBrCde());
		seqData.setGrpCode(brInfo.getGrpCde());
		seqData.setFinProcPer(brInfo.getFppCde());
		
		return seqData;
	}
	
	public void executeTranDefaultUpdates(TransactionInterface trx) throws SQLException {
		
		// If we have obo details then this must be a switching transaction and we must write into obo_hist
		if (trx.getActH01().getOboInData01() != null) {
			MasterCtxFactory.getInstance().getAuditService().insertOboHistEntry(trx);
		}
	}
	
	private String getReplicationXml(TransactionInterface trx) {
		String xml = "<replication>";
		List<String> statements = trx.getActH01().getSqlStatements();
		for (Iterator<String> iterator = statements.iterator(); iterator.hasNext();) {
			String data = (String) iterator.next();
			xml += "<PS>" + data + "</PS>";
		}
		xml += "</replication>";
		return xml;
	}

}
