package com.argility.test;

import java.util.Date;

import com.argility.master.trxengine.header.ActH01;
import com.argility.master.trxengine.header.TransData01;
import com.argility.master.trxengine.header.UiD01;
import com.argility.master.trxengine.iface.tdo.ActionHeader;
import com.argility.master.trxengine.iface.tdo.ClientData;
import com.argility.master.trxengine.iface.tdo.ClientHeader;
import com.argility.master.trxengine.iface.tdo.OboData;

public class ActionHeaderTestFactory {

	public static ClientHeader getClientHeader() {
		ClientData cd = getClientData();
		OboData oboData = getOboData();

		ClientHeader clientHeader = new ClientHeader();
		clientHeader.setClientData(cd);
		clientHeader.setOboData(oboData);
		clientHeader.setCreateDate(new Date().getTime());

		return clientHeader;
	}

	public static OboData getOboData() {
		OboData oboData = new OboData();
		oboData.setOboBrCde("0011");
		oboData.setOboAudId(1233112);

		return oboData;
	}

	public static ClientData getClientData() {
		ClientData cd = new ClientData();
		cd.setUserId("madm9188");
		cd.setDeviceId("localhost");

		return cd;

	}

	public static ActionHeader getActionHeader() {
		ClientHeader clientHeader = getClientHeader();

		ActionHeader ah = new ActionHeader();
		ah.setClientHeader(clientHeader);
		ah.setBuildNumber("78_11");

		return ah;
	}

	public static ActH01 getClientActH01() {
		ActH01 data = new ActH01();
		data.setUiD01(getUiD01());
		return data;
	}
	
	public static ActH01 getActH01() {
		ActH01 data = new ActH01();
		data.setUiD01(getUiD01());
		data.setTransData01(getTransData01());
		return data;
	}

	public static UiD01 getUiD01() {
		UiD01 data = new UiD01();
		data.setUserId("madm9188");
		data.setDeviceId("localhost");

		return data;
	}
	
	public static TransData01 getTransData01() {
		TransData01 data = new TransData01();
		return data;
	}
}
