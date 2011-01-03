package com.argility.master.trxengine.iface.tdo;

/**
 * Transaction profile, this class stored info about the transaction such as the
 * audit, action type and the schema used to validate the transaction
 * <p>
 */
public class TranProfile {

	private int auditId = -1;
	private Integer actionTyp;
	private String schemaLocation;
	private boolean financial;
	private boolean credit;
	private boolean cash;

	public TranProfile() {
	}

	public TranProfile(Integer actionTyp, String schemaLocation) {
		this.actionTyp = actionTyp;
		this.schemaLocation = schemaLocation;
	}

	public Integer getActionTyp() {
		return actionTyp;
	}

	public void setActionTyp(Integer actionTyp) {
		this.actionTyp = actionTyp;
	}

	public int getAuditId() {
		return auditId;
	}

	public void setAuditId(int auditId) {
		this.auditId = auditId;
	}

	public String getSchemaLocation() {
		return schemaLocation;
	}

	public void setSchemaLocation(String schemaLocation) {
		this.schemaLocation = schemaLocation;
	}

	public boolean isFinancial() {
		return financial;
	}

	public void setFinancial(boolean financial) {
		this.financial = financial;
	}

	public boolean isCredit() {
		return credit;
	}

	public void setCredit(boolean credit) {
		this.credit = credit;
	}

	public boolean isCash() {
		return cash;
	}

	public void setCash(boolean cash) {
		this.cash = cash;
	}
}
