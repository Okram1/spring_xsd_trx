package com.argility.master.trxengine.iface.tdo;

/**
 * This is a generic action header, it must be part of every transaction/audit generated 
 * <p>
 */
public class ActionHeader {

	/**
	 * Client header would come from the invoking client and must have certain information such as the user and device
	 * <p>
	 */
	private ClientHeader clientHeader;
	private String buildNumber;
	
	public ActionHeader() {
	}
	
	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public ClientHeader getClientHeader() {
		return clientHeader;
	}

	public void setClientHeader(ClientHeader clientHeader) {
		this.clientHeader = clientHeader;
	}


}
