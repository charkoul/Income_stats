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
	public static String piraeusBankPattern2 ="Ημ/νία Συναλλαγής	Ημ/νία Αξίας	Περιγραφή Συναλλαγής	Σχόλια / Κωδικός Αναφοράς	Ποσό	";
	public static String piraeusBankPattern3 ="Κατηγορία	Περιγραφή Συναλλαγής	Ημ/νία Συναλλαγής	Σχόλια / Κωδικός Αναφοράς	Ποσό	";
	public static String piraeusBankPattern4 ="Κατηγορία	Περιγραφή Συναλλαγής	Ημ/νία Συναλλαγής	Σχόλια / Κωδικός Αναφοράς	Ποσό	Προοδευτικό Λογιστικό Υπόλοιπο	";
	public static String accountNumberPattern = "ΠΕΙΡΑΙΩΣ ΑΠΟΔΟΧΩΝ:";
	
	static Logger logger = Logger.getLogger(PiraeusBankController.class);
	
	public List<DataRecord> getPiraeusBankData() throws Exception{

		File folder = new File(Properties.rootFolder + Properties.piraeusFolder);
		logger.info("Read folder = "+Properties.rootFolder + Properties.piraeusFolder );
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
						int patternFunction = 0;
						
						while ((strLine = br.readLine()) != null && !stopProcess)   {
							
							if (strLine.startsWith(accountNumberPattern)) 
								piraeusAccountNum = Utils.trimText(strLine, accountNumberPattern, Properties.LEFT);
							
							if (strLine.equals(piraeusBankPattern) || strLine.equals(piraeusBankPattern2)) {
								startCollectData = true;
								patternFunction = Properties.PATTERN_ONE;
							}else if (strLine.equals(piraeusBankPattern3) || strLine.equals(piraeusBankPattern4)) {
								startCollectData = true;
								patternFunction = Properties.PATTERN_TWO;
							}
							
							if (startCollectData && !Properties.tab.equals(strLine) && lineNotPatternLine(strLine) ) {
								if (patternFunction == Properties.PATTERN_ONE) {
									DataRecord record = new DataRecord(3);
									record = fillDataRecordForPatternOne(strLine, piraeusAccountNum, record);
									dataList.add(record);
								}
								if (patternFunction == Properties.PATTERN_TWO) {
									DataRecord record = new DataRecord(3);
									record = fillDataRecordForPatternTwo(strLine, piraeusAccountNum, record);
									dataList.add(record);
								}
							}
							
							
							if (startCollectData && Properties.tab.equals(strLine)) {
								stopProcess=true;
							}
						}
						piraeusBankList.addAll(dataList);
						logger.info("PiraeusBank's transaction file: " + file.getName() + " return " + dataList.size() + " records");
						//Close the input stream
						in.close();
			    		
			    	}catch (Exception eX) {
			    		logger.error("PiraeusBankController::", eX);
			    	}
				}
			}
			//for debug
			//Utils.printDataRecordList(piraeusBankList);
			
			if (piraeusBankList.size()== 0)
				logger.info("No records added from PiraeusBank's transaction file(s)");
			else
				logger.info("Add " +piraeusBankList.size() + " (total) records from PiraeusBank's transaction file(s)");
			
		}catch (Exception e) {
			logger.error("PiraeusBankController::", e);
		}
		return piraeusBankList;
	}
	
	
	private static String generateTUNPir(String transactionNumber, double amount) {
		String tunNew = "";
		try {
			String amountStr = String.valueOf(amount);
			amountStr =	utils.Utils.removeCharacter(amountStr, Properties.dot) ;
			amountStr = utils.Utils.removeCharacter(amountStr, Properties.minus) ;
			//TUN =TransactionNumber + Amount
			tunNew = transactionNumber + amountStr;
			tunNew = utils.Utils.removeCharacter(tunNew, Properties.space);
		}catch (Exception ex) {
			logger.error(" generateTUN PiraeusBankController::", ex);
		}
		return tunNew;
	}


	public static String removeCurrencyFromText(String currency, String fullText ) {
		fullText = fullText.replace(currency, "");
		return fullText.trim();
	}
	
	
	public static boolean lineNotPatternLine(String line) {
		boolean result = false;
		if (!line.equals(piraeusBankPattern) && !line.equals(piraeusBankPattern2) && !line.equals(piraeusBankPattern3) && !line.equals(piraeusBankPattern4))
			result = true;
		return result;
	}
	
	public static DataRecord fillDataRecordForPatternOne(String line, String accountNum, DataRecord rec ) throws Exception  {
		String[] element = line.split(Properties.tab);
		try {
			rec.setAccountNumber(accountNum);
			rec.setTransactionDate(Utils.stringToDate(element[0], Properties.TYPICAL_DATE_FORMAT));
			rec.setTransactionDescription(element[2].trim());
			rec.setTransactionComment(Utils.trimText(element[3], Properties.slash, Properties.RIGHT));
			rec.setTransactionNumber(Utils.trimText(element[3], Properties.slash, Properties.LEFT));
			rec.setAmount(Double.parseDouble(Utils.changeDemicalSign(removeCurrencyFromText(Properties.EURO,element[4]))));
			rec.setTUN(generateTUNPir(rec.getTransactionNumber(), rec.getAmount()));
		}catch (Exception e) {
			logger.error(" fillDataRecordForPatternOne::", e);
		}finally {
			return rec;
		}
	}
	
	private DataRecord fillDataRecordForPatternTwo(String line, String accountNum, DataRecord rec) throws Exception   {
		String[] element = line.split(Properties.tab);
		try {
			rec.setAccountNumber(accountNum);
			rec.setTransactionDescription(element[1].trim());
			rec.setTransactionDate(Utils.stringToDate(element[2], Properties.TYPICAL_DATE_FORMAT));
			rec.setTransactionComment(Utils.trimText(element[3], Properties.slash, Properties.RIGHT));
			rec.setTransactionNumber(Utils.trimText(element[3], Properties.slash, Properties.LEFT));
			rec.setAmount(Double.parseDouble(Utils.changeDemicalSign(removeCurrencyFromText(Properties.EURO,element[4]))));
			rec.setTUN(generateTUNPir(rec.getTransactionNumber(), rec.getAmount()));
		}catch (Exception e) {
			logger.error(" fillDataRecordForPatternTwo::", e);
		}finally {
			return rec;
		}
	}


		
	
	
	
}
