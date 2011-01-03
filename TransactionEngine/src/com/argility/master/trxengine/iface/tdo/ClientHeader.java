package com.argility.master.trxengine.iface.tdo;

/**
 * Client header would come from the invoking client and must have certain information such as the user and device
 * <p>
 */
public class ClientHeader {

	private ClientData clientData;
	private OboData oboData;
	private Long createDate;
	
	public ClientHeader() {
	}

	public ClientData getClientData() {
		return clientData;
	}

	public void setClientData(ClientData clientData) {
		this.clientData = clientData;
	}

	public OboData getOboData() {
		return oboData;
	}

	public void setOboData(OboData oboData) {
		this.oboData = oboData;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

}
