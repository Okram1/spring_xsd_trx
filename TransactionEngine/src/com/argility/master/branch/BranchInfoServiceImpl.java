package com.argility.master.branch;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;

import com.argility.master.service.AbstractService;

public class BranchInfoServiceImpl extends AbstractService implements
		BranchInfoService {

	private static BranchInfo branchInfo;
	
	@Override
	public BranchInfo getOwnBranchProfile() throws SQLException {
		
		if (branchInfo == null) {
			branchInfo = reloadOwnBranchInfo();
		}
		
		return branchInfo;

	}

	private BranchInfo reloadOwnBranchInfo() {
		log.info("reloadOwnBranchInfo()");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		BranchInfo bi = jdbcTemplate.queryForObject(
				BranchInfoRowMapper.GET_OWN_BRANCH_INFO_SQL,
				new BranchInfoRowMapper());
		return bi;

	}

	@Override
	public BranchInfo forceReloadOwnBranchBrofile() throws SQLException {
		branchInfo = reloadOwnBranchInfo();
		return branchInfo;
	}

	@Override
	public BranchInfo getOtherBranchProfile(String brCde) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
		BranchInfo bi = jdbcTemplate.queryForObject(
				BranchInfoRowMapper.GET_OTHER_BRANCH_INFO_SQL,
				new Object[]{brCde},
				new BranchInfoRowMapper());
		return bi;
	}
	
}
