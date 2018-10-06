package controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import obj.DataRecord;
import utils.Properties;
import utils.Utils;

public class PiraeusBankController {

	public static String piraeusBankPattern ="Ημ/νία Συναλλαγής	Ημ/νία Αξίας	Περιγραφή Συναλλαγής	Σχόλια / Κωδικός Αναφοράς	Ποσό	Προοδευτικό Λογιστικό Υπόλοιπο	";
	public static String accountNumberPattern = "ΠΕΙΡΑΙΩΣ ΑΠΟΔΟΧΩΝ:";
	
	static Logger logger = Logger.getLogger(PiraeusBankController.class);
	
	public List<DataRecord> getPiraeusBankData() throws Exception{

		File folder = new File(Properties.rootFolder + Properties.piraeusFolder);
		File[] listOfFiles = folder.listFiles();
		
		//list for all data per file
		List<DataRecord> piraeusBankList = new ArrayList<DataRecord>();
		try {
			for (File file : listOfFiles) {
			    if (file.isFile() && file.getName().contains(Properties.txtExtension)) {
			    	logger.info("Read PiraeusBank's transaction file: " + file.getName());
			    	try {
			    		FileInputStream fstream = new FileInputStream(folder + Properties.backslash + file.getName() );
				    	DataInputStream in= new DataInputStream(fstream);
						BufferedReader br = new BufferedReader(new InputStreamReader(in , Properties.UTF8 ));
						
						List<DataRecord> dataList = new ArrayList<DataRecord>(); 
						String strLine;
						boolean startCollectData = false;
						String piraeusAccountNum = "";
						boolean stopProcess = false; 
						
						while ((strLine = br.readLine()) != null && !stopProcess)   {
							
							if (strLine.startsWith(accountNumberPattern)) 
								piraeusAccountNum = Utils.trimText(strLine, accountNumberPattern, Properties.LEFT);
							
							if (strLine.equals(piraeusBankPattern))
								startCollectData = true;
							
							if (startCollectData && !"".equals(strLine)) {
								DataRecord record = new DataRecord(3);
								String[] element = strLine.split(Properties.tab);
								record.setAccountNumber(piraeusAccountNum);
								record.setTransactionDate(Utils.stringToDate(element[0], Properties.TYPICAL));
								record.setTransactionDescription(element[2]);
								record.setTransactionComment(Utils.trimText(element[3], Properties.slash, Properties.RIGHT));
								record.setTransactionNumber(Utils.trimText(element[3], Properties.slash, Properties.LEFT));
								record.setAmount(Double.parseDouble(Utils.changeDemicalSign(element[4])));
								dataList.add(record);
								
							}else {
								stopProcess=true;
							}
						}
						piraeusBankList.addAll(dataList);
			    		
			    	}catch (Exception eX) {
			    		logger.error("PiraeusBankController::", eX);
			    	}
				}
			}
		}catch (Exception e) {
			logger.error("PiraeusBankController::", e);
		}
		return piraeusBankList;
	}
}
