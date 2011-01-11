package com.argility.master.trxengine;

import org.apache.log4j.Logger;

import com.argility.master.trxengine.header.ActH01;
import com.argility.master.trxengine.iface.TransactionInterface;

/**
 * This is the abstract implementation of TransactionInterface, being abstract
 * the class allows us to implements some default behavior while still allowing
 * us to force implementation of the executeWrite method
 * <p>
 */

public abstract class AbstractTransaction implements TransactionInterface {

	protected transient Logger log = Logger
			.getLogger(this.getClass().getName());

	private ActH01 actH01;
	private ActH01 clientActH01;
	private String auditXml;
	private String replicationXml;

	/**
	 * The class will create a default TranProfile by calling the
	 * getDefaultTranProfile() abstract method from the constructor
	 * <p>
	 */
	public AbstractTransaction() {
	}

	public ActH01 getActH01() {
		return actH01;
	}

	public void setActH01(ActH01 actH01) {
		this.actH01 = actH01;
	}

	public String getAuditXml() {
		return auditXml;
	}

	public int getAuditId() {
		Integer audId = getActH01().getSeqD01().getAudit();
		if (audId != null) {
			return audId.intValue();
		}

		return -1;
	}

	public void setAuditXml(String auditXml) {
		this.auditXml = auditXml;
	}

	public String getReplicationXml() {
		return replicationXml;
	}

	public void setReplicationXml(String replicationXml) {
		this.replicationXml = replicationXml;
	}

	public abstract String getSchemaLocation();

	public ActH01 getClientActH01() {
		return clientActH01;
	}

	public void setClientActH01(ActH01 clientActH01) {
		this.clientActH01 = clientActH01;
	}
	
	public String getUsrId() {
		return getActH01().getUiD01().getUserId();
	}
}
