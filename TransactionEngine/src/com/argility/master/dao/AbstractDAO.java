package com.argility.master.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.argility.master.branch.BranchInfo;
import com.argility.master.context.MasterCtxFactory;

/**
 * 
 * @author marko.salic Abstract DAO is a class that all other dao's should
 *         extend, all common functionality to all DAO's goes into this template
 */
public abstract class AbstractDAO  {

	protected transient Logger log = Logger
			.getLogger(this.getClass().getName());

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedJdbcTemplate;

	public AbstractDAO() {
	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
		return namedJdbcTemplate;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Returns own branch profile
	 */
	public BranchInfo getOwnBranchInfo() throws SQLException {
		return MasterCtxFactory.getInstance().getBranchInfoService()
				.getOwnBranchProfile();
	}

	/**
	 * Returns other branch profile
	 */
	public BranchInfo getOtherBranchInfo(String brCde) throws SQLException {
		return MasterCtxFactory.getInstance().getBranchInfoService()
				.getOtherBranchProfile(brCde);
	}

}
