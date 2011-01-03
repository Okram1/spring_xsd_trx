package com.argility.dao.context;

import com.argility.master.service.SpringContextFactory;

public class DaoCtxFactoryImpl extends DaoCtxFactory {

	@Override
	public Object getDao(Class<?> dao) {
		return SpringContextFactory.getApplicationContext().getBean(dao);
	}
	

}
