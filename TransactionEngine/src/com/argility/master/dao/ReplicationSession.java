package com.argility.master.dao;

import java.util.List;

public interface ReplicationSession {

	public List<String> getSqlStatements();
	
	public boolean addSqlStatement(String sql);
}
