package com.argility.dao.jdbcimpl.base;

import com.argility.master.dao.AbstractSpringJdbcDAO;
import com.argility.dao.entity.CashTillEntity;

// THIS IS AN AUTOGENERATED CLASS
public class CashTillBaseJdbcImplDAO extends AbstractSpringJdbcDAO<CashTillEntity> {

	public static final String TABLE_NAME = "cash_till";
	public static final String[] COLUMN_NAMES = new String[] {
			"usr_id",
			"ctill_lst_csh",
			"ctill_float",
			"ctill_amt",
			"ctill_mend_amt",
			"ctill_lst_eos",
			"ctill_prev_eos"
			};

	public static final String[] PK_COLUMNS = new String[] {
			"usr_id"
			};

	public static final String[] INCREMENT_ON_UPDATE_COLUMNS = new String[] {
			"ctill_amt"
			};




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
