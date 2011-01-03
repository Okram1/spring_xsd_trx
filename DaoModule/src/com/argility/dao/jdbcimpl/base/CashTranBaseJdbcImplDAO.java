package com.argility.dao.jdbcimpl.base;

import com.argility.master.dao.AbstractSpringJdbcDAO;
import com.argility.dao.entity.CashTranEntity;

// THIS IS AN AUTOGENERATED CLASS
public class CashTranBaseJdbcImplDAO extends AbstractSpringJdbcDAO<CashTranEntity> {

	public static final String TABLE_NAME = "cash_tran";
	public static final String[] COLUMN_NAMES = new String[] {
			"usr_id",
			"aud_id",
			"ctran_ref_id",
			"ctran_amt",
			"ctran_tax",
			"ctran_fin",
			"ctran_usr_id",
			"ctran_is_cashup"
			};

	public static final String[] PK_COLUMNS = new String[] {};
	public static final String[] INCREMENT_ON_UPDATE_COLUMNS = new String[] {};



	@Override
	protected String getTableName() {
		return TABLE_NAME;
	}

	@Override
	protected String[] getColumnNames() {
		return COLUMN_NAMES;
	}

	@Override
	protected String[] getPrimaryKeyColumns() {
		return PK_COLUMNS;
	}

	@Override
	protected String[] getIncrementOnUpdateColumns() {
		return INCREMENT_ON_UPDATE_COLUMNS;
	}


}
