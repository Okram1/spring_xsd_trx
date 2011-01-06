package com.argility.cashtill.trx.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import com.argility.master.context.MasterCtxFactory;
import com.argility.master.context.SpringContextFactory;
import com.argility.master.trxengine.iface.TransactionInterface;
import com.argility.master.trxengine.iface.TransactionService;
import com.argility.master.trxengine.iface.exception.TransactionException;
import com.argility.master.trxengine.iface.exception.ValidationFailedException;

public class RunTrxCmdLine {

	protected static Logger log = Logger.getLogger(TestCashtillTrx.class);

	public RunTrxCmdLine() {
	}

	public String readFile(File f) throws IOException {
		Reader r = new FileReader(f);
		BufferedReader br = new BufferedReader(r);
		String data = "";
		String line = null;

		while ((line = br.readLine()) != null) {
			data += line;
		}

		return data;
	}

	public int runTransaction(String xml) throws TransactionException,
			ValidationFailedException {
		TransactionService tranService = MasterCtxFactory.getInstance()
				.getTransactionService();

		TransactionInterface ti = tranService.executeTransaction(xml);

		return ti.getActH01().getSeqD01().getAudit().intValue();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			log.info("USAGE: RunTrxCmdLine trx_xml_file");
			System.exit(0);
		}
		
		RunTrxCmdLine run = new RunTrxCmdLine();
		
		File f = new File(args[0]);
		if (!f.exists()) {
			log.info("Invalid file " + args[0]);
			System.exit(0);
		}
		
		String xml = null;
		try {
			xml = run.readFile(f);
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		
		int times = 1;
		if (args.length > 1) {
			times = Integer.parseInt(args[1]);
		}
		
		SpringContextFactory.getApplicationContext(); // Init context
		try {
			MasterCtxFactory.getInstance().getBranchInfoService().getOwnBranchProfile(); // init br prof
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		Date startDate = new Date();
		log.warn("STARTED: " + startDate);
		
		log.info("Executing: " + xml);
		for (int x = 0; x < times; x++) {
			Date st = new Date();
			log.info("RUN NUMBER : " + x);
			int audId = run.runTransaction(xml);
			
			log.info("Wrote audit: " + audId);
			Date end = new Date();
			log.info("Loop: " + x + " took " + (end.getTime() - st.getTime()) + " ms");
		}
		
		Date endDate = new Date();
		long diff = endDate.getTime() - startDate.getTime();
		
		log.warn("STARTED: " + startDate);
		log.warn("ENDED: " + endDate);
		log.warn("Time taken to process " + times + " transactions is : " + diff + " miliseconds");

	}
}
