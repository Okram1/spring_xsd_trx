package com.argility.master.branch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BranchInfoRowMapper implements RowMapper<BranchInfo> {

	public static String COLUMN_SELECT = "SELECT br_cde, " +
	"			grp_cde, " +
	"			fpp_cde ";
	
	public static String GET_OWN_BRANCH_INFO_SQL = COLUMN_SELECT + " FROM br_prof JOIN branch USING (br_cde)";
	
	public static String GET_OTHER_BRANCH_INFO_SQL = COLUMN_SELECT + " FROM branch WHERE br_cde = ?";
	
	@Override
	public BranchInfo mapRow(ResultSet rs, int i) throws SQLException {
		BranchInfo info = new BranchInfo();
		
		info.setBrCde(rs.getString("br_cde"));
		info.setGrpCde(rs.getString("grp_cde"));
		info.setFppCde(rs.getString("fpp_cde"));

		return info;
	}

}
