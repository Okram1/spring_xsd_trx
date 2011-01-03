package com.argility.master.branch;

/**
 * 
 * @author marko.salic
 * This object holds information about a branch
 *
 */
public class BranchInfo {

	private String brCde;
	private String grpCde;
	private String fppCde;
	
	public BranchInfo() {
	}

	public String getBrCde() {
		return brCde;
	}

	public void setBrCde(String brCde) {
		this.brCde = brCde;
	}

	public String getGrpCde() {
		return grpCde;
	}

	public void setGrpCde(String grpCde) {
		this.grpCde = grpCde;
	}

	public String getFppCde() {
		return fppCde;
	}

	public void setFppCde(String fppCde) {
		this.fppCde = fppCde;
	}

}
