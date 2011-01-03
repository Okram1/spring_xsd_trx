package com.argility.dao.context;

import org.apache.log4j.Logger;

import com.argility.master.service.SpringContextFactory;

public abstract class DaoCtxFactory {
	static Logger log = Logger.getLogger(DaoCtxFactory.class.getName());
	
	private static String CONTEXT_NAME = "daoCtxFactory";
	private static DaoCtxFactory instance;

	public static DaoCtxFactory getInstance() {
		if (instance == null) {
			log.info("Creating a new instance of ServiceFactory");
			instance = SpringContextFactory.getApplicationContext().getBean(
					CONTEXT_NAME, DaoCtxFactory.class);
		}
		
		return instance;
	}
	
	public abstract Object getDao(Class<?> dao);

}
