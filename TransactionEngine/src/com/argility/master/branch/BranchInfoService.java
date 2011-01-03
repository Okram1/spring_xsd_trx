package com.argility.master.branch;

import java.sql.SQLException;

/**
 * 
 * @author marko.salic Used to retrieve branch info for the current or other
 *         branches
 */
public interface BranchInfoService {

	/**
	 * 
	 * @return BranchInfo
	 * @throws SQLException
	 *             Get own branch info, if we have a previously saved instance a
	 *             new query will not be executed and that singleton instance
	 *             will be returned
	 */
	public BranchInfo getOwnBranchProfile() throws SQLException;

	/**
	 * 
	 * @return BranchInfo
	 * @throws SQLException
	 *             Get branch info for branch with the supplied brCde
	 */
	public BranchInfo getOtherBranchProfile(String brCde) throws SQLException;

	/**
	 * 
	 * @return BranchInfo
	 * @throws SQLException
	 *             This will force a reload of the cached own branchInfo instance
	 */
	public BranchInfo forceReloadOwnBranchBrofile() throws SQLException;
}
