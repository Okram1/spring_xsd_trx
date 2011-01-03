package com.argility.dao.context;

import com.argility.master.context.SpringContextFactory;

public class DaoCtxFactoryImpl extends DaoCtxFactory {

	@Override
	public Object getDao(Class<?> dao) {
		return SpringContextFactory.getApplicationContext().getBean(dao);
	}
	

}
