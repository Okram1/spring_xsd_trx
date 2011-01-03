package com.argility.cashtill.context;

import com.argility.cashtill.service.CashtillService;

public class CashtillCtxFactoryImpl extends CashtillCtxFactory {

	private CashtillService cashtillService;
	
	@Override
	public CashtillService getCashtillService() {
		return cashtillService;
	}

	public void setCashtillService(CashtillService cashtillService) {
		this.cashtillService = cashtillService;
	}
	
}
