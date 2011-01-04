package com.argility.master.audit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;

import com.argility.master.branch.BranchInfo;
import com.argility.master.service.AbstractService;
import com.argility.master.trxengine.header.ActH01;
import com.argility.master.trxengine.header.OboInData01;
import com.argility.master.trxengine.header.TransData01;
import com.argility.master.trxengine.header.UiD01;
import com.argility.master.trxengine.iface.TransactionInterface;

public class AuditServiceImpl extends AbstractService implements AuditService {

	private JdbcTemplate jdbcTemplate;
	
	public void insertAudit(TransactionInterface trx) throws SQLException {
		
		log.info("Inserting audit " + trx.getActH01().getSeqD01().getAudit());

		ActH01 actHeader = trx.getActH01();
		TransData01 tranData = actHeader.getTransData01();
		UiD01 uid = actHeader.getUiD01();
		OboInData01 oboIn = actHeader.getOboInData01();
		
		BranchInfo brInfo = getOwnBranchInfo();
		Date docDate = new Date(tranData.getDocDate());
		
		String insertAuditSql = "INSERT INTO audit(aud_id, " 
				+ "grp_cde, "
				+ "act_typ, " 
				+ "fpp_cde, " 
				+ "usr_id, " 
				+ "aud_device_id, "
				+ "obo_aud_id, " 
				+ "obo_br_cde, "
				+ "aud_build_number," 
				+ "br_cde, " 
				+ "aud_doc_no, "
				+ "aud_doc_ts) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		getJdbcTemplate().update(insertAuditSql, 
				trx.getAuditId(),
				brInfo.getGrpCde(),
				trx.getActionType(),
				brInfo.getFppCde(),
				uid.getUserId(),
				uid.getDeviceId(),
				oboIn != null ? oboIn.getOboAudId() : null,
				oboIn != null ? oboIn.getOboBrCde() : null,
				tranData.getBuildNumber(),
				brInfo.getBrCde(),
				tranData.getDocNo(),
				docDate);
		
		insertAudmth(trx);

	}
	
	public void updateAuditRepicationAndXml(String xml, String replication, int audId) throws SQLException {
		log.info("Updating audit " + audId);

		String updateAuditSql = "UPDATE audit SET aud_xml = ?, " +
				"aud_replication = ? " +
				"WHERE aud_id = ?";

		getJdbcTemplate().update(updateAuditSql, 
				xml, 
				replication, 
				audId);

	}
	
	@Override
	public void insertOboHistEntry(TransactionInterface trx) throws SQLException {
		
		OboInData01 oboIn = trx.getActH01().getOboInData01();
		String sql = "INSERT INTO obo_hist (oboh_br_cde, oboh_aud_id, aud_id) VALUES (?, ?, ?)";
		
		PreparedStatement ps = null;
		Connection conn = getConnection();
		
		ps = conn.prepareStatement(sql);
		ps.setString(1, oboIn.getOboBrCde());
		ps.setInt(2, oboIn.getOboAudId());
		ps.setInt(3, trx.getAuditId());
		
		ps.execute();
		
		trx.getActH01().addSqlStatement(ps.toString());
		
	}
	
	public void insertAudmth(TransactionInterface trx) {
		if (trx.getActionType() == 5155) {
			log.info("Type is 5155, not adding an audmth entry.");
			return;
		}
		
		getJdbcTemplate().update("INSERT INTO audmth (aud_id) VALUES (?)", new Object[] {trx.getAuditId()} );
	}

	public JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			jdbcTemplate = new JdbcTemplate(getDataSource());
		}
		return jdbcTemplate;
	}
}
