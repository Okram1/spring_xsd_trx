package com.argility.master.dao.sqlbuilder.rules;

import com.argility.master.dao.sqlbuilder.SqlBuilderException;

public abstract class SqlBuilderRule {

	protected SqlBuilderRule nextRule;

	public void setNextRule(SqlBuilderRule nextRule) {
		this.nextRule = nextRule;
	}

	public SqlBuilderRule getNextRule() {
		return nextRule;
	}
	
	public abstract void checkRules() throws SqlBuilderException;
	
}
