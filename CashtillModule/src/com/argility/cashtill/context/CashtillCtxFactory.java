package com.argility.cashtill.context;

import org.apache.log4j.Logger;

import com.argility.cashtill.service.CashtillService;
import com.argility.master.context.SpringContextFactory;

public abstract class CashtillCtxFactory {

	protected static Logger log = Logger.getLogger(CashtillCtxFactory.class.getName());
	
	private static String CONTEXT_NAME = "cashtillCtxFactory";
	private static CashtillCtxFactory instance;

	public static CashtillCtxFactory getInstance() {
		if (instance == null) {
			log.info("Creating a new instance of ServiceFactory");
			instance = SpringContextFactory.getApplicationContext().getBean(
					CONTEXT_NAME, CashtillCtxFactory.class);
		}
		
		return instance;
	}
	
	public abstract CashtillService getCashtillService();
	
	
}
