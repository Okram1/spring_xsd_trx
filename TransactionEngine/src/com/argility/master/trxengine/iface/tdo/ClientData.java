package com.argility.master.trxengine.iface.tdo;

public class ClientData {

	private String userId;
	private String deviceId;
	
	public ClientData() {
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}
